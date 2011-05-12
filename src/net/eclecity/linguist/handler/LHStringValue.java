// LHStringValue.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.handler;

import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.util.LUStringReplace;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	A generic string handler.
*/
public abstract class LHStringValue extends LHVariableHandler implements LHStringHolder
{
	public LHStringValue() {}

	/***************************************************************************
		Set the current value of this item.
	*/
	public void setValue(LVValue value) throws LRException { setValue(value.getStringValue()); }
	public void setValue(long value) throws LRException { setValue(Long.toString(value)); }
	public abstract void setValue(String value) throws LRException;
	/***************************************************************************
		Get the current value of this item.
	*/
	public long getNumericValue() throws LRException
	{
		try {return Long.parseLong(getStringValue()); }
		catch (NumberFormatException e) { return 0; }
	}
	public abstract String getStringValue() throws LRException;
	/***************************************************************************
		Get a specific element of this item.
	*/
	public long getNumericValue(int n) throws LRException
	{
		try {return Long.parseLong(getStringValue(n)); }
		catch (NumberFormatException e) { return 0; }
	}
	public abstract String getStringValue(int n) throws LRException;
	/***************************************************************************
		Return true if this is a numeric value holder.
	*/
	public boolean isNumeric() { return false; }
	/***************************************************************************
		Clear the contents of this item.
	*/
	public void clear() throws LRException { setValue(""); }
	public void clearAll() {}
	public void append(LVValue value) throws LRException
	{
		setValue(getStringValue()+value.getStringValue());
	}
	public void replace(LVValue string1,LVValue string2) throws LRException
	{
		new LUStringReplace(this,string1,string2);
	}
	public void trim() throws LRException
	{
		setValue(getStringValue().trim());
	}
}


