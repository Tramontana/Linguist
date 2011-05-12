//	ServletKPut.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.servlet.keyword;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.servlet.handler.ServletHElement;
import net.eclecity.linguist.servlet.handler.ServletHPut;
import net.eclecity.linguist.value.LVStringConstant;

/******************************************************************************
	put {element}/{text}/break/empty into {element}
*/
public class ServletKPut extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   Object item=null;
	   getNextToken();
	   if (tokenIs("empty")) item=new LVStringConstant("&nbsp;");
	   else if (tokenIs("break")) item=new LVStringConstant("<BR>");
	   else if (isSymbol())
	   {
	   	if (getHandler() instanceof ServletHElement) item=getHandler();
	   	else item=getValue();
	   }
	   else item=getValue();
	   skip("into");
	   if (isSymbol())
	   {
	   	if (getHandler() instanceof ServletHElement)
	   	{
	   		return new ServletHPut(line,item,(ServletHElement)getHandler());
	   	}
	   }
	   return null;
	}
}

