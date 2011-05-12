// DataHInsert.java

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
	Insert a record into the database.
	<pre>
	[1.001 GT]  04/10/00  New class.
	</pre>
*/
public class DataHInsert extends LHHandler
{
	private DataHRecord record=null;

	public DataHInsert(int line,DataHRecord record)
	{
		this.line=line;
		this.record=record;
	}

	public int execute() throws LRException
	{
		if (record!=null) record.insert();
		return pc+1;
	}
}

