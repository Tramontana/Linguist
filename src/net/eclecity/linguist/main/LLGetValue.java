//	LLGetValue.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.main;

import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	The base class for a value getter.
*/
public abstract class LLGetValue extends LLCMethods
{
	protected boolean numeric=true;

	public boolean isNumeric() { return numeric; }
	public boolean isString() { return !numeric; }

	public abstract LVValue getValue(LLCompiler compiler) throws LLException;
}
