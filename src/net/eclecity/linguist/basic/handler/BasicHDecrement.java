// BasicHDecrement.java

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
	Decrement a variable.
	<p><pre>
	[1.001 GT]  27/07/00  Pre-existing.
	</pre>
*/
public class BasicHDecrement extends LHHandler
{
	private LHValueHolder valueHolder=null;

/******************************************************************************
	Construct a BasicHDecrement to decrement a value.
	@param line the program line number
	@param variable the value holder involved
*/
	public BasicHDecrement(int line,LHValueHolder valueHolder)
	{
		super(line);
		this.valueHolder=valueHolder;
	}

/******************************************************************************
	(Runtime) Do it now.
*/
	public int execute() throws LRException
	{
		if (valueHolder!=null) valueHolder.setValue(valueHolder.getNumericValue()-1);
		return pc+1;
	}
}

