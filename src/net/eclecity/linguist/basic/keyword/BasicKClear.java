//	BasicKClear.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.BasicLMessages;
import net.eclecity.linguist.basic.handler.BasicHClear;
import net.eclecity.linguist.basic.handler.BasicHClearAll;
import net.eclecity.linguist.basic.handler.BasicHHashtable;
import net.eclecity.linguist.basic.handler.BasicHVector;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHValueHolder;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
	clear [all] {variable}/{buffer}/{hashtable}/{vector}
*/
public class BasicKClear extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	   if (tokenIs("all"))
	   {
		   getNextToken();
	      if (isSymbol())
	      {
		      LHHandler variable=getHandler();
		      if (variable instanceof LHValueHolder) return new BasicHClearAll(line,(LHValueHolder)variable);
				warning(this,BasicLMessages.valueHolderExpected(getToken()));
		   }
			return null;
		}
      if (isSymbol())
      {
	      LHHandler handler=getHandler();
	      if (handler instanceof LHValueHolder) return new BasicHClear(line,(LHValueHolder)handler);
	      if (handler instanceof BasicHHashtable) return new BasicHClear(line,(BasicHHashtable)handler);
	      if (handler instanceof BasicHVector) return new BasicHClear(line,(BasicHVector)handler);
			warning(this,BasicLMessages.valueHolderExpected(getToken()));
	   }
      return null;
	}
}

