// BasicHInvert.java

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
	Invert (ones complement) a variable.
*/
public class BasicHInvert extends LHHandler
{
	private LHValueHolder variable;		// the variable to put it in

	public BasicHInvert(int line,LHValueHolder variable)
	{
		super(line);
		this.variable=variable;
	}

	public int execute() throws LRException
	{
		variable.setValue(variable.getNumericValue()^0xffffffffffffffffL);
		return pc+1;
	}
}

