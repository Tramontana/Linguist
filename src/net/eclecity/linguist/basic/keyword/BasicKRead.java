//	BasicKRead.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.handler.BasicHBuffer;
import net.eclecity.linguist.basic.handler.BasicHFile;
import net.eclecity.linguist.basic.handler.BasicHReadFile;
import net.eclecity.linguist.basic.handler.BasicHVariable;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLMessages;

/******************************************************************************
	read [line] {variable}/{buffer} from {file}
	read {buffer} from file {name}
*/
public class BasicKRead extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		boolean wholeLine=false;
	   getNextToken();
	   if (tokenIs("line"))
	   {
	   	wholeLine=true;
	   	getNextToken();
	   }
	  	if (isSymbol())
	  	{
			LHHandler handler=getHandler();
			if (handler instanceof BasicHVariable || handler instanceof BasicHBuffer)
			{
				getNextToken();
			   if (tokenIs("from"))
			   {
			   	getNextToken();
			   	if (tokenIs("file"))
			   	{
			   		if (handler instanceof BasicHBuffer)
			   		{
							// read {buffer} from file {name}
			   			return new BasicHReadFile(line,(BasicHBuffer)handler,getNextValue());
			   		}
			   	}
			   	else if (isSymbol())
			   	{
			   		if (getHandler() instanceof BasicHFile)
							return new BasicHReadFile(line,(BasicHFile)getHandler(),(LHVariableHandler)handler,wholeLine);
						warning(this,LLMessages.inappropriateType(getToken()));
						return null;
					}
			   }
		   	dontUnderstandToken();
		   }
		}
		warning(this,LLMessages.variableExpected(getToken()));
	   return null;
	}
}

