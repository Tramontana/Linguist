//	BasicKGosub.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.handler.BasicHGosub;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
   gosub to {label}
*/
public class BasicKGosub extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		skip("to");
		checkProgramLabel();
		LHHandler handler=getHandler();
		int pc=(handler!=null)?handler.getPC():0;
		return new BasicHGosub(line,pc);
	}
}

