// BasicKPush.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.handler.BasicHPush;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;

/*******************************************************************************
 * push [value] {value}
 */
public class BasicKPush extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		getNextToken();
		if (tokenIs("value"))
		{
			getNextToken();
			return new BasicHPush(line, getValue());
		}
		if (isSymbol()) return new BasicHPush(line,
				(LHVariableHandler) getHandler());
		dontUnderstandToken();
		return null;
	}
}

