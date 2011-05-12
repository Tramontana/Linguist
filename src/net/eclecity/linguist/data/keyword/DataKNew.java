//	DataKNew.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.data.keyword;

import net.eclecity.linguist.data.handler.DataHNew;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
	new transaction
*/
public class DataKNew extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
   	if (tokenIs("transaction")) return new DataHNew(line);
	   return null;
	}
}

