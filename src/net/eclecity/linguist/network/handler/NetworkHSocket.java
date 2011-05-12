// NetworkHSocket.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;

import net.eclecity.linguist.handler.LHData;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.network.runtime.NetworkRMessages;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVConstant;
import net.eclecity.linguist.value.LVStringConstant;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	A socket variable.
	<pre>
	[1.001 GT]  12/02/01  Pre-existing.
	</pre>
*/
public class NetworkHSocket extends LHVariableHandler
{
	public NetworkHSocket() {}

	public Object newElement(Object extra) { return new NetworkHSocketData(); }

	/***************************************************************************
		Tell callers this type doesn't return a value.
	*/
	public boolean hasValue() { return false; }

	/***************************************************************************
		Set the address or port of this socket.
	*/
	public void setValue(LVValue value,int mode) throws LRException
	{
		NetworkHSocketData myData=(NetworkHSocketData)getData();
		if (mode==ADDRESS) myData.address=value;
		else myData.port=value;
	}

	/***************************************************************************
		Set the mode of this socket.
	*/
	public void setMode(int mode) throws LRException
	{
		((NetworkHSocketData)getData()).mode=mode;
	}

	/***************************************************************************
		Open a socket.
	*/
	public void open() throws LRException
	{
		((NetworkHSocketData)getData()).open();
	}

	/***************************************************************************
		Close a socket.
	*/
	public void close() throws LRException
	{
		((NetworkHSocketData)getData()).close();
	}

	/***************************************************************************
		Read data from a socket.
	*/
	public String read() throws LRException
	{
		return ((NetworkHSocketData)getData()).read();
	}

	/***************************************************************************
		Write data to a socket.
	*/
	public void write(LVValue s) throws LRException
	{
		((NetworkHSocketData)getData()).write(s.getStringValue());
	}

	/***************************************************************************
		Set up a callback for events.
	*/
	public void onInput(int cb) throws LRException
	{
		((NetworkHSocketData)getData()).setOnInputCB(cb);
	}

	/***************************************************************************
		Test if this socket has received data.
	*/
	public boolean hasData() throws LRException
	{
		return ((NetworkHSocketData)getData()).hasData();
	}

	/***************************************************************************
		Test if this socket has an error.
	*/
	public boolean hasError() throws LRException
	{
		return ((NetworkHSocketData)getData()).hasError();
	}

	/***************************************************************************
		Here if the operation didn't go according to plan.
	*/
	public void setException(Exception e)
	{
		try { ((NetworkHSocketData)getData()).setError(true); }
		catch (LRException ignored) {}
		e.printStackTrace();
	}

	/***************************************************************************
		Get the address associated with this socket.
	*/
	public String getAddress() throws LRException
	{
		return ((NetworkHSocketData)getData()).getAddress();
	}

	/***************************************************************************
		Get the port associated with this socket.
	*/
	public int getPort() throws LRException
	{
		return ((NetworkHSocketData)getData()).getPort();
	}

	public static final int
		MULTICAST=1,
		SERVER=2,
		ADDRESS=0,
		PORT=1;

	/***************************************************************************
		An inner class that manages a socket.
	*/
	class NetworkHSocketData extends LHData implements Runnable
	{
		int mode=0;
		LVValue address=null;
		LVValue port=null;
		int onInputCB=0;
		String receivedData;
		Vector lines=new Vector();
		ServerSocket serverSocket;
		String socketMessage="";
		MulticastSocket msocket=null;
		InetAddress group=null;
		Socket socket;
		DataInputStream in;
		DataOutputStream out;
		Thread runner;
		boolean error=false;

		NetworkHSocketData() {}

		/************************************************************************
			Get the address associated with this socket.
		*/
		String getAddress() throws LRException
		{
			if (address!=null) return address.getStringValue();
			return null;
		}

		/************************************************************************
			Get the port associated with this socket.
		*/
		int getPort() throws LRException
		{
			if (port!=null) return port.getIntegerValue();
			return 17348;
		}

