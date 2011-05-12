// BasicHNew.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHHandler;

/******************************************************************************
	Start a new thread.
*/
public class BasicHNew extends LHHandler
{
	/***************************************************************************
		New thread.
	*/
	public BasicHNew(int line)
	{
		super(line);
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute()
	{
		addQueue(pc+1,getQueueData());
		return 0;
	}
}

