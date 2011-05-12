// DataHUpdate.java

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
	Update (freshen) a record.
	<pre>
	[1.001 GT]  04/10/00  New class.
	</pre>
*/
public class DataHUpdate extends LHHandler
{
	private DataHRecord record=null;

	/***************************************************************************
		Delete all records that match the expression or ID given.
	*/
	public DataHUpdate(int line,DataHRecord record)
	{
		this.line=line;
		this.record=record;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		record.updateRecord();
		return pc+1;
	}
}

