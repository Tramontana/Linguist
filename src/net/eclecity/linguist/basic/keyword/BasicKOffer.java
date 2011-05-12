//	BasicKOffer.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.BasicLMessages;
import net.eclecity.linguist.basic.handler.BasicHHashtable;
import net.eclecity.linguist.basic.handler.BasicHOffer;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
	<pre>
	offer {hashtable}

		[1.000 GT] 01/04/03  New class.
	</pre>
*/
public class BasicKOffer extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
      if (isSymbol())
      {
	      LHHandler handler=getHandler();
	      if (handler instanceof BasicHHashtable)
	      	return new BasicHOffer(line,(BasicHHashtable)handler);
			warning(this,BasicLMessages.tableExpected(getToken()));
	   }
      return null;
	}
}

