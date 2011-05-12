//	BasicKIndex.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.handler.BasicHIndex;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
	index {variable} to {value}
*/
public class BasicKIndex extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	   if (isSymbol())
	   {
	   	LHVariableHandler handler=(LHVariableHandler)getHandler();
	   	skip("to");
	   	return new BasicHIndex(line,handler,getValue());
	   }
      return null;
	}
}

