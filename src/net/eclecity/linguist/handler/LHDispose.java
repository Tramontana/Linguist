// LHDispose.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.handler;

import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Dispose of a variable.
*/
public class LHDispose extends LHHandler
{
	private LHVariableHandler variable;

	public LHDispose(int line,LHVariableHandler variable)
	{
		this.line=line;
		this.variable=variable;
	}

	public int execute() throws LRException
	{
		variable.dispose();
		return pc+1;
	}
}

