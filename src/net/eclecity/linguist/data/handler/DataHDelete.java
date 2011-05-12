// DataHDelete.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.data.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Delete a record from the database.
*/
public class DataHDelete extends LHHandler
{
	private DataHRecord record=null;
	private LVValue value=null;
	private boolean useID=false;
	private boolean useSQL=false;

	/***************************************************************************
		Delete all records that match the expression or ID given.
	*/
	public DataHDelete(int line,DataHRecord record,LVValue value,boolean flag)
	{
		this.line=line;
		this.record=record;
		this.value=value;
		if (flag) useID=true;
		else useSQL=true;
	}

	/***************************************************************************
		Delete the current record.
	*/
	public DataHDelete(int line,DataHRecord record)
	{
		this.line=line;
		this.record=record;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		if (record!=null)
		{
			if (value!=null)
			{
				if (useID) record.deleteRecord(value);
				else if (useSQL) record.deleteRecords(value);
			}
			else record.deleteRecord();
		}
		return pc+1;
	}
}

