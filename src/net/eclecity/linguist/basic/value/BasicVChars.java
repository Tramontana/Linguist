//	BasicVChars.java

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
	Extract a range of characters from a string.
	Parameter 'start' is the first character, 'end' is the last.
	If 'start' is -1, take the last character.
	If 'end' is -1 take everything from 'start'.
	If 'end' is 0, take just one character.
*/
public class BasicVChars extends LVValue
{
	LVValue start;				// the start of the range
	LVValue end;				// the end of the range
	LVValue string;			// the string to take them from

	public BasicVChars(LVValue string,LVValue start,LVValue end)
	{
		this.string=string;
		this.start=start;
		this.end=end;
	}

	public long getNumericValue() throws LRException
	{
	   return longValue();
	}

	public String getStringValue() throws LRException
	{
		String s=string.getStringValue();
		int first=start.getIntegerValue();
		int last=end.getIntegerValue();
		if (first<0) first=s.length()-1;
		if (first>=s.length()) first=s.length()-1;
		if (last>=s.length()) last=s.length()-1;
		if (first<0) return "";
		switch (last)
		{
		case 0:
			return s.substring(first,first+1);
		case -1:
			return s.substring(first);
		default:
			if (last>=s.length()) last=s.length()-1;
			return s.substring(first,last+1);
		}
	}
}
