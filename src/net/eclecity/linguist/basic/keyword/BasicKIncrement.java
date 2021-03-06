//	BasicKIncrement.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.handler.BasicHIncrement;
import net.eclecity.linguist.basic.handler.BasicHIncrementIndex;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHValueHolder;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLMessages;

/******************************************************************************
	<pre>
	increment the index of {variable}
	increment {value holder}

	[1.001 GT]  27/07/00  Pre-existing.
	</pre>
*/
public class BasicKIncrement extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		LHHandler variable=null;
	   getNextToken();
      if (isSymbol())
      {
	      variable=getHandler();
	      if (variable instanceof LHValueHolder)
	      	return new BasicHIncrement(line,(LHValueHolder)variable);
	   }
	   else if (tokenIs("the"))
	   {
	   	getNextToken();
	   	if (tokenIs("index"))
	   	{
	   		skip("of");
      		if (isSymbol())
      		{
	      		variable=getHandler();
	      		if (variable instanceof LHVariableHandler)
	      			return new BasicHIncrementIndex(line,(LHVariableHandler)variable);
	      	}
	      }
	   }
		warning(this,LLMessages.variableExpected(getToken()));
	   return null;
	}
}

