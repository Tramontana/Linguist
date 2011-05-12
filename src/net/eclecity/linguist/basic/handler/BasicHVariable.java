// BasicHVariable.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHValueHolder;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	A 64-bit numeric variable.
*/
public class BasicHVariable extends LHVariableHandler implements LHValueHolder
{
	/***************************************************************************
		Create a new object of the correct type for this variable.
	*/
	public Object newElement(Object extra) { return new Long(0); }

	/***************************************************************************
		Implement the LHValueHolder interface.
	*/
	public void setValue(LVValue value) throws LRException
	{
		setData(new Long(value.getNumericValue()));
	}

	public void setValue(long value) throws LRException
	{
		setData(new Long(value));
	}

	public void setValue(String value) throws LRException
	{
		try { setData(new Long(Long.parseLong(value))); }
		catch (NumberFormatException e) {  }
	}

	public long getNumericValue() throws LRException
	{
		return ((Long)getData()).longValue();
	}

	public String getStringValue() throws LRException
	{
		return ((Long)getData()).toString();
	}

	public long getNumericValue(int n) throws LRException
	{
		return ((Long)getData(n)).longValue();
	}

	public String getStringValue(int n) throws LRException
	{
		return ((Long)getData(n)).toString();
	}

	public boolean isNumeric() { return true; }
	////////////////////////////////////////////////////////////////////////////

	/***************************************************************************
		Set a specific element of this variable.
	*/
	public void setValue(int index,long value) throws LRException
	{
		setData(index,new Long(value));
	}

	/***************************************************************************
		Set (boolean) the current element of this variable.
	*/
	public void set() throws LRException { setValue(1); }

	/***************************************************************************
		Clear the current element of this variable.
	*/
	public void clear() throws LRException { setValue(0); }

	/***************************************************************************
		Clear all elements of this variable.
	*/
	public void clearAll() throws LRException
	{
		for (int n=0; n<elements; n++) setData(n,new Long(0));
	}

	/***************************************************************************
		Duplicate this variable.
	*/
	public void duplicate(LHVariableHandler handler)
	{
		Object[] data=handler.getDataArray();
		Long[] newData=new Long[data.length];
		for (int n=0; n<data.length; n++) newData[n]=new Long(((Long)data[n]).longValue());
		setDataArray(newData);
	}
}
