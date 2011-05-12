//	NetworkKSet.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.keyword;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLMessages;
import net.eclecity.linguist.network.NetworkLMessages;
import net.eclecity.linguist.network.handler.NetworkHAdvisor;
import net.eclecity.linguist.network.handler.NetworkHClient;
import net.eclecity.linguist.network.handler.NetworkHConnection;
import net.eclecity.linguist.network.handler.NetworkHDoNetworker;
import net.eclecity.linguist.network.handler.NetworkHMessage;
import net.eclecity.linguist.network.handler.NetworkHService;
import net.eclecity.linguist.network.handler.NetworkHSet;
import net.eclecity.linguist.network.handler.NetworkHSocket;
import net.eclecity.linguist.network.runtime.NetworkRBackground;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	<pre>
	set the url of {connection} to {url}
	set property {name} of {connection}/{advisor} to {value}
	set the mode of {connection} to get/post
	set the mode of {socket} to multicast/server
	set the address of {socket} to {address}
	set the port of {socket} to {port}
	set the source of {message} to {module}
	set the destination of {message} to {module}/service {n} of {client}
	set the destination of {message} to the sender
	set the destination of {message} to {name} {address}
	set the timeout of {message} to {value}
	set the text of {message} to {text}
	set the network name to {name}
	set {client} to report if no services
	<p>
	[1.002 GT]  08/05/01  Message functions.
	[1.001 GT]  12/02/01  Pre-existing.
	</pre>
