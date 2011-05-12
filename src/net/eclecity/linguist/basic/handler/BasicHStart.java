// BasicHStart.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Start a timer.
*/
public class BasicHStart extends LHHandler
{
	private BasicHTimer timer;

	public BasicHStart(int line,BasicHTimer timer)
	{
		super(line);
		this.timer=timer;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		timer.start();
		return pc+1;
	}
}

