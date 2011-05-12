// BasicHCancel.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/********************************************************************************
	Cancel all timers or one timer by ID.
*/
public class BasicHCancel extends LHHandler
{
	private LVValue id=null;

	/*****************************************************************************
		Cancel all timers.
	*/
	public BasicHCancel(int line)
	{
		super(line);
	}

	/*****************************************************************************
		Cancel a timer by ID.
	*/
	public BasicHCancel(int line,LVValue id)
	{
		super(line);
		this.id=id;
	}

	/*****************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		if (id!=null) BasicHTimer.cancel(program,id);
		else program.cancelTimers();
		return pc+1;
	}
}

