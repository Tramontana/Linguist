//	BasicKEcho

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.handler.BasicHEcho;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	echo {string}
*/
public class BasicKEcho extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
      LVValue value=getValue();
      return new BasicHEcho(line,value);
	}
}
