// BasicHIndex.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Set the index of a variable.
*/
public class BasicHIndex extends LHHandler
{
	private LHVariableHandler variable;
	private LVValue value;

	public BasicHIndex(int line,LHVariableHandler variable,LVValue value)
	{
		super(line);
		this.variable=variable;
		this.value=value;
	}

	public int execute() throws LRException
	{
		variable.setTheIndex(value);
		return pc+1;
	}
}

