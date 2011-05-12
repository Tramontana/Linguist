//	BasicKDispose.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.handler.LHDispose;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
	dispose {variable}
*/
public class BasicKDispose extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
   	if (isSymbol()) return new LHDispose(line,(LHVariableHandler)getHandler());
	   return null;
	}
}

