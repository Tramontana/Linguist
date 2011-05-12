//	BasicKStart.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.handler.BasicHStart;
import net.eclecity.linguist.basic.handler.BasicHTimer;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
	start {timer}
*/
public class BasicKStart extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		getNextToken();
		if (isSymbol())
		{
			if (getHandler() instanceof BasicHTimer) return new BasicHStart(line,(BasicHTimer)getHandler());
		}
		return null;
	}
}

