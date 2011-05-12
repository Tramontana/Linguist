// DataHClear.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.data.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Clear a record.
*/
public class DataHClear extends LHHandler
{
	private DataHRecord record=null;

	public DataHClear(int line,DataHRecord record)
	{
		this.line=line;
		this.record=record;
	}

	public int execute() throws LRException
	{
		if (record!=null) record.clear();
		return pc+1;
	}
}

