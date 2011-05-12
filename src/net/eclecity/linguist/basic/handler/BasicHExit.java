// BasicHExit.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHHandler;

/******************************************************************************
	Exit from this script.
*/
public class BasicHExit extends LHHandler
{
	public BasicHExit(int line)
	{
		super(line);
	}

	public int execute()
	{
		program.terminate();
		return 0;
	}
}

