// BasicHIncrementIndex.java

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

/******************************************************************************
	Increment a variable's index.
	<p><pre>
	[1.001 GT]  27/07/00  Pre-existing.
	</pre>
*/
public class BasicHIncrementIndex extends LHHandler
{
	private LHVariableHandler variable=null;

/******************************************************************************
	Construct a BasicHIncrement to increment a variable's index.
	@param line the program line number
	@param variable the variable involved
*/
	public BasicHIncrementIndex(int line,LHVariableHandler variable)
	{
		super(line);
		this.variable=variable;
	}

/******************************************************************************
	(Runtime) Do it now.
*/
	public int execute() throws LRException
	{
		variable.incrementTheIndex();
		return pc+1;
	}
}

