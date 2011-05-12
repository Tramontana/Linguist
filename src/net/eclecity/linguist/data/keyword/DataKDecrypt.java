//	DataKDecrypt.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.data.keyword;

import net.eclecity.linguist.data.DataLMessages;
import net.eclecity.linguist.data.handler.DataHDecrypt;
import net.eclecity.linguist.data.handler.DataHRecord;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Decrypt a string to build a record.
	<pre>
	decrypt {value} to {record}
	<p>
	[1.001 GT]  04/10/00  New class.
	</pre>
*/
public class DataKDecrypt extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   LVValue value=getNextValue();
	   skip("to");
	   if (isSymbol())
		{
			if (getHandler() instanceof DataHRecord)
			{
				return new DataHDecrypt(line,value,(DataHRecord)getHandler());
			}
			warning(this,DataLMessages.recordExpected(getToken()));
		}
	   return null;
	}
}

