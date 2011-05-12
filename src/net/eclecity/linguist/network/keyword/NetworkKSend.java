//	NetworkKSend.java

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
import net.eclecity.linguist.network.NetworkLMessages;
import net.eclecity.linguist.network.handler.NetworkHMessage;
import net.eclecity.linguist.network.handler.NetworkHSend;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	<pre>
	send {message} [text {text}]
	<p>
	[1.001 GT]  17/02/01  New class.
	</pre>
*/
public class NetworkKSend extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		getNextToken();
		if (isSymbol())
		{
	   	if (getHandler() instanceof NetworkHMessage)
	   	{
	   		NetworkHMessage message=(NetworkHMessage)getHandler();
	   		LVValue text=null;
	   		getNextToken();
	   		if (tokenIs("text")) text=getNextValue();
	   		else unGetToken();
	   		return new NetworkHSend(line,message,text);
			}
			warning(this,NetworkLMessages.messageExpected(getToken()));
      }
      return null;
	}
}

