//	DataVEncrypt.java

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
	Encrypt a record to a string.
	<p><pre>
	[1.001 GT]  03/10/00  New class.
	</pre>
*/
public class DataVEncrypt extends LVValue
{
	private DataHRecord record;

	public DataVEncrypt(DataHRecord record)
	{
		this.record=record;
	}

	public long getNumericValue() throws LRException
	{
	   return longValue();
	}

	public String getStringValue() throws LRException
	{
		return record.encrypt();
	}
}
