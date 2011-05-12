//	ServletKClear.java

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
import net.eclecity.linguist.servlet.ServletLMessages;
import net.eclecity.linguist.servlet.handler.ServletHClear;
import net.eclecity.linguist.servlet.handler.ServletHElement;
import net.eclecity.linguist.servlet.handler.ServletHTemplate;

/******************************************************************************
	clear doument
	clear {element}
	clear {template}
*/
public class ServletKClear extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	   if (tokenIs("document"))
	   {
	   	return new ServletHClear(line);
	   }
	   if (isSymbol())
	   {
	   	if (getHandler() instanceof ServletHElement)
	   	{
		   	return new ServletHClear(line,(ServletHElement)getHandler());
	   	}
	   	if (getHandler() instanceof ServletHTemplate)
	   	{
		   	return new ServletHClear(line,(ServletHTemplate)getHandler());
	   	}
	   	warning(this,ServletLMessages.elementExpected(getToken()));
	   }
      return null;
	}
}

