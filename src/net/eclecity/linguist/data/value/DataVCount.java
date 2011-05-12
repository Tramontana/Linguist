//	DataVCount.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.data.value;

import net.eclecity.linguist.data.handler.DataHRecord;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Count how many records are in the set.
*/
public class DataVCount extends LVValue
{
	private DataHRecord record;
	private LVValue query;

	public DataVCount(DataHRecord record,LVValue query)
	{
		this.record=record;
		this.query=query;
	}

	public long getNumericValue() throws LRException
	{
		return record.count(query);
	}

	public String getStringValue() throws LRException
	{
		return String.valueOf(getNumericValue());
	}
}
