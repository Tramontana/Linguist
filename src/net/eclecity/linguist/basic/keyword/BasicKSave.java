//	BasicKSave.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.BasicLMessages;
import net.eclecity.linguist.basic.handler.BasicHHashtable;
import net.eclecity.linguist.basic.handler.BasicHSaveTable;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
	save {hashtable} as {file}
*/
public class BasicKSave extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
   	if (isSymbol())
   	{
   		if (getHandler() instanceof BasicHHashtable)
   		{
	   		skip("as");
				return new BasicHSaveTable(line,(BasicHHashtable)getHandler(),getValue());
			}
		}
		warning(this,BasicLMessages.tableExpected(getToken()));
	   return null;
	}
}

