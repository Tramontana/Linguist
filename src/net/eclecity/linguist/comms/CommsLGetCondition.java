// CommsLGetCondition

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.comms;

import net.eclecity.linguist.comms.condition.CommsCHasData;
import net.eclecity.linguist.comms.handler.CommsHPort;
import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.main.LLCompiler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLGetCondition;

/******************************************************************************
	* Generate	code for a condition:
	*
	* {port} has data
*/
public class CommsLGetCondition extends LLGetCondition
{
	public LCCondition getCondition(LLCompiler c) throws LLException
	{
		compiler=c;
		getNextToken();
		if (isSymbol())
		{
			if (getHandler() instanceof CommsHPort)
			{
				skip("has");
				if (tokenIs("data"))
				{
					return new CommsCHasData((CommsHPort)getHandler());
				}
				dontUnderstandToken();
			}
		}
		return null;
	}
}
