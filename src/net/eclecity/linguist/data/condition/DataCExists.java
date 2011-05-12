//	DataCExists.java

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
	Test if this record exists in the database.
	<pre>
	[1.001 GT]  04/10/00  New class.
	</pre>
*/
public class DataCExists extends LCCondition
{
	private DataHRecord record=null;
	private boolean sense;

	public DataCExists(DataHRecord record,boolean sense)
	{
		this.record=record;
		this.sense=sense;
	}

	public boolean test() throws LRException
	{
		return sense?record.exists():!record.exists();
	}
}
