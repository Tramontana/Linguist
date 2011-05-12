// BasicHToggle.java

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
	Toggle (boolean NOT) a variable.
*/
public class BasicHToggle extends LHHandler
{
	private LHValueHolder variable;		// the variable to toggle

	public BasicHToggle(int line,LHValueHolder variable)
	{
		super(line);
		this.variable=variable;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		variable.setValue((variable.getNumericValue()==0)?1:0);
		return pc+1;
	}
}

