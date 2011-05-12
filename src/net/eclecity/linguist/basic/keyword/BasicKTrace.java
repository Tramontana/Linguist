//	BasicKTrace.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.handler.BasicHTrace;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
	trace on/off
*/
public class BasicKTrace extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	   return new BasicHTrace(line,tokenIs("on"));
	}
}

