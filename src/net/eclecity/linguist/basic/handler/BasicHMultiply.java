// BasicHMultiply.java

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
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Multiply a variable by a numeric value [giving another variable].
*/
public class BasicHMultiply extends LHHandler
{
	private LVValue value;					// the value to multiply by
	private LHValueHolder variable;		// the variable to divide
	private LHValueHolder target;			// the variable to put it in

	public BasicHMultiply(int line,LVValue value,LHValueHolder variable,LHValueHolder target)
	{
		super(line);
		this.value=value;
		this.variable=variable;
		this.target=target;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		target.setValue(variable.getNumericValue()*value.getNumericValue());
		return pc+1;
	}
}

