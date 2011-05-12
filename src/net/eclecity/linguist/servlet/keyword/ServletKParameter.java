//	ServletKParameter.java

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
import net.eclecity.linguist.servlet.handler.ServletHParameter;

/******************************************************************************
	parameter {key} {data}
*/
public class ServletKParameter extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   return new ServletHParameter(line,getNextValue(),getNextValue());
	}
}

