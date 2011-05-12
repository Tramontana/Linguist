//	NetworkVAsk.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.value;

import net.eclecity.linguist.network.handler.NetworkHAdvisor;
import net.eclecity.linguist.network.handler.NetworkHMessage;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Get the response from a message.  This causes the message to be sent.
*/
public class NetworkVAsk extends LVValue
{
	private NetworkHMessage message=null;
	private NetworkHAdvisor advisor=null;
	private LVValue question;

	public NetworkVAsk(NetworkHMessage message)
	{
		this.message=message;
	}

	public NetworkVAsk(NetworkHAdvisor advisor,LVValue question)
	{
		this.advisor=advisor;
		this.question=question;
	}

	public long getNumericValue() throws LRException
	{
		return longValue();
	}

	public String getStringValue() throws LRException
	{
		if (message!=null) return message.ask();
		else if (advisor!=null) return advisor.ask(question);
		return "";
	}
}
