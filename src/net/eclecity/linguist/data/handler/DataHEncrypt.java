// DataHEncrypt.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.data.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHStringHolder;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Encrypt a record to a string.
	<pre>
	[1.001 GT]  06/10/00  New class.
	</pre>
*/
public class DataHEncrypt extends LHHandler
{
	private DataHRecord record;
	private LHStringHolder string;

	public DataHEncrypt(int line,DataHRecord record,LHStringHolder string)
	{
		this.line=line;
		this.record=record;
		this.string=string;
	}

	public int execute() throws LRException
	{
		record.encrypt(string);
		return pc+1;
	}
}

