//	BasicVAscii.java

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
	Two-way ASCII conversion.
*/
public class BasicVAscii extends LVValue
{
	LVValue value;		// the value to convert to or from ASCII

	public BasicVAscii(LVValue value)
	{
		this.value=value;
	}

	public long getNumericValue() throws LRException
	{
		return value.getStringValue().charAt(0);
	}

	public String getStringValue() throws LRException
	{
		return ""+(char)value.getNumericValue();
	}
}
