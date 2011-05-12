// DataHCreate.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.data.handler;

import java.util.Hashtable;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;


/******************************************************************************
	Create something.
*/
public class DataHCreate extends LHHandler
{
	private DataHRecord record=null;
	private Hashtable fields=null;

	/***************************************************************************
		Create a new table for the given class of record.
	*/
	public DataHCreate(int line,DataHRecord record)
	{
		this.line=line;
		this.record=record;
	}

	/***************************************************************************
		Create a record.
	*/
	public DataHCreate(int line,DataHRecord record,Hashtable fields)
	{
		this.line=line;
		this.record=record;
		this.fields=fields;
	}

	/***************************************************************************
		Runtime execution.
	*/
	public int execute() throws LRException
	{
		if (record!=null)
		{
			if (fields!=null) record.create(fields);
			else record.createTable();
		}
		return pc+1;
	}
}

