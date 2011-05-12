//	NetworkKReply.java

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
import net.eclecity.linguist.network.handler.NetworkHReply;
import net.eclecity.linguist.network.handler.NetworkHSocket;
import net.eclecity.linguist.network.handler.NetworkHWrite;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	reply {value} [to {socket}]
*/
public class NetworkKReply extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	   LVValue value=null;
	   try { value=getValue(); } catch (LLException e) { return null; }
	   getNextToken();
	   if (tokenIs("to"))
	   {
	   	getNextToken();
	   	if (isSymbol())
	   	{
	   		if (getHandler() instanceof NetworkHSocket)
					return new NetworkHWrite(line,(NetworkHSocket)getHandler(),value);
				warning(this,NetworkLMessages.socketExpected(getToken()));
			}
			warning(this,NetworkLMessages.socketExpected(getToken()));
		   return null;
		}
		unGetToken();
		return new NetworkHReply(line,value);
	}
}

