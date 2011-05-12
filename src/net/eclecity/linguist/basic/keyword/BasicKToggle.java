//	BasicKToggle.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.handler.BasicHToggle;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHValueHolder;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLMessages;

/******************************************************************************
	toggle {variable}
*/
public class BasicKToggle extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
      if (isSymbol())
      {
	      LHHandler variable=getHandler();
	      if (variable instanceof LHValueHolder) return new BasicHToggle(line,(LHValueHolder)variable);
	   }
		warning(this,LLMessages.variableExpected(getToken()));
	   return null;
	}
}

