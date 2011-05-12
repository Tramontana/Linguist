//	LVConstant.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.value;

/******************************************************************************
	Hold a numeric constant.
*/
public class LVConstant extends LVValue
{
	private long value;				// the constant value

	public LVConstant(long value)
	{
		this.value=value;
	}

	public long getNumericValue()
	{
		return value;
	}

	public String getStringValue()
	{
		return String.valueOf(value);
	}
}
