//	CommsKOn.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.comms.keyword;

import net.eclecity.linguist.comms.handler.CommsHOn;
import net.eclecity.linguist.comms.handler.CommsHPort;
import net.eclecity.linguist.handler.LHFlag;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHNoop;
import net.eclecity.linguist.handler.LHStop;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
	on {port} {block}
*/
public class CommsKOn extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	   if (isSymbol())
		{
			if (getHandler() instanceof CommsHPort)
			{
				// on {port} {block}
				CommsHPort port=(CommsHPort)getHandler();
				int here=getPC();
				addCommand(new LHNoop(0));
				doKeyword();
				addCommand(new LHStop(line));
				setCommandAt(new CommsHOn(line,port,getPC()),here);
				return new LHFlag();
			}
		}
		return null;
	}
}

