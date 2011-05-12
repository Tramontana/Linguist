// LHValueHolder.java

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
	An interface that defines a value holder, which can be numeric or string.
*/
public interface LHValueHolder
{
	/***************************************************************************
		Set the current value of this item.
	*/
	public void setValue(LVValue value) throws LRException;
	public void setValue(long value) throws LRException;
	public void setValue(String value) throws LRException;
	/***************************************************************************
		Get the current value of this item.
	*/
	public long getNumericValue() throws LRException;
	public String getStringValue() throws LRException;
	/***************************************************************************
		Get a specific element of this item.
	*/
	public long getNumericValue(int n) throws LRException;
	public String getStringValue(int n) throws LRException;
	/***************************************************************************
		Return true if this is a numeric value holder.
	*/
	public boolean isNumeric();
	/***************************************************************************
		Clear the contents of this item.
	*/
	public void clear() throws LRException;
	public void clearAll() throws LRException;
}

