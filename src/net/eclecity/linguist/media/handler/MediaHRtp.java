// MediaHRtp.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.media.handler;

import java.io.IOException;
import java.util.Vector;

import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Controller;
import javax.media.ControllerClosedEvent;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.DataSink;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaException;
import javax.media.MediaLocator;
import javax.media.NoPlayerException;
import javax.media.NoProcessorException;
import javax.media.Player;
import javax.media.Processor;
import javax.media.RealizeCompleteEvent;
import javax.media.control.TrackControl;
import javax.media.format.AudioFormat;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.DataSource;

import net.eclecity.linguist.handler.LHData;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	An RTP (Real Time Protocol) processor.
	<pre>
	[1.001 GT]  07/08/01  New class.
	</pre>
*/
public class MediaHRtp extends LHVariableHandler
{
	public MediaHRtp() {}

	public Object newElement(Object extra) { return new MediaHRtpData(); }
	
	/***************************************************************************
		Create an rtp processor.
	*/
	public void create(int type,int mode,int coding,LVValue rate,LVValue bits,
		LVValue channels,LVValue source,LVValue destination) throws LRException
	{
		String src=null;
		if (source!=null) src=source.getStringValue();
		String dest=null;
		if (destination!=null) dest=destination.getStringValue();
		MediaHRtpData myData=(MediaHRtpData)getData();
		myData.create(type,mode,coding,rate.getIntegerValue(),
			bits.getIntegerValue(),channels.getIntegerValue(),src,dest);
	}

	/***************************************************************************
		Stop the processor.
	*/
	public void stop() throws LRException
	{
		MediaHRtpData myData=(MediaHRtpData)getData();
		myData.stop();
	}
	
	/***************************************************************************
		Report the error state.
	*/
	public boolean hasError() throws LRException
	{
		MediaHRtpData myData=(MediaHRtpData)getData();
		return myData.hasError();
	}
	
	public static final int
		AUDIO=1;
	
	public static final int
		CAPTURE=1,
		RELAY=2,
		PLAY=3;
	
	public static final int
		LINEAR=1,
		G723=2,
		GSM=3;
	
	/***************************************************************************
		A private class for RTP.
	*/
	class MediaHRtpData extends LHData
	{
		int type;
		int mode;
		int coding;
		int rate;
		int bits;
		int channels;
		String source;
		String destination;
		boolean error;

		private MediaLocator locator;
		private Processor processor=null;
		private DataSink  rtptransmitter=null;
		private DataSource dataOutput=null;
		private Player player=null;
		
		MediaHRtpData() {}

		void create(int type,int mode,int coding,int rate,int bits,
			int channels,String source,String destination)
		{
			this.type=type;
			this.mode=mode;
			this.coding=coding;
			this.rate=rate;
			this.bits=bits;
			this.channels=channels;
			this.source=source;
			this.destination=destination;
			
			error=false;
			
			switch (type)
			{
				case AUDIO:
					switch (mode)
					{
						case CAPTURE:
							captureAudio();
							break;
						case RELAY:
							relayAudio();
							break;
						case PLAY:
							playAudio();
							break;
					}
					break;
			}
		}

		/****************************************************************************
			Capture audio and convert it using the parameters given.
		*/
		void captureAudio()
		{
			Vector deviceList=CaptureDeviceManager.getDeviceList(
				new AudioFormat(AudioFormat.LINEAR,44100,16,2));
			if (deviceList.size()>0)
			{
				CaptureDeviceInfo di=(CaptureDeviceInfo)deviceList.firstElement();
				locator=di.getLocator();
				startAudioTransmission();
			}
			else
			{
				System.out.println("No capture device found!");
				error=true;
			}
		}
		
		/****************************************************************************
			Relay audio from one URL to another.
		*/
		void relayAudio()
		{
			String url="rtp://"+source+"/audio/1";
			locator=new MediaLocator(url);
			if (locator==null)
			{
				System.out.println("Can't build MediaRL for RTP");
				error=true;
			}
			else startAudioTransmission();
		}

		/****************************************************************************
			Start transmission of audio.
		*/
		void startAudioTransmission()
		{
				// Start the transmission
			String result;
			
			// Create a processor for the specified media locator
			result=createAudioProcessor();
			if (result!=null)
			{
				System.out.println(result);
				error=true;
				return;
			}
			
			// Create an RTP session to transmit the output of the
			// processor to the specified IP address and port no.
			result=createTransmitter();
			if (result!=null)
			{
				processor.close();
				processor=null;
				System.out.println(result);
				error=true;
				return;
			}
		
			// Start the transmission
			processor.start();
	}
		
		/****************************************************************************
			Play audio from a URL.
		*/
		void playAudio()
		{
			String url="rtp://"+source+"/audio/1";
			MediaLocator mrl=new MediaLocator(url);
			if (mrl==null)
			{
				System.out.println("Can't build MediaRL for RTP");
				error=true;
				return;
			}
	
			try
			{
				player=Manager.createPlayer(mrl);
				player.addControllerListener(new ControllerListener()
				{
					public synchronized void controllerUpdate(ControllerEvent event)
					{
//						System.out.println(event.toString());
						if (event instanceof RealizeCompleteEvent)
						{
							System.out.println("Player started.");
							player.start();
						}
					}
				});
				player.realize();
			}
			catch (IOException e) { e.printStackTrace(); }
			catch (NoPlayerException e) { e.printStackTrace(); }
		}

