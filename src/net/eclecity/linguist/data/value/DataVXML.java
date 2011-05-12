//	DataVXML.java

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
	Get the current status.
*/
public class DataVXML extends LVValue
{
	private DataHRecord record;
	private boolean allRecords;

	public DataVXML(DataHRecord record,boolean allRecords)
	{
		this.record=record;
		this.allRecords=allRecords;
	}

	public long getNumericValue() throws LRException
	{
	   return longValue();
	}

	public String getStringValue() throws LRException
	{
		return record.getXML(allRecords);
	}
}
