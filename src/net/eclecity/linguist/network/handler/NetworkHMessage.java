// NetworkHMessage.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.handler;

import java.io.IOException;

import net.eclecity.linguist.handler.LHData;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.network.runtime.NetworkRBackground;
import net.eclecity.linguist.networker.Networker;
import net.eclecity.linguist.networker.NetworkerModule;
import net.eclecity.linguist.networker.NetworkerService;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	A message variable.
	<pre>
	[1.001 GT]  03/05/01  New class.
	</pre>
*/
public class NetworkHMessage extends LHVariableHandler
{
	public NetworkHMessage() {}

	public Object newElement(Object extra) { return new MessageData(); }

	private NetworkerModule getModule(LHVariableHandler handler) throws LRException
	{
		if (handler instanceof NetworkHService) return ((NetworkHService)handler).getModule();
		return ((NetworkHClient)handler).getModule();
	}

	/***************************************************************************
		Set an attribute of this message.
	*/
	public void set(int opcode,Object data,Object data2) throws LRException
	{
		MessageData myData=(MessageData)getData();
		if (myData!=null)
		{
			try
			{
				switch (opcode)
				{
					case SET_SOURCE:
						myData.source=getModule((LHVariableHandler)data);
						break;
					case SET_DESTINATION_MODULE:
						myData.dest=getModule((LHVariableHandler)data);
						break;
					case SET_DESTINATION_SERVICE:
						NetworkHClient client=(NetworkHClient)data2;
						NetworkerService[] services=client.getServices();
						int n=((LVValue)data).getIntegerValue();
						if (services!=null && services.length>n)
							myData.dest=services[((LVValue)data).getIntegerValue()];
						break;
					case SET_DESTINATION_SENDER:
						Object[] qData=(Object[])program.getQueueData(Object[].class);
						if (qData!=null && qData.length>0) myData.dest=(NetworkerModule)qData[1];
						break;
					case SET_DESTINATION_TEXT:
						myData.dest=new NetworkerModule(((LVValue)data).getStringValue(),
							((LVValue)data2).getStringValue(),"")
							{ public String asked(NetworkerModule module,String message) { return ""; }};
						break;
					case SET_TEXT:
						myData.message=((LVValue)data).getStringValue();
						break;
					case SET_TIMEOUT:
						myData.timeout=((LVValue)data).getIntegerValue();
						break;
				}
			}
			catch (Exception e) {}
		}
	}

	/***************************************************************************
		Get an attribute of this message.
	*/
	public String get(int opcode) throws LRException
	{
		MessageData myData=(MessageData)getData();
		if (myData!=null)
		{
			switch (opcode)
			{
				case GET_SOURCE_NAME:
					try { return myData.source.getModuleName(); }
					catch (NullPointerException e) { return ""; }
				case GET_DESTINATION_NAME:
					try { return myData.dest.getModuleName(); }
					catch (NullPointerException e) { return ""; }
				case GET_SOURCE_ADDRESS:
					try { return myData.source.getModuleAddress(); }
					catch (NullPointerException e) { return ""; }
				case GET_DESTINATION_ADDRESS:
					try { return myData.dest.getModuleAddress(); }
					catch (NullPointerException e) { return ""; }
				case GET_SOURCE_TYPE:
					try { return myData.source.getModuleType(); }
					catch (NullPointerException e) { return ""; }
				case GET_DESTINATION_TYPE:
					try { return myData.dest.getModuleType(); }
					catch (NullPointerException e) { return ""; }
				case GET_TEXT:
					return myData.message;
			}
		}
		return "";
	}

	/***************************************************************************
		Send this message.
	*/
	public void send(LVValue text) throws LRException
	{
		Networker networker=((NetworkRBackground)getBackground("network")).getNetworker();
		MessageData myData=(MessageData)getData();
		if (myData!=null)
		{
			try
			{
				if (text!=null) myData.message=text.getStringValue();
				myData.error=false;
				networker.tell(myData.source,myData.dest,myData.message);
			}
			catch (IOException e) { myData.error=true; }
		}
	}

	/***************************************************************************
		Send this message and get the reply.
	*/
	public String ask() throws LRException
	{
		Networker networker=((NetworkRBackground)getBackground("network")).getNetworker();
		MessageData myData=(MessageData)getData();
		if (myData!=null)
		{
			try
			{
				myData.error=false;
				return networker.ask(myData.source,myData.dest,myData.message);
			}
			catch (IOException e) { myData.error=true; }
		}
		return "";
	}

	/***************************************************************************
		Test if the send resulted in an error.
	*/
	public boolean hasError() throws LRException
	{
		return ((MessageData)getData()).error;
	}

	/***************************************************************************
		Test if the sender of the last message is the source or destination
		named in this message.
	*/
	public boolean isSender(boolean source) throws LRException
	{
		MessageData myData=(MessageData)getData();
		Object data=program.getQueueData();
		if (data instanceof Object[])
		{
			NetworkerModule sender=(NetworkerModule)((Object[])data)[1];
			String name=sender.getModuleName();
			String address=sender.getModuleAddress();
			if (source)
			{
				if (name.equals(myData.source.getModuleName())
					&& address.equals(myData.source.getModuleAddress())) return true;
			}
			else
			{
				if (name.equals(myData.dest.getModuleName())
					&& address.equals(myData.dest.getModuleAddress())) return true;
			}
		}
		return false;
	}

	public static final int
		SET_SOURCE=1,
		SET_DESTINATION_MODULE=2,
		SET_DESTINATION_SERVICE=3,
		SET_DESTINATION_SENDER=4,
		SET_DESTINATION_TEXT=5,
		SET_TEXT=6,
		SET_TIMEOUT=7,
		GET_SOURCE_NAME=8,
		GET_DESTINATION_NAME=9,
		GET_SOURCE_ADDRESS=10,
		GET_DESTINATION_ADDRESS=11,
		GET_SOURCE_TYPE=12,
		GET_DESTINATION_TYPE=13,
		GET_TEXT=14;

	/***************************************************************************
		An inner class that manages a message.
	*/
	class MessageData extends LHData
	{
		NetworkerModule source;
		NetworkerModule dest;
		String message;
		int timeout;
		boolean error;

		MessageData() {}
	}
}

