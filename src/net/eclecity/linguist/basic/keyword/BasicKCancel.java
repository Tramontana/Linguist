//	BasicKCancel.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.handler.BasicHCancel;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
   cancel timers
   cancel timer id {value}
*/
public class BasicKCancel extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		getNextToken();
		if (tokenIs("timers")) return new BasicHCancel(line);
		if (tokenIs("timer"))
		{
			getNextToken();
			if (tokenIs("id")) return new BasicHCancel(line,getNextValue());
		}
		return null;
	}
}

