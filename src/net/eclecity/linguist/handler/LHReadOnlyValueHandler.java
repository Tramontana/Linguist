// LHReadOnlyValueHandler.java

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
	A base class for a read-only variable.
*/
public abstract class LHReadOnlyValueHandler extends LHVariableHandler
	implements LHValueHolder
{
	public LHReadOnlyValueHandler() {}

	/***************************************************************************
		Set the current value of this item.  Non-functional.
	*/
	public void setValue(LVValue value) {}
	public void setValue(long value) {}
	public void setValue(String value) {}
	/***************************************************************************
		Get the current value of this item.
	*/
	public abstract long getNumericValue() throws LRException;
	public abstract String getStringValue() throws LRException;
	/***************************************************************************
		Get a specific element of this item.
	*/
	public abstract long getNumericValue(int n) throws LRException;
	public abstract String getStringValue(int n) throws LRException;
	/***************************************************************************
		Return true if this is a numeric value holder.
	*/
	public abstract boolean isNumeric();
	/***************************************************************************
		Clear the contents of this item.  Non-functional.
	*/
	public void clear() {}
	public void clearAll() {}
}
