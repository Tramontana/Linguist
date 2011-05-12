//	BasicKReturn.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.handler.BasicHReturn;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHLabel;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
	return
*/
public class BasicKReturn extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		getNextToken();
		if (isSymbol())
		{
			if (!(getHandler() instanceof LHLabel)) return null;
		}
		unGetToken();
      return new BasicHReturn(line);
	}
}

