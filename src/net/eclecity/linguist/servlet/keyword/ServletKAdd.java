//	ServletKAdd.java

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
import net.eclecity.linguist.servlet.handler.ServletHAdd;
import net.eclecity.linguist.servlet.handler.ServletHCookie;
import net.eclecity.linguist.servlet.handler.ServletHElement;
import net.eclecity.linguist.servlet.handler.ServletHTemplate;
import net.eclecity.linguist.servlet.handler.ServletHUploader;
import net.eclecity.linguist.value.LVStringConstant;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	add {element}/{template}/{text}/break/empty [to document]
	add {element}/{template}/{text}/break/empty to {element}
	add {cookie}
	add type {type} to {uploader}
*/
public class ServletKAdd extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		Object item=null;
	   getNextToken();
	   if (tokenIs("type"))
	   {
	   	LVValue type=getNextValue();
	   	skip("to");
	   	if (isSymbol())
	   	{
	   		if (getHandler() instanceof ServletHUploader)
	   		{
	   			return new ServletHAdd(line,type,(ServletHUploader)getHandler());
	   		}
	   	}
	   	return null;
	   }
	   if (tokenIs("empty")) item=new LVStringConstant("&nbsp;");
	   else if (tokenIs("break")) item=new LVStringConstant("<BR>");
	   else if (isSymbol())
	   {
	   	if (getHandler() instanceof ServletHCookie) return new ServletHAdd(line,(ServletHCookie)getHandler());
	   	if (getHandler() instanceof ServletHElement || getHandler() instanceof ServletHTemplate)
	   		item=getHandler();
	   	else item=getValue();
	   }
	   else item=getValue();
	   getNextToken();
	   if (tokenIs("to"))
	   {
	   	getNextToken();
	   	if (tokenIs("document")) return new ServletHAdd(line,item);
	   	else if (isSymbol())
	   	{
	   		if (getHandler() instanceof ServletHElement)
	   		{
	   			return new ServletHAdd(line,item,(ServletHElement)getHandler());
	   		}
	   	}
	   	dontUnderstandToken();
	   }
	   else
	   {
	   	unGetToken();
	   	return new ServletHAdd(line,item);
	   }
	   return null;
	}
}

