//	NetworkLGetValue.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network;

import net.eclecity.linguist.main.LLCompiler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLGetValue;
import net.eclecity.linguist.main.LLMessages;
import net.eclecity.linguist.network.handler.NetworkHAdvisor;
import net.eclecity.linguist.network.handler.NetworkHClient;
import net.eclecity.linguist.network.handler.NetworkHConnection;
import net.eclecity.linguist.network.handler.NetworkHMessage;
import net.eclecity.linguist.network.handler.NetworkHRules;
import net.eclecity.linguist.network.value.NetworkVAsk;
import net.eclecity.linguist.network.value.NetworkVConnection;
import net.eclecity.linguist.network.value.NetworkVMessage;
import net.eclecity.linguist.network.value.NetworkVMessageAddress;
import net.eclecity.linguist.network.value.NetworkVMessageName;
import net.eclecity.linguist.network.value.NetworkVMessageText;
import net.eclecity.linguist.network.value.NetworkVProperty;
import net.eclecity.linguist.network.value.NetworkVSenderInfo;
import net.eclecity.linguist.network.value.NetworkVService;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Get a network value.
	<pre>
	[1.001 GT]  12/02/01  Pre-existing.
	</pre>
*/
public class NetworkLGetValue extends LLGetValue
{
	public boolean isNumeric() { return numeric; }
	public boolean isString() { return !numeric; }

	/********************************************************************
		Create a numeric or string value.

	   <connection>
	   <socket>
	   ask {message}
	   ask {advisor} {question}
	   the service count of {client}
	   the name/address of service {count} of {client}
	   the name/address/text of the message
	   the name/address/type of {message} source/destination
	   the name/address/type of the sender
	   the text of {message}
	   the query
	   property {key} of (rules}
	*/

