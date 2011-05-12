//	DataVField.java

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
	Get a field from a record.
	<pre>
	[1.001 GT]  12/11/00  Pre-existing.
	</pre>
*/
public class DataVField extends LVValue
{
	private DataHRecord record;
	private LVValue name;

	public DataVField(DataHRecord record,LVValue name)
	{
		this.record=record;
		this.name=name;
	}
	
	public long getNumericValue() throws LRException
	{
	   return longValue();
	}

	public String getStringValue() throws LRException
	{
		return record.getFieldValue(name);
	}
}
