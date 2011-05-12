// DataHDecrypt.java

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
	Decrypt a string to build a record.
	<pre>
	[1.001 GT]  04/10/00  New class.
	</pre>
*/
public class DataHDecrypt extends LHHandler
{
	private LVValue value;
	private DataHRecord record;

	public DataHDecrypt(int line,LVValue value,DataHRecord record)
	{
		this.line=line;
		this.value=value;
		this.record=record;
	}

	public int execute() throws LRException
	{
		record.decrypt(value);
		return pc+1;
	}
}

