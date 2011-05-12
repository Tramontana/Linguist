// BasicHClearAll.java

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
	Clear all the elements of a variable.
*/
public class BasicHClearAll extends LHHandler
{
	private LHValueHolder variable;		// the variable to clear

	public BasicHClearAll(int line,LHValueHolder variable)
	{
		super(line);
		this.variable=variable;
	}

	public int execute() throws LRException
	{
		variable.clearAll();
		return pc+1;
	}
}

