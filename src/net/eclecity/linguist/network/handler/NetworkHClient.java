// NetworkHClient.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.handler;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import net.eclecity.linguist.handler.LHData;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.network.runtime.NetworkRMessages;
import net.eclecity.linguist.networker.Networker;
import net.eclecity.linguist.networker.NetworkerClient;
import net.eclecity.linguist.networker.NetworkerModule;
import net.eclecity.linguist.networker.NetworkerService;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	A client variable.
	<pre>
	[1.001 GT]  03/05/01  New class.
	</pre>
*/
public class NetworkHClient extends LHVariableHandler
{
	public NetworkHClient() {}

	public Object newElement(Object extra) { return new ClientData(); }
	
	/***************************************************************************
		Tell callers this type doesn't return a value.
	*/
	public boolean hasValue() { return false; }
	
	/***************************************************************************
		Create this client.
	*/
	public void create(LVValue name,LVValue type,boolean notifyOnce) throws LRException
	{
		try
		{
			((ClientData)getData()).client=new Client(name.getStringValue(),
				InetAddress.getLocalHost().getHostAddress(),type.getStringValue(),
				this,getTheIndex(),notifyOnce);
		}
		catch (UnknownHostException e)
		{
			throw new LRException(NetworkRMessages.cantCreateClient(getName()));
		}
	}
	
	/***************************************************************************
		Get this client's module.
	*/
	public NetworkerModule getModule() throws LRException
	{
		return ((ClientData)getData()).client;
	}
	
	/***************************************************************************
		Get the service list for this client.
	*/
	public NetworkerService[] getServices() throws LRException
	{
		return (((ClientData)getData()).client).getServices();
	}
	
	/***************************************************************************
		Tell the caller if this client has any services.
	*/
	public boolean hasServices() throws LRException
	{
		return (((ClientData)getData()).client).getServices().length>0;
	}
	
	/***************************************************************************
		Add this client to the networker.
	*/
	public void addTo(Networker networker) throws LRException
	{
		Client client=((ClientData)getData()).client;
		if (client!=null) client.addTo(networker);
	}
	
	/***************************************************************************
		Update this client.
	*/
	public void update() throws LRException
	{
		Client client=((ClientData)getData()).client;
		if (client!=null) client.update();
	}
	
	/***************************************************************************
		Set an attribute of this client.
	*/
	public void set(int opcode,Object data) throws LRException
	{
		ClientData myData=(ClientData)getData();
		switch (opcode)
		{
			case REPORT_IF_NO_SERVICES:
				myData.client.reportIfNoServices=true;
				break;
			default:
				break;
		}
	}
	
	/***************************************************************************
		Specify where to go to handle an event.
	*/
	public void onEvent(int opcode,int where) throws LRException
	{
		ClientData myData=(ClientData)getData();
		if (myData!=null)
		{
			NetworkerClient client=myData.client;
			if (client!=null)
			{
				switch (opcode)
				{
					case ON_TELL:
						myData.client.tellCB=where;
						break;
					case ON_ASK:
						myData.client.askCB=where;
						break;
					case ON_NOTIFY:
						myData.client.notifyCB=where;
						break;
					case ON_WATCHDOG:
						myData.client.setupWatchdog(where);
						break;
				}
			}
		}
	}
	
	public static final int
		ON_TELL=1,
		ON_ASK=2,
		ON_NOTIFY=3,
		ON_WATCHDOG=4;
	
	public static final int
		REPORT_IF_NO_SERVICES=1;
	
	/***************************************************************************
		An inner class that manages a client.
	*/
	class ClientData extends LHData
	{
		Client client;
		
		ClientData() {}
	}
	
	/***************************************************************************
		An inner client implementation.
	*/
	class Client extends NetworkerClient
	{
		Networker networker=null;
		Thread myThread=null;
		int tellCB=0;
		int askCB=0;
		int notifyCB=0;
		int watchdogCB=0;
		ClientListener listener=null;
		NetworkerService service=null;
		int index=0;
		boolean notifyOnce;
		boolean notified=false;
		boolean reportIfNoServices=false;
		NetworkHClient client;

		Client(String name,String address,String type,NetworkHClient client,int index,boolean notifyOnce)
		{
			super(name,address,type);
			this.client=client;
			this.index=index;
			this.notifyOnce=notifyOnce;
		}
		
		public void addTo(Networker networker)
		{
			this.networker=networker;
			networker.add(this);
			if (watchdogCB!=0) networker.setWatchdogListener(this);
		}
		
		public void setupWatchdog(int where)
		{
			watchdogCB=where;
			if (networker!=null) networker.setWatchdogListener(this);
		}
		
		public void update()
		{
			if (networker!=null)
			{
				try { networker.announce(); }
				catch (IOException e) {}
			}
		}
		
		public void newServices()
		{
			if ((this.getServices().length==0) && !reportIfNoServices)
			{
//				println(name+": New Services (0)");
				return;
			}
			if (notifyOnce && notified) return;
			notified=true;
			if (listener!=null) listener.notifyListener();
			else doNotify();
		}
		
		protected void doNotify()
		{
//			println(name+": New Services ("+this.getServices().length+" "+this.getServices()[0].getModuleType()+"): Go to "+notifyCB);
			if (notifyCB!=0) getProgram().execute(notifyCB);
		}
		
		public void triggerWatchdog()
		{
			if (watchdogCB!=0) getProgram().addQueue(watchdogCB);
		}
		
		/************************************************************************
			Here if we're told something by another module.
		*/
		public void told(NetworkerModule module,String message)
		{
			if (tellCB!=0)
			{
				try
				{
					client.setTheIndex(index);
					Object[] question=new Object[3];
				question[0]=this;
				question[1]=module;
				question[2]=message;
					program.execute(tellCB,question);
				}
				catch (LRException e) {}
			}
		}
		
		/************************************************************************
			Here if we're asked something by another module.
			It's assumed the script being executed will return a value.
		*/
		public String asked(NetworkerModule module,String message)
		{
			if (askCB!=0)
			{
				Object[] question=new Object[3];
				question[0]=this;
				question[1]=module;
				question[2]=message;
				try
				{
					return program.execute(askCB,question).getStringValue();
				}
				catch (LRException e) { return ""; }
			}
			return "asked";
		}
	}
	
	/***************************************************************************
		A notification listener.
	*/
	interface ClientListener
	{
		public void notifyListener();
	}
}

