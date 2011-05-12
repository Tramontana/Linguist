//	DataCMore.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.data.condition;

import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.data.handler.DataHRecord;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Test if there are more records in the result set held by this variable.
*/
public class DataCMore extends LCCondition
{
	private DataHRecord record=null;

	public DataCMore(DataHRecord record)
	{
		this.record=record;
	}

	public boolean test() throws LRException
	{
		return record.hasMoreRecords();
	}
}
