//	BasicKPut.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.BasicLMessages;
import net.eclecity.linguist.basic.handler.BasicHHashtable;
import net.eclecity.linguist.basic.handler.BasicHPut;
import net.eclecity.linguist.basic.handler.BasicHQueue;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHValueHolder;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLMessages;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	<pre>
	put {value} into {value holder}
	put {value} into {hashtable} as {key}
	put {value} into {queue}

	[1.001 GT]  20/10/00  Pre-existing.
	</pre>
*/
public class BasicKPut extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		LVValue value=null;
		try { value=getNextValue(); }
		catch (LLException e) { return null; }
		skip("into");
	   if (isSymbol())
	   {
	      if (getHandler() instanceof BasicHHashtable)
	      {
	      	skip("as");
	      	return new BasicHPut(line,value,(BasicHHashtable)getHandler(),getValue());
	      }
	      if (getHandler() instanceof BasicHQueue)
	      	return new BasicHPut(line,value,(BasicHQueue)getHandler());
	      if (getHandler() instanceof LHValueHolder)
	      	return new BasicHPut(line,value,(LHValueHolder)getHandler());
			warning(this,BasicLMessages.valueHolderExpected(getToken()));
	   }
		warning(this,LLMessages.variableExpected(getToken()));
	   return null;
	}
}

