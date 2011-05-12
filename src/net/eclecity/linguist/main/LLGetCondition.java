// LLGetCondition

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.main;

import net.eclecity.linguist.condition.LCCondition;

/******************************************************************************
	Base class for a condition getter.
*/
public abstract class LLGetCondition extends LLCMethods
{
	public abstract LCCondition getCondition(LLCompiler c) throws LLException;
}
