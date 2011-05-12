// BasicHork.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHHandler;

/******************************************************************************
	A fork instruction.
*/
public class BasicHFork extends LHHandler
{
	private int target;		// where to go to

	public BasicHFork(int line,int target)
	{
		super(line);
		this.target=target;
	}

	public int execute()
	{
		program.addQueue(pc+1,null);
		return target;
	}
}

