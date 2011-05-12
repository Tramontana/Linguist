// BasicHSetReady.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHHandler;

/******************************************************************************
	Set the 'ready' flag in a module.
	The parent 'run' call will hang until this is given.
*/
public class BasicHSetReady extends LHHandler
{
	public BasicHSetReady(int line)
	{
		super(line);
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute()
	{
		program.releaseParent();
		return pc+1;
	}
}