	public LVValue getValue(LLCompiler compiler) throws LLException
	{
		this.compiler=compiler;
		if (isSymbol())
		{
			if (getHandler() instanceof NetworkHConnection)
			{
				numeric=false;
				return new NetworkVConnection((NetworkHConnection)getHandler());
			}
	   }
	   if (tokenIs("ask"))
	   {
	   	getNextToken();
	   	if (isSymbol())
	   	{
				if (getHandler() instanceof NetworkHMessage)
				{
					numeric=false;
					return new NetworkVAsk((NetworkHMessage)getHandler());
				}
				if (getHandler() instanceof NetworkHAdvisor)
				{
					numeric=false;
					return new NetworkVAsk((NetworkHAdvisor)getHandler(),getNextValue());
				}
	   	}
	   	warning(this,NetworkLMessages.messageExpected(getToken()));
	   	return null;
	   }
	   if (tokenIs("the")) getNextToken();
	   if (tokenIs("service"))
	   {
	   	getNextToken();
	   	if (tokenIs("count"))
	   	{
	   		skip ("of");
	   		if (isSymbol())
	   		{
	   			if (getHandler() instanceof NetworkHClient)
			   		return new NetworkVService(NetworkVService.COUNT,(NetworkHClient)getHandler());
			   }
			   throw new LLException(NetworkLMessages.clientExpected(getToken()));
			}
			warning(this,LLMessages.dontUnderstand(getToken()));
			return null;
	   }
	   if (tokenIs("text"))
	   {
			numeric=false;
	   	skip("of");
	   	if (isSymbol())
	   	{
	   		if (getHandler() instanceof NetworkHMessage)
	   		{
	   			return new NetworkVMessage((NetworkHMessage)getHandler(),NetworkHMessage.GET_TEXT);
	   		}
	   		warning(this,NetworkLMessages.messageExpected(getToken()));
	   		return null;
	   	}
	   	if (tokenIs("the")) getNextToken();
	   	if (tokenIs("message")) return new NetworkVMessageText(getProgram());
			return null;
	   }
	   if (tokenIs("query"))
	   {
			numeric=false;
	   	return new NetworkVMessageText(getProgram());
	   }
	   if (tokenIs("name"))
	   {
			numeric=false;
	   	skip("of");
	   	if (tokenIs("service"))
	   	{
	   		LVValue count=getNextValue();
	   		skip("of");
	   		if (isSymbol())
	   		{
	   			if (getHandler() instanceof NetworkHClient)
	   			{
	   				return new NetworkVService(NetworkVService.NAME,count,(NetworkHClient)getHandler());
	   			}
	   		}
	   		dontUnderstandToken();
	   	}
	   	if (isSymbol())
	   	{
	   		if (getHandler() instanceof NetworkHMessage)
	   		{
	   			getNextToken();
	   			if (tokenIs("source"))
	   				return new NetworkVMessage((NetworkHMessage)getHandler(),NetworkHMessage.GET_SOURCE_NAME);
	   			if (tokenIs("destination"))
	   				return new NetworkVMessage((NetworkHMessage)getHandler(),NetworkHMessage.GET_DESTINATION_NAME);
	   			dontUnderstandToken();
	   		}
	   	}
	   	if (tokenIs("the"))
	   	{
	   		getNextToken();
	   		if (tokenIs("message")) return new NetworkVMessageName(getProgram());
	   		if (tokenIs("sender")) return new NetworkVSenderInfo(getProgram(),NetworkVSenderInfo.NAME);
	   	}
			return null;
	   }
	   if (tokenIs("address"))
	   {
			numeric=false;
	   	skip("of");
	   	if (tokenIs("service"))
	   	{
	   		LVValue count=getNextValue();
	   		skip("of");
	   		if (isSymbol())
	   		{
	   			if (getHandler() instanceof NetworkHClient)
	   			{
	   				return new NetworkVService(NetworkVService.ADDRESS,count,(NetworkHClient)getHandler());
	   			}
	   		}
	   		dontUnderstandToken();
	   	}
	   	if (isSymbol())
	   	{
	   		if (getHandler() instanceof NetworkHMessage)
	   		{
	   			getNextToken();
	   			if (tokenIs("source"))
	   				return new NetworkVMessage((NetworkHMessage)getHandler(),NetworkHMessage.GET_SOURCE_ADDRESS);
	   			if (tokenIs("destination"))
	   				return new NetworkVMessage((NetworkHMessage)getHandler(),NetworkHMessage.GET_DESTINATION_ADDRESS);
	   			dontUnderstandToken();
	   		}
	   	}
	   	if (tokenIs("the"))
	   	{
	   		getNextToken();
	   		if (tokenIs("message")) return new NetworkVMessageAddress(getProgram());
	   		if (tokenIs("sender")) return new NetworkVSenderInfo(getProgram(),NetworkVSenderInfo.ADDRESS);
	   	}
			return null;
	   }
	   if (tokenIs("type"))
	   {
			numeric=false;
	   	skip("of");
	   	if (tokenIs("service"))
	   	{
	   		LVValue count=getNextValue();
	   		skip("of");
	   		if (isSymbol())
	   		{
	   			if (getHandler() instanceof NetworkHClient)
	   			{
	   				return new NetworkVService(NetworkVService.TYPE,count,(NetworkHClient)getHandler());
	   			}
	   		}
	   		dontUnderstandToken();
	   	}
	   	if (isSymbol())
	   	{
	   		if (getHandler() instanceof NetworkHMessage)
	   		{
	   			getNextToken();
	   			if (tokenIs("source"))
	   				return new NetworkVMessage((NetworkHMessage)getHandler(),NetworkHMessage.GET_SOURCE_TYPE);
	   			if (tokenIs("destination"))
	   				return new NetworkVMessage((NetworkHMessage)getHandler(),NetworkHMessage.GET_DESTINATION_TYPE);
	   			dontUnderstandToken();
	   		}
	   	}
	   	if (tokenIs("the"))
	   	{
	   		getNextToken();
	   		if (tokenIs("message")) return new NetworkVMessageAddress(getProgram());
	   		if (tokenIs("sender")) return new NetworkVSenderInfo(getProgram(),NetworkVSenderInfo.TYPE);
	   	}
			return null;
	   }
	   if (tokenIs("property"))
	   {
	   	LVValue key=getNextValue();
	   	skip("of");
	   	if (isSymbol())
	   	{
	   		if (getHandler() instanceof NetworkHRules)
	   		{
	   			return new NetworkVProperty((NetworkHRules)getHandler(),key);
	   		}
	   	}
	   	return null;
	   }
	   return null;
	}
}
