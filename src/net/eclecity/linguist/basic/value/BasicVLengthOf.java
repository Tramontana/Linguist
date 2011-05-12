//	BasicVLengthOf.java

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
	Extract the length of a string.
*/
public class BasicVLengthOf extends LVValue
{
	LVValue string;			// the string

	public BasicVLengthOf(LVValue string)
	{
		this.string=string;
	}

	public long getNumericValue() throws LRException
	{
		return string.getStringValue().length();
	}

	public String getStringValue() throws LRException
	{
		return String.valueOf(getNumericValue());
	}
}
