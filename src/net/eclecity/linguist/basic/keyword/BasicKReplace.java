//	BasicKReplace.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.BasicLMessages;
import net.eclecity.linguist.basic.handler.BasicHReplace;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHStringHolder;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	replace {string} with {string} in {stringholder}
*/
public class BasicKReplace extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	   LVValue string1=getValue();
	   skip("with");
	   LVValue string2=getValue();
	   skip("in");
	   if (isSymbol())
	   {
	   	if (getHandler() instanceof LHStringHolder)
	   	{
	   		return new BasicHReplace(line,(LHStringHolder)getHandler(),string1,string2);
	   	}
			warning(this,BasicLMessages.stringHolderExpected(getToken()));
	   }
	   return null;
	}
}

