// LHNoop.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.handler;


/******************************************************************************
	No operation.
*/
public class LHNoop extends LHHandler
{
	public LHNoop(int line)
	{
		this.line=line;
	}

	public int execute()
	{
		return pc+1;
	}
}

