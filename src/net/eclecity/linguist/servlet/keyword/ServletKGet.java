//	ServletKGet.java

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
import net.eclecity.linguist.servlet.handler.ServletHCookie;
import net.eclecity.linguist.servlet.handler.ServletHGet;

/******************************************************************************
	get session
	get {cookie} {name}
*/
public class ServletKGet extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		getNextToken();
		if (tokenIs("session")) return new ServletHGet(line);
	   if (isSymbol())
	   {
	   	if (getHandler() instanceof ServletHCookie)
	   	{
		   	return new ServletHGet(line,(ServletHCookie)getHandler(),getNextValue());
	   	}
	   	warning(this,ServletLMessages.cookieExpected(getToken()));
	   }
	   return null;
	}
}

