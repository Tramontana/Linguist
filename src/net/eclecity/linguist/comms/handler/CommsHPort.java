// CommsHPort.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.comms.handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;
import java.util.Vector;

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;

import net.eclecity.linguist.basic.handler.BasicHBuffer;
import net.eclecity.linguist.basic.handler.BasicHVariable;
import net.eclecity.linguist.comms.runtime.CommsRMessages;
import net.eclecity.linguist.handler.LHData;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	A port variable.
*/
public class CommsHPort extends LHVariableHandler
{
	public CommsHPort() {}

	public Object newElement(Object extra) { return new CommsHPortData(); }
	
	/***************************************************************************
		Open a port.
	*/
	public void open(LVValue portName,boolean rawMode,boolean input,
		boolean output) throws LRException
	{
		CommsHPortData myData=(CommsHPortData)getData();
		myData.port=this;
		myData.rawMode=rawMode;
		Enumeration portList=CommPortIdentifier.getPortIdentifiers();
		while (portList.hasMoreElements())
		{
			myData.portID=(CommPortIdentifier)portList.nextElement();
			if (myData.portID.getPortType()==CommPortIdentifier.PORT_SERIAL)
			{
				if (myData.portID.getName().equals(portName.getStringValue()))
				{
					try
					{
						myData.serialPort=(SerialPort)myData.portID.open("Linguist",2000);
					}
					catch (PortInUseException e) { throw new LRException(CommsRMessages.portInUse(name)); }
					if (myData.serialPort==null) throw new LRException(CommsRMessages.cantOpenPort(name));
					if (input)
					{
						try
						{
							myData.inputStream=myData.serialPort.getInputStream();
						}
						catch (IOException e) { throw new LRException(CommsRMessages.cantGetInputStream(name)); }
						try
						{
							myData.serialPort.addEventListener(myData);
						}
						catch (TooManyListenersException e) { throw new LRException(CommsRMessages.tooManyListenersException(name)); }
						myData.serialPort.notifyOnDataAvailable(true);
						myData.serialPort.notifyOnOutputEmpty(true);
						myData.serialPort.notifyOnCTS(true);
						myData.serialPort.notifyOnDSR(true);
						myData.serialPort.notifyOnRingIndicator(true);
						myData.serialPort.notifyOnCarrierDetect(true);
						myData.serialPort.notifyOnOverrunError(true);
						myData.serialPort.notifyOnParityError(true);
						myData.serialPort.notifyOnFramingError(true);
						myData.serialPort.notifyOnBreakInterrupt(true);
					}
					if (output)
					{
						try
						{
							myData.outputStream=myData.serialPort.getOutputStream();
						}
						catch (IOException e) { throw new LRException(CommsRMessages.cantGetOutputStream(name)); }
					}
				}
			}
		}
	}
	
	/***************************************************************************
		Close a port.
	*/
	public void close() throws LRException
	{
		CommsHPortData myData=(CommsHPortData)getData();
		myData.serialPort.close();
	}
	
	/***************************************************************************
		Set the baudrate etc. of a port.
	*/
	public void set(LVValue baudrate,int dataBits,int stopBits,int parity) throws LRException
	{
		CommsHPortData myData=(CommsHPortData)getData();
		if (myData.serialPort==null) throw new LRException(CommsRMessages.portNotOpen(name));
		try
		{
			myData.serialPort.setSerialPortParams(baudrate.getIntegerValue(),
				dataBits,stopBits,parity);
		}
		catch (UnsupportedCommOperationException e)
		{
			throw new LRException(CommsRMessages.unsupportedCommOperation(name));
		}
	}
	
	/***************************************************************************
		Read data from a port.
	*/
	public void read(LHVariableHandler handler,boolean wholeLine) throws LRException
	{
		CommsHPortData myData=(CommsHPortData)getData();
		if (myData.serialPort==null) throw new LRException(CommsRMessages.portNotOpen(name));
		String s="";
		if (handler instanceof BasicHVariable)
		{
			long value;
			try
			{
				value=Long.parseLong(s);
			}
			catch (NumberFormatException e ) { value=0; }
			((BasicHVariable)handler).setValue(value);
		}
		else if (handler instanceof BasicHBuffer)
		{
			((BasicHBuffer)handler).setValue(s);
		}
	}
	
	/***************************************************************************
		Flush a port.
	*/
	public void flush() throws LRException
	{
		while (hasData()) getNextByte();
	}

	/***************************************************************************
		Write data to a port.
	*/
	public void write(LVValue s) throws LRException
	{
		CommsHPortData myData=(CommsHPortData)getData();
		if (myData.serialPort==null) throw new LRException(CommsRMessages.portNotOpen(name));
		try
		{
			if (myData.rawMode)
			{
				myData.outputStream.write((byte)s.getIntegerValue());
			}
			else myData.outputStream.write(s.getStringValue().getBytes());
		}
		catch (IOException e) { throw new LRException(CommsRMessages.cantWritePort(name)); }
	}
	
