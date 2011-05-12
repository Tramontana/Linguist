//	BasicKFork.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.handler.BasicHFork;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
   fork to {label}
*/
public class BasicKFork extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		skip("to");
		checkProgramLabel();
		LHHandler handler=getHandler();
		int pc=(handler!=null)?handler.getPC():0;
		return new BasicHFork(line,pc);
	}
}

