// MediaHPlayer.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.media.handler;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.Hashtable;

import javax.media.CachingControl;
import javax.media.CachingControlEvent;
import javax.media.Clock;
import javax.media.ControllerErrorEvent;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;
import javax.media.Manager;
import javax.media.Player;
import javax.media.RealizeCompleteEvent;
import javax.media.StopAtTimeEvent;
import javax.media.Time;
import javax.swing.JOptionPane;

import net.eclecity.linguist.handler.LHData;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.media.runtime.MediaRMessages;
import net.eclecity.linguist.media.value.MediaVLocation;
import net.eclecity.linguist.media.value.MediaVSize;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	A media player variable.
*/
public class MediaHPlayer extends LHVariableHandler
	implements ControllerListener, MouseListener
{
	private static Hashtable players=new Hashtable();
	private static Hashtable components=new Hashtable();

	public MediaHPlayer() {}

	public Object newElement(Object extra) { return new MediaHPlayerData(); }

	/***************************************************************************
		Create a media player.
		If the sizes are null the player will use the default size of the movie
		and center it in its container.
	*/
	public void create(Container container,LVValue left,LVValue top,LVValue width,LVValue height) throws LRException
	{
		MediaHPlayerData myData=(MediaHPlayerData)getData();
		myData.container=container;
		if (left!=null) myData.left=left.getIntegerValue();
		if (top!=null) myData.top=top.getIntegerValue();
		if (width!=null) myData.width=width.getIntegerValue();
		if (height!=null) myData.height=height.getIntegerValue();
	}

	public void onMediaEvent(int event,int n) throws LRException
	{
		switch (event)
		{
			case MEDIA_READY:
				((MediaHPlayerData)getData()).mediaReadyCB=n;
				break;
			case MEDIA_END:
				((MediaHPlayerData)getData()).mediaEndCB=n;
				break;
		}
	}

	public String getValue() { return ""; }

	public void play(LVValue media) { }

	public int getLeft() throws LRException
	{
		try { return ((MediaHPlayerData)getData()).visualComponent.getLocation().x; }
		catch (NullPointerException e) { return 0; }
	}
	public int getTop() throws LRException
	{
		try { return ((MediaHPlayerData)getData()).visualComponent.getLocation().y; }
		catch (NullPointerException e) { return 0; }
	}
	public int getOriginalWidth() throws LRException
	{
		return ((MediaHPlayerData)getData()).originalWidth;
	}
	public int getOriginalHeight() throws LRException
	{
		return ((MediaHPlayerData)getData()).originalHeight;
	}

	/***************************************************************************
		Get the duration of a movie.  The timebase is ticks (100's of a second).
	*/
	public long getDuration() throws LRException
	{
		return (((MediaHPlayerData)getData()).player.getDuration().getNanoseconds()/10000000);
	}

	/***************************************************************************
		Get the current position in a movie.
	*/
	public long getMediaTime() throws LRException
	{
		return (((MediaHPlayerData)getData()).player.getMediaTime().getNanoseconds()/10000000);
	}

	/***************************************************************************
		Load a media file.  When it is realized, resume at 'nextPC'.
	*/
	public void load(LVValue media,int nextPC) throws LRException
	{
		MediaHPlayerData myData=(MediaHPlayerData)getData();
		if (myData.realized) closeAndDeallocate(myData);
		myData.realizeCompleteCB=nextPC;
		URL mediaURL = null;
		try
		{
			mediaURL=new URL(media.getStringValue());
			myData.player=Manager.createPlayer(mediaURL);
			players.put(myData.player,new Integer(getTheIndex()));
			myData.player.addControllerListener(this);
			myData.player.prefetch();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new LRException(MediaRMessages.cantLoadMedia(media.getStringValue()));
		}
	}

	/***************************************************************************
		Show the visual component for the current (realized) Player.
	*/
	public void showVisualComponent() throws LRException
	{
		MediaHPlayerData myData=(MediaHPlayerData)getData();
		if (!myData.realized)
		{
			JOptionPane.showMessageDialog(null,name+" is not realized.");
			return;		// throw new LRError(MediaRErrors.notRealized(name));
		}
		int width;
		int height;
		if (myData.width==-1)		// use the default size
		{
			width=myData.visualComponent.getPreferredSize().width;
			height=myData.visualComponent.getPreferredSize().height;
		}
		else
		{
			width=myData.width;
			height=myData.height;
		}
		if (myData.left==-1) fitVisualComponent(myData,width,height);
		else
		{
			Insets insets=myData.container.getInsets();
			myData.visualComponent.setBounds(
				myData.left+insets.left,
				myData.top+insets.top,
				myData.width,
				myData.height);
		}
		myData.visualComponent.setVisible(true);
		myData.container.validate();
   }

	/***************************************************************************
		Fit the visual component into a given width and height.
	*/
   private void fitVisualComponent(MediaHPlayerData myData,int w,int h)
   {
		int width=myData.visualComponent.getPreferredSize().width;
		int height=myData.visualComponent.getPreferredSize().height;
		int hh=(w*height)/width;
		if (hh>h)
		{
			width=(h*width)/height;
			height=h;
		}
		else if (hh<h)
		{
			width=w;
			height=hh;
		}
		else
		{
			width=w;
			height=h;
		}
		h=height;
		if (myData.controlPanelComponentVisible)
			h+=myData.controlPanelComponent.getMinimumSize().height;
		int left=(myData.container.getSize().width-width)/2;
		int top=(myData.container.getSize().height-h)/2;
		myData.visualComponent.setBounds(left,top,width,height);
	}

	/***************************************************************************
		Show the control component for the current (realized) Player.
	*/
	public void showControlComponent() throws LRException
	{
		MediaHPlayerData myData=(MediaHPlayerData)getData();
		if (!myData.realized) return;		// throw new LRError(MediaRErrors.notRealized(name));
		myData.controlPanelComponentVisible=true;
		myData.controlPanelComponent.setBackground(Color.lightGray);
		int height=myData.controlPanelComponent.getMinimumSize().height;
		Dimension d=myData.visualComponent.getSize();
		fitVisualComponent(myData,d.width,d.height-height);
		Rectangle r=myData.visualComponent.getBounds();
		int left=r.x;
		int top=r.y+r.height;
		int width=r.width;
		myData.controlPanelComponent.setBounds(left,top,width,height);
		myData.controlPanelComponent.setVisible(true);
		myData.container.validate();
	}

	/***************************************************************************
		Hide the visual component for the current Player.
	*/
	public void hideVisualComponent() throws LRException
	{
		MediaHPlayerData myData=(MediaHPlayerData)getData();
		try
		{
			myData.visualComponent.setVisible(false);
		}
		catch (NullPointerException ignored) {}
	}

	/***************************************************************************
		Hide the control component for the current Player.
	*/
	public void hideControlComponent() throws LRException
	{
		MediaHPlayerData myData=(MediaHPlayerData)getData();
		try
		{
			myData.controlPanelComponent.setVisible(false);
		}
		catch (NullPointerException ignored) {}
	}

	/***************************************************************************
		Play the current (realized) Player
	*/
	public void play(LVValue start,LVValue finish) throws LRException
	{
		MediaHPlayerData myData=(MediaHPlayerData)getData();
		if (!myData.realized) return;		// throw new LRError(MediaRErrors.notRealized(name));
		myData.player.stop();
		if (start!=null) myData.player.setMediaTime(new Time(start.getNumericValue()*10000000));
		if (finish!=null) myData.player.setStopTime(new Time(finish.getNumericValue()*10000000));
		else myData.player.setStopTime(Clock.RESET);
		myData.player.start();
	}

	/***************************************************************************
		Stop the current (realized) Player.
	*/
	public void stop() throws LRException
	{
		MediaHPlayerData myData=(MediaHPlayerData)getData();
		if (myData.realized && myData.player!=null) myData.player.stop();
	}

	/***************************************************************************
		Seek the current (realized) Player.
	*/
	public void seek(LVValue value,boolean absolute) throws LRException
	{
		MediaHPlayerData myData=(MediaHPlayerData)getData();
		if (myData.realized)
		{
			myData.player.stop();
			long time;
			if (absolute) time=value.getNumericValue()*10000000;
			else time=myData.player.getMediaTime().getNanoseconds()+value.getNumericValue()*10000000;
			if (time<0) time=0;
			long duration=myData.player.getDuration().getNanoseconds();
			if (time>=duration) time=duration-10000000;
			myData.player.setMediaTime(new Time(time));
			myData.player.start();
			myData.player.stop();
		}
	}

	/***************************************************************************
		Dispose the current (realized) Player.
	*/
	public void dispose() throws LRException
	{
		MediaHPlayerData myData=(MediaHPlayerData)getData();
		if (myData.realized) closeAndDeallocate(myData);
		super.dispose();
	}

	private void closeAndDeallocate(MediaHPlayerData myData)
	{
		Player player=myData.player;
		if (player==null) return;
		Component cv=player.getVisualComponent();
		Component cc=player.getControlPanelComponent();
		if (cv!=null) myData.container.remove(cv);
		if (cc!=null) myData.container.remove(cc);
		player.stop();
		player.deallocate();
		player.close();
		myData.container.validate();
		myData.player=null;
	}

	/***************************************************************************
		Move this player.
	*/
	public void moveTo(MediaVLocation location) throws LRException
	{
		try
		{
			Component c=((MediaHPlayerData)getData()).player.getVisualComponent();
			c.setLocation(location.getLeft(),location.getTop());
		}
		catch (NullPointerException ignored) {}
	}

	public void moveBy(MediaVSize size) throws LRException
	{
		try
		{
			Component c=((MediaHPlayerData)getData()).player.getVisualComponent();
			Point loc=c.getLocation();
			c.setLocation(loc.x+size.getWidth(),loc.y+size.getHeight());
		}
		catch (NullPointerException ignored) {}
	}

	public void moveBy(LVValue value,int direction) throws LRException
	{
		try
		{
			Component c=((MediaHPlayerData)getData()).player.getVisualComponent();
			Point loc=c.getLocation();
			switch (direction)
			{
			case LEFT:
				c.setLocation(loc.x-value.getIntegerValue(),loc.y);
				break;
			case RIGHT:
				c.setLocation(loc.x+value.getIntegerValue(),loc.y);
				break;
			case UP:
				c.setLocation(loc.x,loc.y-value.getIntegerValue());
				break;
			case DOWN:
				c.setLocation(loc.x,loc.y+value.getIntegerValue());
				break;
			}
		}
		catch (NullPointerException ignored) {}
	}

	/***************************************************************************
		Register a handler for when play stops.
	*/
	public void onStopPlayer(int n) throws LRException
	{
		((MediaHPlayerData)getData()).onStopPlayCB=n;
	}

	/***************************************************************************
		Register a handler for mouse clicks.
	*/
	public void onMouseClick(int n) throws LRException
	{
		((MediaHPlayerData)getData()).onMouseClickCB=n;
	}

	/***************************************************************************
		Set the cursor for this player.
	*/
	public void setCursor(Cursor cursor) throws LRException
	{
		((MediaHPlayerData)getData()).visualComponent.setCursor(cursor);
	}

	/***************************************************************************
		Handle controller events.
	*/
	public void controllerUpdate(ControllerEvent event)
	{
		try
		{
			Player player=(Player)event.getSourceController();
			Integer index=(Integer)players.get(player);
			if (index==null) return;
			MediaHPlayerData myData=(MediaHPlayerData)getData(index.intValue());

			if (event instanceof RealizeCompleteEvent)
			{
				println("RealizeCompleteEvent");
				if ((myData.visualComponent=player.getVisualComponent())!=null)
				{
					components.put(myData.visualComponent,index);
					myData.visualComponent.addMouseListener(this);
				}
				else JOptionPane.showMessageDialog(null,"Player visual component didn't get created.");
				myData.controlPanelComponent=player.getControlPanelComponent();
				player.start();
				player.stop();
				if (myData.realizeCompleteCB!=0) addQueue(myData.realizeCompleteCB);
				myData.realized=true;
				if (myData.visualComponent!=null)
				{
					myData.visualComponent.setVisible(false);
					if (myData.container!=null)
						myData.container.add(myData.visualComponent,0);
				}
				if (myData.controlPanelComponent!=null)
				{
					myData.controlPanelComponent.setVisible(false);
					if (myData.container!=null)
						myData.container.add(myData.controlPanelComponent,0);
				}
				if (myData.mediaReadyCB!=0) addQueue(myData.mediaReadyCB);
			}

			else if (event instanceof ControllerErrorEvent)
			{
				println("ControllerErrorEvent");
				System.err.println("*** ControllerErrorEvent *** " +
					((ControllerErrorEvent)event).getMessage());
			}

			else if (event instanceof CachingControlEvent)
			{
				println("CachingControlEvent");
				Component progressBar=null;
				// Put a progress bar up when downloading starts,
				// take it down when downloading ends.
				CachingControlEvent  e = (CachingControlEvent) event;
				CachingControl control = e.getCachingControl();

				long cc_progress = e.getContentProgress();
				long cc_length   = control.getContentLength();

				if (progressBar==null)  // Add the bar if not already there ...
					if ((progressBar=control.getProgressBarComponent())!=null)
					{
	//					progressBar.setBounds(myData.left,myData.top+myData.height,myData.width,
	//						progressBar.getMinimumSize().height);
						myData.container.add(progressBar,0);
						myData.container.validate();
					}

				if (progressBar!=null)  // Remove bar when finished downloading
					if (cc_progress==cc_length)
					{
						myData.container.remove(progressBar);
						progressBar=null;
						myData.container.validate();
					}
			}

			else if (event instanceof StopAtTimeEvent)
			{
				println("StopAtTimeEvent");
				if (myData.onStopPlayCB!=0) addQueue(myData.onStopPlayCB);
			}

			else if (event instanceof EndOfMediaEvent)
			{
				println("EndOfMediaEvent");
	//			closeAndDeallocate(myData);
				if (myData.mediaEndCB!=0) addQueue(myData.mediaEndCB);
			}
		}
		catch (LRException e) {}
	}

	/***************************************************************************
		Process a mouse clicked event on the player display component.
	*/
	public void mouseClicked(MouseEvent evt)
	{
		Component component=(Component)evt.getSource();
		Integer index=(Integer)components.get(component);
		if (index==null) return;
		try
		{
			MediaHPlayerData myData=(MediaHPlayerData)getData();
			if (myData.onMouseClickCB!=0) addQueue(myData.onMouseClickCB);
		}
		catch (LRException e) {}
	}

	/***************************************************************************
		The remaining mouse events.
	*/
	public void mouseEntered(MouseEvent evt) {}
	public void mouseExited(MouseEvent evt) {}
	public void mousePressed(MouseEvent evt) {}
	public void mouseReleased(MouseEvent evt) {}
	public void mouseMoved(MouseEvent evt) {}
	public void mouseDragged(MouseEvent evt) {}

	public static final int MEDIA_READY=1;
	public static final int MEDIA_END=2;

	public static final int LEFT=1;
	public static final int RIGHT=2;
	public static final int UP=3;
	public static final int DOWN=4;

	/***************************************************************************
		A private class for player data.
	*/
	class MediaHPlayerData extends LHData
	{
		Container container=null;
		Component visualComponent=null;
		Component controlPanelComponent=null;
		boolean controlPanelComponentVisible=false;
		Player player=null;
		int left=-1;
		int top=-1;
		int width=0;
		int height=0;
		int originalWidth=0;
		int originalHeight=0;
		int mediaReadyCB=0;
		int mediaEndCB=0;
		int onStopPlayCB=0;
		int onMouseClickCB=0;
		int realizeCompleteCB=0;
		boolean realized=false;

		MediaHPlayerData() {}
	}
}

