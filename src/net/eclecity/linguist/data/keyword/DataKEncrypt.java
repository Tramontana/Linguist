//	DataKEncrypt.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.data.keyword;

import net.eclecity.linguist.data.DataLMessages;
import net.eclecity.linguist.data.handler.DataHEncrypt;
import net.eclecity.linguist.data.handler.DataHRecord;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHStringHolder;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
	Encrypt a database record to a string.
	<pre>
	encrypt {record} to {stringholder}
	<p>
	[1.001 GT]  06/10/00  New class.
	</pre>
*/
public class DataKEncrypt extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	   if (isSymbol())
		{
			if (getHandler() instanceof DataHRecord)
			{
				DataHRecord handler=(DataHRecord)getHandler();
				skip("to");
				if (isSymbol())
				{
					if (getHandler() instanceof LHStringHolder)
					{
						return new DataHEncrypt(line,handler,(LHStringHolder)getHandler());
					}
				}
				throw new LLException(DataLMessages.stringHolderExpected(getToken()));
			}
			throw new LLException(DataLMessages.recordExpected(getToken()));
		}
	   return null;
	}
}

