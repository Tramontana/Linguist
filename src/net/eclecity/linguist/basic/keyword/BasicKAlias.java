//	BasicKAlias.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.handler.BasicHAlias;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLMessages;

/******************************************************************************
	alias {variable} to {variable}
*/
public class BasicKAlias extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
   	if (isSymbol())
   	{
   		LHVariableHandler handler=(LHVariableHandler)getHandler();
   		skip("to");
   		if (isSymbol())
   		{
   			if (handler.getClass().equals(getHandler().getClass()))
   				return new BasicHAlias(line,handler,(LHVariableHandler)getHandler());
   			inappropriateType();
   		}
   	}
		warning(this,LLMessages.variableExpected(getToken()));
      return null;
	}
}

