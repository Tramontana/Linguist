//	LVStringConstant.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.value;


/******************************************************************************
	Hold a string constant.
*/
public class LVStringConstant extends LVValue
{
	String value;				// the string value

	public LVStringConstant(String value)
	{
		this.value=value;
		setNumeric(false);
	}

	public long getNumericValue()
	{
		try { return Long.parseLong(value); }
		catch (NumberFormatException e) { return 0; }
	}

	public String getStringValue()
	{
		return value;
	}
}
