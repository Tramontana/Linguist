//	BasicKSort.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHSort;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
	sort {array}
*/
public class BasicKSort extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
      if (isSymbol())
      {
	      LHHandler handler=getHandler();
	      return new LHSort(line,(LHVariableHandler)handler);
	   }
      return null;
	}
}

