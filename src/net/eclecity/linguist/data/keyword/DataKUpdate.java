//	DataKUpdate.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.data.keyword;

import net.eclecity.linguist.data.DataLMessages;
import net.eclecity.linguist.data.handler.DataHRecord;
import net.eclecity.linguist.data.handler.DataHUpdate;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
	<pre>
	update {record}
	<p>
	[1.001 GT]  04/10/00  New class.
	</pre>
*/
public class DataKUpdate extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	   if (isSymbol())
		{
			if (getHandler() instanceof DataHRecord)
			{
				DataHRecord record=(DataHRecord)getHandler();
				return new DataHUpdate(line,record);
			}
			warning(this,DataLMessages.recordExpected(getToken()));
		}
	   return null;
	}
}