		/****************************************************************************
			Stop the transmission if already started
		*/
		public void stop()
		{
			synchronized (this)
			{
				if (processor!=null)
				{
					processor.stop();
					processor.close();
					processor=null;
					if (rtptransmitter!=null)
					{
						rtptransmitter.close();
						rtptransmitter=null;
					}
				}
			}
		}
	
		/****************************************************************************
			Create an audio processor
		*/
		private String createAudioProcessor()
		{
			if (locator == null) return "Locator is null";
			
			DataSource ds;
			
			try
			{
				ds = Manager.createDataSource(locator);
			}
			catch (Exception e)
			{
				return "Couldn't create DataSource";
			}
			
			// Try to create a processor to handle the input media locator
			try
			{
				processor = Manager.createProcessor(ds);
			}
			catch (NoProcessorException npe)
			{
				return "Couldn't create processor";
			}
			catch (IOException ioe)
			{
				return "IOException creating processor";
			} 
			
			// Wait for it to configure
			boolean result = waitForState(processor, Processor.Configured);
			if (result == false) return "Couldn't configure processor";
			
			// If the mode is CAPTURE, set the output format.
			if (mode==CAPTURE)
			{
				// Get the tracks from the processor
				TrackControl[] tracks = processor.getTrackControls();
				
				// Do we have atleast one track?
				if (tracks == null || tracks.length < 1) return "Couldn't find tracks in processor";
				
				String fmt=AudioFormat.LINEAR;
				switch (coding)
				{
					case LINEAR:
						break;
					case G723:
						fmt=AudioFormat.G723_RTP;
						break;
					case GSM:
						fmt=AudioFormat.GSM_RTP;
						break;
				}
				
				boolean programmed = false;
		
				// Search through the tracks for an audio track
				for (int n = 0; n < tracks.length; n++)
				{
					Format format = tracks[n].getFormat();
					if (tracks[n].isEnabled() && format instanceof AudioFormat && !programmed)
					{
						// Found an audio track.
						if (tracks[n].setFormat(new AudioFormat(fmt,rate,bits,channels))!=null)
							programmed = true;
					}
					else tracks[n].setEnabled(false);
				}
		
				if (!programmed) return "Couldn't find audio track";
			}
			
			// Set the output content descriptor to RAW_RTP
			ContentDescriptor cd = new ContentDescriptor(ContentDescriptor.RAW_RTP);
			processor.setContentDescriptor(cd);
			
			// Realize the processor. This will internally create a flow
			// graph and attempt to create an output datasource.
			result = waitForState(processor, Controller.Realized);
			if (result == false) return "Couldn't realize processor";
			
			// Get the output data source of the processor
			dataOutput = processor.getDataOutput();
			return null;
		}

		/****************************************************************************
			Create an RTP transmit data sink.
		*/
		private String createTransmitter()
		{
			// Create a media locator for the RTP data sink.
			// For example:
			//    rtp://129.130.131.132:42050/audio
			String rtpURL = "rtp://" + destination + "/audio";
			MediaLocator outputLocator = new MediaLocator(rtpURL);
		
			// Create a data sink, open it and start transmission. It will wait
			// for the processor to start sending data. So we need to start the
			// output data source of the processor. We also need to start the
			// processor itself, which is done after this method returns.
			try
			{
				rtptransmitter = Manager.createDataSink(dataOutput, outputLocator);
				rtptransmitter.open();
				rtptransmitter.start();
				dataOutput.start();
			}
			catch (MediaException me)
			{
				return "Couldn't create RTP data sink";
			}
			catch (IOException ioe)
			{
				return "Couldn't create RTP data sink";
			}
			
			return null;
		}

		boolean hasError() { return error; }
	
		/****************************************************************
		* Convenience methods to handle processor's state changes.
		****************************************************************/
		
		private Integer stateLock = new Integer(0);
		private boolean failed = false;
		
		Integer getStateLock()
		{
			return stateLock;
		}
		
		void setFailed()
		{
			failed = true;
		}
		
		private synchronized boolean waitForState(Processor p, int state)
		{
			p.addControllerListener(new ControllerListener()
			{
				public void controllerUpdate(ControllerEvent ce)
				{
					// If there was an error during configure or
					// realize, the processor will be closed
					if (ce instanceof ControllerClosedEvent)
					setFailed();
					
					// All controller events, send a notification
					// to the waiting thread in waitForState method.
					synchronized (getStateLock())
					{
						getStateLock().notifyAll();
					}
				}
			});
			failed = false;
			
			// Call the required method on the processor
			if (state == Processor.Configured)
			{
				p.configure();
			}
			else if (state == Processor.Realized)
			{
				p.realize();
			}
			
			// Wait until we get an event that confirms the
			// success of the method, or a failure event.
			// See StateListener inner class
			while (p.getState() < state && !failed)
			{
				synchronized (getStateLock())
				{
					try
					{
						getStateLock().wait();
					}
					catch (InterruptedException ie)
					{
						return false;
					}
				}
			}
			
			if (failed) return false;
			return true;
		}
	}
}

