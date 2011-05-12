//	DataVFields.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.data.value;

import java.util.Hashtable;

import net.eclecity.linguist.data.handler.DataHRecord;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVHashtable;


/******************************************************************************
	Get all the fields from a record.
*/
public class DataVFields extends LVHashtable
{
	private DataHRecord record;

	public DataVFields(DataHRecord record)
	{
		this.record=record;
	}

	public Hashtable getTable()
	{
		try { return record.getFields(); }
		catch (LRException e) {}
		return null;
	}
}
