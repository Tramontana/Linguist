//	BasicKLock.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.handler.BasicHLock;
import net.eclecity.linguist.handler.LHFlag;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHNoop;
import net.eclecity.linguist.handler.LHStop;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
	lock {variable} {command}
*/
public class BasicKLock extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		getNextToken();
	  	if (isSymbol())
	  	{
			if (getHandler() instanceof LHVariableHandler)
			{
				LHVariableHandler variable=(LHVariableHandler)getHandler();
				int here=getPC();
				addCommand(new LHNoop(0));
				doKeyword();
				addCommand(new LHStop(line));
				setCommandAt(new BasicHLock(line,variable,getPC()),here);
				return new LHFlag();
			}
	   }
	   return null;
	}
}

