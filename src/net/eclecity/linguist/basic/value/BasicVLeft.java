//	BasicVLeft.java

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
	Extract the left part a string.
*/
public class BasicVLeft extends LVValue
{
	LVValue value;				// the number of chars to extract
	LVValue string;			// the string to take them from

	public BasicVLeft(LVValue string,LVValue value)
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
		if (n>s.length()) n=s.length();
		return s.substring(0,n);
	}
}
