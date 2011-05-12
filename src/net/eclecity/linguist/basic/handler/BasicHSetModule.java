// BasicHSetModule.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHModule;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Set a module variable to a running module.
*/
public class BasicHSetModule extends LHHandler
{
	private LHModule module;				// the module to set
	private LVValue value;					// the name of the module

	public BasicHSetModule(int line,LHModule module,LVValue value)
	{
		super(line);
		this.module=module;
		this.value=value;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		if (value==null) module.setModule(getParent());
		else module.setModule(value);
		return pc+1;
	}
}

