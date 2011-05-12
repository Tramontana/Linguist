//	BasicKAdd.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.BasicLMessages;
import net.eclecity.linguist.basic.handler.BasicHAdd;
import net.eclecity.linguist.basic.handler.BasicHAppend;
import net.eclecity.linguist.basic.handler.BasicHVector;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHStringHolder;
import net.eclecity.linguist.handler.LHValueHolder;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLMessages;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	<pre>
	add {value} to {variable} [giving {variable}]
	add {value} to {buffer}
	add {value} to {vector}

	[1.001 GT]  27/07/00  Pre-existing.
	</pre>
*/
public class BasicKAdd extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	   String token=getToken();
      LVValue value=null;
      try { value=getValue(); }
      catch (LLException e)
      {
		   warning(this,BasicLMessages.valueExpected(token));
		   return null;
      }
      skip("to");
      if (isSymbol())
      {
	      LHHandler variable=getHandler();
	      if (variable instanceof LHStringHolder)
	      	return new BasicHAppend(line,value,(LHStringHolder)variable);
	      if (variable instanceof BasicHVector)
	      	return new BasicHAdd(line,value,(BasicHVector)variable);
	      if (variable instanceof LHValueHolder)
	      {
		      LHValueHolder target=(LHValueHolder)variable;
				getNextToken();
				if (tokenIs("giving"))
				{
					getNextToken();
					if (isSymbol())
					{
				      if (!(getHandler() instanceof LHValueHolder)) inappropriateType();
						target=(LHValueHolder)getHandler();
				   }
				   else throw new LLException(LLMessages.undefinedSymbol(getToken()));
				}
				else unGetToken();
		      return new BasicHAdd(line,value,(LHValueHolder)variable,target);
		   }
		   warning(this,BasicLMessages.notAddableTo(getToken()));
	   }
		warning(this,LLMessages.variableExpected(getToken()));
	   return null;
	}
}

