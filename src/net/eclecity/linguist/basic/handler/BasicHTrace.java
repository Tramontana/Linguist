// BasicHTrace.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHHandler;

/******************************************************************************
	Turn the debug tracer on and off.
*/
public class BasicHTrace extends LHHandler
{
	private boolean trace;

	public BasicHTrace(int line,boolean trace)
	{
		super(line);
		this.trace=trace;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute()
	{
		program.setTrace(trace);
		return pc+1;
	}
}

