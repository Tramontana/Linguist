//	BasicKOpen.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.BasicLMessages;
import net.eclecity.linguist.basic.handler.BasicHFile;
import net.eclecity.linguist.basic.handler.BasicHOpen;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.value.LVConstant;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	open {file} {name}
	open {file} using dialog {title} [at {left} {top}]
*/
public class BasicKOpen extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	  	if (isSymbol())
	  	{
			LHHandler handler=getHandler();
			if (handler instanceof BasicHFile)
			{
				getNextToken();
			   if (tokenIs("using"))
			   {
			   	getNextToken();
			   	if (tokenIs("dialog"))
			   	{
			   		getNextToken();
			   		LVValue title=getValue();
			   		LVValue left=new LVConstant(100);
			   		LVValue top=new LVConstant(100);
			   		getNextToken();
			   		if (tokenIs("at"))
			   		{
			   			getNextToken();
			   			left=getValue();
			   			getNextToken();
			   			top=getValue();
			   		}
			   		else unGetToken();
			   		return new BasicHOpen(line,(BasicHFile)handler,title,left,top);
			   	}
			   	dontUnderstandToken();
			   }
				return new BasicHOpen(line,(BasicHFile)handler,getValue());
		   }
			warning(this,BasicLMessages.fileExpected(getToken()));
		}
	   return null;
	}
}

