// BasicHReset.java

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
	Reset a hashtable or a vector.
*/
public class BasicHReset extends LHHandler
{
	private BasicHHashtable hashtable;
	private BasicHVector vector;

	public BasicHReset(int line,BasicHHashtable hashtable)
	{
		super(line);
		this.hashtable=hashtable;
	}

	public BasicHReset(int line,BasicHVector vector)
	{
		super(line);
		this.vector=vector;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		if (hashtable!=null) hashtable.reset();
		else if (vector!=null) vector.reset();
		return pc+1;
	}
}