*/
public class NetworkKSet extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		getNextToken();
	   if (tokenIs("the")) getNextToken();
	   if (tokenIs("url"))
	   {
			// set the url of {connection} to {url}
			skip("of");
			if (isSymbol())
			{
				if (getHandler() instanceof NetworkHConnection)
				{
					skip("to");
					return new NetworkHSet(line,(NetworkHConnection)getHandler(),getValue());
				}
			}
			warning(this,NetworkLMessages.connectionExpected(getToken()));
	   }
	   else if (tokenIs("property"))
	   {
			// set property {name} of {connection}/{advisor} to {value}
			LVValue name=getNextValue();
			skip("of");
			if (isSymbol())
			{
				if (getHandler() instanceof NetworkHConnection)
				{
					skip("to");
					return new NetworkHSet(line,(NetworkHConnection)getHandler(),name,getValue());
				}
				if (getHandler() instanceof NetworkHAdvisor)
				{
					skip("to");
					return new NetworkHSet(line,(NetworkHAdvisor)getHandler(),name,getValue());
				}
			}
			warning(this,NetworkLMessages.connectionExpected(getToken()));
	   }
	   else if (tokenIs("mode"))
	   {
			// set the mode of {connection} to get/post
			// set the mode of {socket} to server
			skip("of");
			if (isSymbol())
			{
				if (getHandler() instanceof NetworkHConnection)
				{
					skip("to");
					if (tokenIs("get"))
						return new NetworkHSet(line,(NetworkHConnection)getHandler(),NetworkHConnection.GET);
					else if (tokenIs("post"))
						return new NetworkHSet(line,(NetworkHConnection)getHandler(),NetworkHConnection.POST);
				}
				if (getHandler() instanceof NetworkHSocket)
				{
					skip("to");
					if (tokenIs("server"))
						return new NetworkHSet(line,(NetworkHSocket)getHandler(),NetworkHSocket.SERVER);
				}
			}
			warning(this,NetworkLMessages.connectionExpected(getToken()));
	   }
	   else if (tokenIs("address"))
	   {
			// set the address of {socket} to {address}
			skip("of");
			if (isSymbol())
			{
				if (getHandler() instanceof NetworkHSocket)
				{
					skip("to");
					return new NetworkHSet(line,(NetworkHSocket)getHandler(),getValue(),NetworkHSocket.ADDRESS);
				}
			}
			warning(this,NetworkLMessages.connectionExpected(getToken()));
	   }
	   else if (tokenIs("port"))
	   {
			// set the port of {socket} to {port}
			skip("of");
			if (isSymbol())
			{
				if (getHandler() instanceof NetworkHSocket)
				{
					skip("to");
					return new NetworkHSet(line,(NetworkHSocket)getHandler(),getValue(),NetworkHSocket.PORT);
				}
			}
			warning(this,NetworkLMessages.socketExpected(getToken()));
	   }
	   else if (tokenIs("source"))
	   {
			// set the source of {message} to {module}
			skip("of");
			if (isSymbol())
			{
				if (getHandler() instanceof NetworkHMessage)
				{
					NetworkHMessage message=(NetworkHMessage)getHandler();
					skip("to");
					if (isSymbol())
					{
						if (getHandler() instanceof NetworkHService || getHandler() instanceof NetworkHClient)
							return new NetworkHSet(line,message,NetworkHMessage.SET_SOURCE,getHandler());
					}
				}
			}
			warning(this,NetworkLMessages.messageExpected(getToken()));
	   }
	   else if (tokenIs("destination"))
	   {
			// set the destination of {message} to {module}/service {n} of {client}
			// set the destination of {message} to the sender
			// set the destination of {message} to {name} {address}
			skip("of");
			if (isSymbol())
			{
				if (getHandler() instanceof NetworkHMessage)
				{
					NetworkHMessage message=(NetworkHMessage)getHandler();
					skip("to");
					if (isSymbol())
					{
						if (getHandler() instanceof NetworkHService || getHandler() instanceof NetworkHClient)
							return new NetworkHSet(line,message,NetworkHMessage.SET_DESTINATION_MODULE,getHandler());
					}
					if (tokenIs("service"))
					{
						LVValue serviceNumber=getNextValue();
						skip("of");
						if (isSymbol())
						{
							if (getHandler() instanceof NetworkHClient)
								return new NetworkHSet(line,message,NetworkHMessage.SET_DESTINATION_SERVICE,
									serviceNumber,getHandler());
						}
						throw new LLException(NetworkLMessages.clientExpected(getToken()));
					}
					if (tokenIs("the"))
					{
						getNextToken();
						if (tokenIs("sender"))
						{
							return new NetworkHSet(line,message,NetworkHMessage.SET_DESTINATION_SENDER,null);
						}
					}
					return new NetworkHSet(line,message,NetworkHMessage.SET_DESTINATION_TEXT,getValue(),getNextValue());
				}
				warning(this,NetworkLMessages.serviceOrClientExpected(getToken()));
				return null;
			}
			warning(this,NetworkLMessages.messageExpected(getToken()));
	   }
	   else if (tokenIs("text"))
	   {
			// set the text of {message} to {text}
			skip("of");
			if (isSymbol())
			{
				if (getHandler() instanceof NetworkHMessage)
				{
					skip("to");
					return new NetworkHSet(line,(NetworkHMessage)getHandler(),NetworkHMessage.SET_TEXT,getValue());
				}
			}
			warning(this,NetworkLMessages.messageExpected(getToken()));
	   }
	   else if (tokenIs("timeout"))
	   {
			// set the timeout of {message} to {value}
			skip("of");
			if (isSymbol())
			{
				if (getHandler() instanceof NetworkHMessage)
				{
					skip("to");
					return new NetworkHSet(line,(NetworkHMessage)getHandler(),NetworkHMessage.SET_TIMEOUT,getValue());
				}
			}
			warning(this,NetworkLMessages.messageExpected(getToken()));
	   }
	   else if (tokenIs("network"))
	   {
	   	getNextToken();
	   	if (tokenIs("name"))
	   	{
				// set the network name to {name}
				skip("to");
				return new NetworkHDoNetworker(line,NetworkRBackground.SET_NETWORK_NAME,getValue());
			}
			warning(this,LLMessages.dontUnderstand(getToken()));
	   }
	   else if (isSymbol())
	   {
	   	if (getHandler() instanceof NetworkHClient)
	   	{
	   		// set {client} to report if no services
	   		skip("to");
	   		if (tokenIs("report"))
	   		{
	   			getNextToken();
	   			if (tokenIs("if"))
	   			{
	   				getNextToken();
	   				if (tokenIs("no"))
	   				{
		   				if (tokenIs("services"))
		   				{
		   					return new NetworkHSet(line,(NetworkHClient)getHandler(),NetworkHClient.REPORT_IF_NO_SERVICES,null);
		   				}
		   			}
	   			}
	   		}
	   		dontUnderstandToken();
	   	}
			warning(this,NetworkLMessages.clientExpected(getToken()));
	   }
      return null;
	}
}

