// BasicHClear.java

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
	Clear a variable.
	<pre>
	[1.001 GT]  15/02/01  Pre-existing.
	</pre>
*/
public class BasicHClear extends LHHandler
{
	private LHValueHolder variable;		// the variable to clear
	private BasicHHashtable table;
	private BasicHVector vector;

	/*****************************************************************************
		Clear a variable.
	*/
	public BasicHClear(int line,LHValueHolder variable)
	{
		super(line);
		this.variable=variable;
	}

	/*****************************************************************************
		Clear a hashtable.
	*/
	public BasicHClear(int line,BasicHHashtable table)
	{
		super(line);
		this.table=table;
	}

	/*****************************************************************************
		Clear a vector.
	*/
	public BasicHClear(int line,BasicHVector vector)
	{
		super(line);
		this.vector=vector;
	}

	/*****************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		if (variable!=null) variable.clear();
		else if (table!=null) table.clear();
		else if (vector!=null) vector.clear();
		return pc+1;
	}
}

