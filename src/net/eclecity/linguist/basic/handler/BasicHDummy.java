// BasicHDummy.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHHandler;

/******************************************************************************
	A dummy instruction for debugging.
*/
public class BasicHDummy extends LHHandler
{
	public BasicHDummy(int line)
	{
		super(line);
	}

	public int execute()
	{
		return pc+1;
	}
}

