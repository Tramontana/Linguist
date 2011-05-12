// IoLGetCondition

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.io;

import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.main.LLCompiler;
import net.eclecity.linguist.main.LLGetCondition;

/******************************************************************************
	* Generate	code for a condition:
	*
*/
public class IoLGetCondition extends LLGetCondition
{
	public LCCondition getCondition(LLCompiler c)
	{
		compiler=c;
		return null;
	}
}
