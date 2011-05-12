//	BasicVSquare.java

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
	Return the square of a value.
*/
public class BasicVSquare extends LVValue
{
	LVValue value;			// the value to square

	public BasicVSquare(LVValue value)
	{
		this.value=value;
	}

	public long getNumericValue() throws LRException
	{
		long v=value.getNumericValue();
		return v*v;
	}

	public String getStringValue() throws LRException
	{
		return String.valueOf(getNumericValue());
	}
}
