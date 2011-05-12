// BasicHLock.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHVariableHandler;

/******************************************************************************
	Lock a command to a variable.
*/
public class BasicHLock extends LHHandler
{
	private LHVariableHandler variable;
	private int next;				// the next instruction

	public BasicHLock(int line,LHVariableHandler variable,int next)
	{
		super(line);
		this.variable=variable;
		this.next=next;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute()
	{
		synchronized(variable)
		{
			program.execute(pc+1,program.getQueueData());
		}
		return next;
	}
}

