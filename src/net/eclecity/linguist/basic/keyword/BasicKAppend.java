//	BasicKAppend.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.BasicLMessages;
import net.eclecity.linguist.basic.handler.BasicHAppend;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHStringHolder;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLMessages;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	append {value} to {buffer}
	append {value} to file {name}
*/
public class BasicKAppend extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
      LVValue value=getValue();
      skip("to");
      if (tokenIs("file"))
      {
      	return new BasicHAppend(line,value,getNextValue());
      }
      if (isSymbol())
      {
	      LHHandler handler=getHandler();
	      if (handler instanceof LHStringHolder) return new BasicHAppend(line,value,(LHStringHolder)handler);
			warning(this,BasicLMessages.stringHolderExpected(getToken()));
	   }
		warning(this,LLMessages.variableExpected(getToken()));
	   return null;
	}
}

