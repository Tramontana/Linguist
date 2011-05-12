// BasicHNegate.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHValueHolder;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Negate a variable.
*/
public class BasicHNegate extends LHHandler
{
	private LHValueHolder variable;		// the variable to negate

	public BasicHNegate(int line,LHValueHolder variable)
	{
		super(line);
		this.variable=variable;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		variable.setValue(-variable.getNumericValue());
		return pc+1;
	}
}

