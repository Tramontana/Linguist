// BasicHIf.java

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
	Handle an 'if' instruction.
*/
public class BasicHIf extends LHHandler
{
	private LCCondition condition;		// the test
	private int thenTarget;					// then go here
	private int elseTarget;					// else go here

	public BasicHIf(int line,LCCondition condition,int thenTarget,int elseTarget)
	{
		super(line);
		this.condition=condition;
		this.thenTarget=thenTarget;
		this.elseTarget=elseTarget;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		if (condition.test()) return thenTarget;
		return elseTarget;
	}
}

