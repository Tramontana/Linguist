// BasicHAlias.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHVariableHandler;

/******************************************************************************
	Set an alias variable.
*/
public class BasicHAlias extends LHHandler
{
	private LHVariableHandler alias;			// the variable to use
	private LHVariableHandler variable;		// the variable to alias it to

	public BasicHAlias(int line,LHVariableHandler alias,LHVariableHandler variable)
	{
		super(line);
		this.alias=alias;
		this.variable=variable;
	}

	public int execute()
	{
		alias.aliasTo(variable);
		return pc+1;
	}
}

