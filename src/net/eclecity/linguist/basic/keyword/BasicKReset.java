//	BasicKReset.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.BasicLMessages;
import net.eclecity.linguist.basic.handler.BasicHHashtable;
import net.eclecity.linguist.basic.handler.BasicHReset;
import net.eclecity.linguist.basic.handler.BasicHVector;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
	<pre>
	reset {hashtable}
	reset {vector}

	[1.001 GT]  22/05/03  Existing class.
	</pre>
*/
public class BasicKReset extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	   if (isSymbol())
	   {
	   	if (getHandler() instanceof BasicHHashtable)
	   	{
	   		return new BasicHReset(line,(BasicHHashtable)getHandler());
	   	}
	   	if (getHandler() instanceof BasicHVector)
	   	{
	   		return new BasicHReset(line,(BasicHVector)getHandler());
	   	}
	   }
	   warning(this,BasicLMessages.tableExpected(getToken()));
	   return null;
	}
}
