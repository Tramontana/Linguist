//	BasicKGo.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.handler.LHGoto;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
   go to {label}
*/
public class BasicKGo extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		skip("to");
		checkProgramLabel();
		int pc=getLabelValue();
		return new LHGoto(line,pc);
	}
}

