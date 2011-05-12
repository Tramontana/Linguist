//	CommsKClose.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.comms.keyword;

import net.eclecity.linguist.comms.CommsLMessages;
import net.eclecity.linguist.comms.handler.CommsHClose;
import net.eclecity.linguist.comms.handler.CommsHPort;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
	close {port}
*/
public class CommsKClose extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	  	if (isSymbol())
	  	{
			LHHandler handler=getHandler();
			if (handler instanceof CommsHPort) return new CommsHClose(line,(CommsHPort)handler);
			warning(this,CommsLMessages.portExpected(getToken()));
		}
	   return null;
	}
}

