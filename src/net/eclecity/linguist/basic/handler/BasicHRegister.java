// BasicHRegister.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Register this script.
*/
public class BasicHRegister extends LHHandler
{
	private LVValue name;
	private LVValue number;

	public BasicHRegister(int line,LVValue name,LVValue number)
	{
		super(line);
		this.name=name;
		this.number=number;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		program.register(name.getStringValue(),number.getIntegerValue());
		return pc+1;
	}
}

