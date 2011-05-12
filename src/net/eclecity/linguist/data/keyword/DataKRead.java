//	DataKRead.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.data.keyword;

import net.eclecity.linguist.data.DataLMessages;
import net.eclecity.linguist.data.handler.DataHRead;
import net.eclecity.linguist.data.handler.DataHRecord;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
	read xml {record} from {xml file}
*/
public class DataKRead extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		getNextToken();
		if (tokenIs("xml"))
		{
		   getNextToken();
		   if (isSymbol())
			{
				if (getHandler() instanceof DataHRecord)
				{
					DataHRecord record=(DataHRecord)getHandler();
					getNextToken();
					if (tokenIs("from"))
					{
						return new DataHRead(line,record,getNextValue());
					}
				}
				warning(this,DataLMessages.recordExpected(getToken()));
			}
		}
	   return null;
	}
}

