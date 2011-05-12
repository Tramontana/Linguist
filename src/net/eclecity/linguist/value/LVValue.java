//	LVValue.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.value;

import net.eclecity.linguist.main.LLObject;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	An abstrction of a value.
	<pre>
	[1.000 GT] 01/01/00  Existing class.
	</pre>
*/
public abstract class LVValue extends LLObject implements java.io.Serializable
{
	private boolean numeric=true;

	public LVValue()
	{
	}
	public void setNumeric(boolean flag) { numeric=flag; }
	public boolean isNumeric() { return numeric; }

	public int getIntegerValue() throws LRException
	{
		return (int)getNumericValue();
	}

	public abstract long getNumericValue() throws LRException;
	public abstract String getStringValue() throws LRException;
	
	public Object getBinaryValue() throws LRException		// overridden by some classes
	{
		if (numeric) return new Long(getNumericValue());
		return getStringValue();
	}
	
	protected long longValue() throws LRException { return longValue(getStringValue()); }

	public static long longValue(String s)
	{
		try { return Long.parseLong(s); }
		catch (NumberFormatException e) {}
		return 0;
	}
	
	public String toString()
	{
		try { return getStringValue(); }
		catch (LRException e) {}
		return "";
	}
}
