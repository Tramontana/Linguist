// LHStringHolder.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.handler;

import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	An interface that defines a string value holder.
*/
public interface LHStringHolder extends LHValueHolder
{
	public void append(LVValue value) throws LRException;
	public void replace(LVValue string1,LVValue string2) throws LRException;
	public void trim() throws LRException;
}

