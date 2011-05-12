// NetworkHService.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.handler;

import java.net.InetAddress;
import java.net.UnknownHostException;

import net.eclecity.linguist.handler.LHData;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.network.runtime.NetworkRMessages;
import net.eclecity.linguist.networker.Networker;
import net.eclecity.linguist.networker.NetworkerModule;
import net.eclecity.linguist.networker.NetworkerService;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	A service variable.
	<pre>
	[1.001 GT]  03/05/01  New class.
	</pre>
*/
public class NetworkHService extends LHVariableHandler
{
	public NetworkHService() {}

	public Object newElement(Object extra) { return new ServiceData(); }
	
	/***************************************************************************
		Tell callers this type doesn't return a value.
	*/
	public boolean hasValue() { return false; }
	
	/***************************************************************************
		Create this service.
	*/
	public void create(LVValue name,LVValue type) throws LRException
	{
		try
		{
			Service service=new Service(name.getStringValue(),
				InetAddress.getLocalHost().getHostAddress(),type.getStringValue());
			((ServiceData)getData()).service=service;
			service.service=this;
		}
		catch (UnknownHostException e)
		{
			throw new LRException(NetworkRMessages.cantCreateService(getName()));
		}
	}
	
	/***************************************************************************
		Get this service's module.
	*/
	public NetworkerModule getModule() throws LRException
	{
		return ((ServiceData)getData()).service;
	}
	
	/***************************************************************************
		Add this service to the networker.
	*/
	public void addTo(Networker networker) throws LRException
	{
		Service service=((ServiceData)getData()).service;
		if (service!=null) networker.add(service);
	}
	
	/***************************************************************************
		Specify where to go to handle an event.
	*/
	public void onEvent(int opcode,int where) throws LRException
	{
		ServiceData myData=(ServiceData)getData();
		switch (opcode)
		{
			case ON_TELL:
				myData.service.tellCB=where;
				break;
			case ON_ASK:
				myData.service.askCB=where;
				break;
		}
	}
	
	public static final int
		ON_TELL=1,
		ON_ASK=2;
	
	/***************************************************************************
		An inner class that manages a service.
	*/
	class ServiceData extends LHData
	{
		Service service;
		
		ServiceData() {}
	}
	
	/***************************************************************************
		An inner service implementation.
	*/
	class Service extends NetworkerService
	{
		NetworkHService service=null;
		int tellCB=0;
		int askCB=0;
		ServiceListener listener=null;
		Object data;

		Service(String name,String address,String type)
		{
			super(name,address,type);
		}
		
		/************************************************************************
			Here if we're told something by another module.
		*/
		public void told(NetworkerModule module,String message)
		{
			if (listener!=null) listener.told(module,message);
			else if (tellCB!=0)
			{
				Object[] question=new Object[3];
				question[0]=this;
				question[1]=module;
				question[2]=message;
				program.execute(tellCB,question);
			}
		}
		
		/************************************************************************
			Here if we're asked something by another module.
			It's assumed the script being executed will return a value.
		*/
		public String asked(NetworkerModule module,String message)
		{
			if (listener!=null) return listener.asked(module,message);
			return doAsked(module,message);
		}
		
		protected String doAsked(NetworkerModule module,String message)
		{
			if (askCB!=0)
			{
				Object[] question=new Object[3];
				question[0]=this;
				question[1]=module;
				question[2]=message;
				try
				{
					LVValue reply=program.execute(askCB,question);
					if (reply!=null) return reply.getStringValue();
				}
				catch (LRException e) { return ""; }
			}
			return "";
		}
	}
	
	/***************************************************************************
		A notification listener.
	*/
	interface ServiceListener
	{
		public void told(NetworkerModule module,String message);
		public String asked(NetworkerModule module,String message);
	}
}

