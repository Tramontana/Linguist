// BasicHWhile.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Handle a 'while' loop.
*/
public class BasicHWhile extends LHHandler
{
	private LCCondition condition;		// the test
	private int next;							// then go here

	public BasicHWhile(int line,LCCondition condition,int next)
	{
		super(line);
		this.condition=condition;
		this.next=next;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		if (!condition.test()) return next;
		return pc+1;
	}
}

