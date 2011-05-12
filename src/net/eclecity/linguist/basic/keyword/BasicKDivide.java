//	BasicKDivide.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.handler.BasicHDivide;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHValueHolder;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLMessages;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	<pre>
	divide {variable} by {value} [giving {variable}]

	[1.001 GT]  27/07/00  Pre-existing.
	</pre>
*/
public class BasicKDivide extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
      if (isSymbol())
      {
	      LHHandler variable=getHandler();
	      if (!(variable instanceof LHValueHolder)) inappropriateType();
	      LHHandler target=variable;
			getNextToken();
	      skip("by");
	      LVValue value=getValue();
	      getNextToken();
			if (tokenIs("giving"))
			{
				getNextToken();
				if (isSymbol())
				{
					target=getHandler();
			      if (!(target instanceof LHValueHolder)) inappropriateType();
			   }
				   else throw new LLException(LLMessages.undefinedSymbol(getToken()));
			}
			else unGetToken();
	      return new BasicHDivide(line,value,(LHValueHolder)variable,(LHValueHolder)target);
	   }
		warning(this,LLMessages.variableExpected(getToken()));
	   return null;
	}
}

