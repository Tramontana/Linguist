//	BasicKRemove.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.BasicLMessages;
import net.eclecity.linguist.basic.handler.BasicHHashtable;
import net.eclecity.linguist.basic.handler.BasicHRemove;
import net.eclecity.linguist.basic.handler.BasicHVector;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHStringHolder;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	remove item {key} of {table}/{vector}
	remove all {table}
	remove the first word/line of {stringholder}
*/
public class BasicKRemove extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	   if (tokenIs("item"))
	   {
	   	getNextToken();
	   	LVValue key=getValue();
	   	skip("of");
	   	if (isSymbol())
	   	{
	   		if (getHandler() instanceof BasicHHashtable)
	   			return new BasicHRemove(line,(BasicHHashtable)getHandler(),key);
	   		if (getHandler() instanceof BasicHVector)
	   			return new BasicHRemove(line,(BasicHVector)getHandler(),key);
	   	}
			warning(this,BasicLMessages.tableExpected(getToken()));
	   }
	   if (tokenIs("all"))
	   {
	   	getNextToken();
	   	if (isSymbol())
	   	{
	   		if (getHandler() instanceof BasicHHashtable)
	   			return new BasicHRemove(line,(BasicHHashtable)getHandler());
	   	}
			warning(this,BasicLMessages.tableExpected(getToken()));
	   }
	   if (tokenIs("the")) getNextToken();
	   if (tokenIs("first"))
	   {
			getNextToken();
			if (tokenIs("word"))
			{
				skip("of");
				if (isSymbol())
				{
					if (getHandler() instanceof LHStringHolder)
					{
						return new BasicHRemove(line,(LHStringHolder)getHandler(),false);
					}
				}
			}
			if (tokenIs("line"))
			{
				skip("of");
				if (isSymbol())
				{
					if (getHandler() instanceof LHStringHolder)
					{
						return new BasicHRemove(line,(LHStringHolder)getHandler(),true);
					}
				}
			}
	   }
	   return null;
	}
}

