//	BasicVRight.java

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
	Extract the right part a string.
*/
public class BasicVRight extends LVValue
{
	LVValue value;				// the number of chars to extract
	LVValue string;			// the string to take them from

	public BasicVRight(LVValue string,LVValue value)
	{
		this.string=string;
		this.value=value;
	}

	public long getNumericValue() throws LRException
	{
		return longValue();
	}

	public String getStringValue() throws LRException
	{
		String s=string.getStringValue();
		int n=value.getIntegerValue();
		int length=s.length();
		if (n>length) n=length;
		return s.substring(length-n);
	}
}
