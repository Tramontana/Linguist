//	NetworkKClose.java

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
import net.eclecity.linguist.network.handler.NetworkHClose;
import net.eclecity.linguist.network.handler.NetworkHSocket;

/******************************************************************************
	close {socket}
*/
public class NetworkKClose extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	  	if (isSymbol())
	  	{
			LHHandler handler=getHandler();
			if (handler instanceof NetworkHSocket) return new NetworkHClose(line,(NetworkHSocket)handler);
			warning(this,NetworkLMessages.socketExpected(getToken()));
		}
	   return null;
	}
}

