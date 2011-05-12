// BasicHWait.java

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

/******************************************************************************
	A wait command.
*/
public class BasicHWait extends LHHandler
{
	private LVValue count;
	private int scale;

	public BasicHWait(int line,LVValue count,int scale)
	{
		super(line);
		this.count=count;
		this.scale=scale;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		program.addTimer(count.getIntegerValue()*scale,pc+1,false);
		return 0;
	}
}

