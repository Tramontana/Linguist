// BasicHSet.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Set something.
*/
public class BasicHSet extends LHHandler
{
	private BasicHVariable variable=null;
	
	/***************************************************************************
		Set a variable.
	*/
	public BasicHSet(int line,BasicHVariable variable)
	{
		super(line);
		this.variable=variable;
	}

	/***************************************************************************
		(Runtime)  Call the appropriate method.
	*/
	public int execute() throws LRException
	{
		if (variable!=null) variable.set();
		return pc+1;
	}
}

