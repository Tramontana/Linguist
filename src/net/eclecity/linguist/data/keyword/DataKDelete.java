//	DataKDelete.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.data.keyword;

import net.eclecity.linguist.data.DataLMessages;
import net.eclecity.linguist.data.handler.DataHDelete;
import net.eclecity.linguist.data.handler.DataHRecord;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
	delete {record}
	delete {record} id {value}
	delete {record} using {sql expression}
*/
public class DataKDelete extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	   if (isSymbol())
		{
			if (getHandler() instanceof DataHRecord)
			{
				DataHRecord record=(DataHRecord)getHandler();
				getNextToken();
				if (tokenIs("id"))
				{
					// delete {record} id {value}
					return new DataHDelete(line,record,getNextValue(),true);
				}
				else if (tokenIs("using"))
				{
					// delete {record} using {sql expression}
					return new DataHDelete(line,record,getNextValue(),false);
				}
				else
				{
					unGetToken();
					return new DataHDelete(line,record);
				}
			}
			warning(this,DataLMessages.recordExpected(getToken()));
		}
	   return null;
	}
}

