// BasicHOn.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHModule;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Deal with an event.
*/
public class BasicHOn extends LHHandler
{
	private LHModule module=null;
	private BasicHTimer timer=null;
	private boolean message=false;
	private int next;				// the next instruction

	/***************************************************************************
		Trap a module exit.
	*/
	public BasicHOn(int line,LHModule module,int next)
	{
		super(line);
		this.next=next;
		this.module=module;
	}

	public BasicHOn(int line,BasicHTimer timer,int next)
	{
		super(line);
		this.next=next;
		this.timer=timer;
	}

	public BasicHOn(int line,boolean message,int next)
	{
		super(line);
		this.message=message;
		this.next=next;
	}

	public BasicHOn(int line,int next)
	{
		super(line);
		this.next=next;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		if (module!=null) module.getModule().setExitHandler(getProgram(),pc+1);
		else if (timer!=null) timer.onFire(pc+1);
		else if (message) program.setMessageHandler(pc+1);
		else program.setExternalEventHandler(pc+1);
		return next;
	}
}