	/***************************************************************************
		Set the DTR or RTS line of a port.
	*/
	public void set(boolean dtr,boolean state) throws LRException
	{
		CommsHPortData myData=(CommsHPortData)getData();
		if (myData.serialPort==null) throw new LRException(CommsRMessages.portNotOpen(name));
		if (dtr) myData.serialPort.setDTR(state);
		else myData.serialPort.setRTS(state);
	}

	/***************************************************************************
		Set up a callback for input.
	*/
	public void onInput(int cb) throws LRException
	{
		CommsHPortData myData=(CommsHPortData)getData();
		if (myData.serialPort==null) throw new LRException(CommsRMessages.portNotOpen(name));
		myData.setOnInputCB(cb);
	}
	
	/***************************************************************************
		Test if this port has received data.
	*/
	public boolean hasData() throws LRException
	{
		CommsHPortData myData=(CommsHPortData)getData();
		if (myData.serialPort==null) throw new LRException(CommsRMessages.portNotOpen(name));
		return myData.hasData();
	}

	/***************************************************************************
		Return the next byte input via this port.
	*/
	public long getNextByte() throws LRException
	{
		CommsHPortData myData=(CommsHPortData)getData();
		if (myData.serialPort==null) throw new LRException(CommsRMessages.portNotOpen(name));
		return myData.getNextByte();
	}

	/***************************************************************************
		Return the next line input via this port.
	*/
	public String getNextLine() throws LRException
	{
		CommsHPortData myData=(CommsHPortData)getData();
		if (myData.serialPort==null) throw new LRException(CommsRMessages.portNotOpen(name));
		return myData.getNextLine();
	}
	
	/***************************************************************************
		A private class that holds data about a port.
		It also handles serial port events.
	*/
	class CommsHPortData extends LHData implements SerialPortEventListener
	{
		CommsHPort port=null;
		CommPortIdentifier portID=null;
		SerialPort serialPort=null;
		InputStream inputStream=null;
		OutputStream outputStream=null;
		boolean rawMode=false;

		private int onInputCB=0;
		private String receivedData;
		private Vector input=new Vector();
		private byte[] packet=null;
		private int packetPosition=0;
		
		CommsHPortData() {}
		
		void setOnInputCB(int cb)
		{
			onInputCB=cb;
			receivedData="";
			input=new Vector();
		}
		
		boolean hasData()
		{
			if (input.size()>0) return true;
			if (rawMode)
			{
				if (packet==null) return false;
				if (packetPosition<packet.length) return true;
			}
			return false;
		}
		
		long getNextByte()
		{
			// If no packet, get the next one.
			if (packet==null)
			{
				if (input.size()>0)
				{
					packet=(byte[])input.elementAt(0);
					input.removeElementAt(0);
					packetPosition=0;
				}
				else return 0;		// no packets - should have checked first
			}
			if (packetPosition<packet.length)
			{
				long value=packet[packetPosition++];
				if (packetPosition==packet.length) packet=null;
				if (value<0) value+=256;
				return value;
			}
			return 0;
		}

		String getNextLine()
		{
			String s="";
			if (input.size()>0)
			{
				s=(String)input.elementAt(0);
				input.removeElementAt(0);
			}
			return s;
		}

		public void serialEvent(SerialPortEvent evt)
		{
			switch (evt.getEventType())
			{
			case SerialPortEvent.BI:
				System.out.println("Serial port event BI.");
				break;
			case SerialPortEvent.OE:
				System.out.println("Serial port event OE.");
				break;
			case SerialPortEvent.FE:
				System.out.println("Framing error.");
				break;
			case SerialPortEvent.PE:
				System.out.println("Parity error.");
				break;
			case SerialPortEvent.CD:
				System.out.println("Serial port event CD.");
				break;
			case SerialPortEvent.CTS:
				System.out.println("Serial port event CTS.");
				break;
			case SerialPortEvent.DSR:
				System.out.println("Serial port event DSR.");
				break;
			case SerialPortEvent.RI:
				System.out.println("Serial port event RI.");
				break;
			case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
				break;
			case SerialPortEvent.DATA_AVAILABLE:
				byte[] readBuffer=new byte[200];
				try
				{
					// Read up to 200 bytes into a buffer and get the byte count
					synchronized(readBuffer)
					{
						int n=inputStream.read(readBuffer);
						if (onInputCB>0)
						{
							if (rawMode)
							{
								// Copy the received bytes into a new buffer
								byte[] buf=new byte[n];
								System.arraycopy(readBuffer,0,buf,0,n);
								// Add the new buffer to the list of input data
								input.addElement(buf);
								// Trigger the callback
								port.getProgram().addQueue(onInputCB,port);
							}
							else
							{
								receivedData+=new String(readBuffer,0,n);
								while (true)
								{
									n=receivedData.indexOf('\r');
									if (n<0) break;
									input.addElement(receivedData.substring(0,n));
									receivedData=receivedData.substring(n+1);
									port.getProgram().addQueue(onInputCB,port);
								}
							}
						}
					}
				}
				catch (IOException ignored) {}
				break;
			}
		}
	}
}

