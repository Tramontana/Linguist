// BasicHOnExternalEvent.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHHandler;

/******************************************************************************
	Handle an external event.
*/
public class BasicHOnExternalEvent extends LHHandler
{
	private int next;

	public BasicHOnExternalEvent(int line,int next)
	{
		super(line);
		this.next=next;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute()
	{
		program.setExternalEventHandler(pc+1);
		return next;
	}
}

