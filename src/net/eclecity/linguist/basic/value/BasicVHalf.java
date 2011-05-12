//	BasicVHalf.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.value;

import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Return half of a value.
*/
public class BasicVHalf extends LVValue
{
	LVValue value;			// the value to halve

	public BasicVHalf(LVValue value)
	{
		this.value=value;
	}

	public long getNumericValue() throws LRException
	{
		return value.getNumericValue()/2;
	}

	public String getStringValue() throws LRException
	{
		return String.valueOf(getNumericValue());
	}
}
