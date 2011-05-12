//	LCCondition.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.condition;

import net.eclecity.linguist.main.LLObject;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	The base class for any condition.
*/
public abstract class LCCondition extends LLObject implements java.io.Serializable
{
	public LCCondition() {}

	public abstract boolean test() throws LRException;
}