		/************************************************************************
			Open this socket.
		*/
		void open() throws LRException
		{
			error=false;
			try
			{
				switch (mode)
				{
					case MULTICAST:
						if (address==null) address=new LVStringConstant("224.0.0.1");
						if (port==null) port=new LVConstant(17349);
						group=InetAddress.getByName(address.getStringValue());
						msocket=new MulticastSocket(port.getIntegerValue());
						msocket.joinGroup(group);
						runner=new Thread(this);
						runner.start();
						break;
					case SERVER:
						// Open a server socket
						// and wait for incoming connections.
						// This is all done in a single thread, so requests
						// are processed on a first-come, first served basis.
						if (port==null) port=new LVConstant(17348);
						serverSocket=new ServerSocket(port.getIntegerValue());
						new Thread(new Runnable()
						{
							public void run()
							{
								while (true)
								{
									try
									{
										Socket socket=serverSocket.accept();
										socket.setSoTimeout(2000);
										socket.setTcpNoDelay(true);
										DataInputStream dis=new DataInputStream(socket.getInputStream());
										DataOutputStream dos=new DataOutputStream(socket.getOutputStream());
										socketMessage=dis.readUTF();
										if (onInputCB!=0) getProgram().execute(onInputCB);
										else socketMessage="";
										// Now we have the reply from the script
										dos.writeUTF(socketMessage);
										socket.close();
										socketMessage="";
									}
									catch (IOException e) { e.printStackTrace(); }
								}
							}
						}).start();
						break;
					default:
						if (address==null) throw new LRException(NetworkRMessages.noAddressGiven(getName()));
						if (port==null) port=new LVConstant(17348);
						socket=new Socket(address.getStringValue(),port.getIntegerValue());
						socket.setSoTimeout(2000);
						socket.setTcpNoDelay(true);
						in=new DataInputStream(socket.getInputStream());
						out=new DataOutputStream(socket.getOutputStream());
						break;
				}
			}
			catch (IOException e)
			{
				error=true;
			}
		}

		/************************************************************************
			Close this socket.
		*/
		void close()
		{
			if (mode==MULTICAST) msocket.close();
			else if (socket!=null)
			{
				try { socket.close(); }
				catch (IOException ignored) {}
			}
		}

		/************************************************************************
			Set a callback for when data is received.
		*/
		void setOnInputCB(int cb)
		{
			onInputCB=cb;
			receivedData="";
			lines=new Vector();
		}

		/************************************************************************
			Tell the caller if any data has been received
		*/
		boolean hasData()
		{
			return lines.size()>0;
		}

		/************************************************************************
			Tell the caller if an error has occurred
		*/
		boolean hasError()
		{
			boolean e=error;
			error=false;
			return e;
		}

		/************************************************************************
			Set the error status
		*/
		void setError(boolean e)
		{
			error=e;
		}

		/************************************************************************
			Read data from this socket.
		*/
		String read()
		{
			switch (mode)
			{
				case MULTICAST:
					if (lines.size()>0)
					{
						String s=(String)lines.elementAt(0);
						lines.removeElementAt(0);
						return s;
					}
					return "";
				case SERVER:
					return socketMessage;
				default:
					try { return in.readUTF(); }
					catch (IOException e)
					{
//						e.printStackTrace();
						return "";
					}
			}
		}

		/************************************************************************
			Write a string to this socket.
		*/
		void write(String s) throws LRException
		{
			switch (mode)
			{
				case MULTICAST:
					try
					{
						new DatagramSocket().send(new DatagramPacket(
							s.getBytes(),s.length(),group,port.getIntegerValue()));
					}
					catch (SocketException e) { e.printStackTrace(); }
					catch (IOException e) { e.printStackTrace(); }
					break;
				case SERVER:
					socketMessage=s;
					break;
				default:
					if (out!=null)
					{
						try { out.writeUTF(s); }
						catch (IOException e) { e.printStackTrace(); }
					}
			}
		}

		/************************************************************************
			Run the socket, listening for input.
		*/
		public void run()
		{
			if (mode==MULTICAST)
			{
				while (true)
				{
					try
					{
						byte[] buf = new byte[80];
						DatagramPacket packet = new DatagramPacket(buf, buf.length);
						msocket.receive(packet);
						lines.addElement(new String(packet.getData(),0,packet.getLength()));
						getProgram().addQueue(onInputCB,NetworkHSocket.this);
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
	}
}

