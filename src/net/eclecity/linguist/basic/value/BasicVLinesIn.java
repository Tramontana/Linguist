//	BasicVLinesIn.java

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
	Extract the number of lines in a string.
*/
public class BasicVLinesIn extends LVValue
{
	LVValue string;			// the string

	public BasicVLinesIn(LVValue string)
	{
		this.string=string;
	}

	public long getNumericValue() throws LRException
	{
		String s=string.getStringValue();
		int lines=0;
		int length=s.length();
		char lastc='\n';
		for (int n=0; n<length; n++)
		{
			switch (lastc=s.charAt(n))
			{
				case '\n':
					lines++;
					break;
				case '\r':
					lines++;
					if (s.length()<n+2) break;
					if (s.charAt(n+1)=='\n') n++;
					break;
				default:
					break;
			}
		}
		if (lastc!='\n' && lastc!='\r') lines++;
		return lines;
	}

	public String getStringValue() throws LRException
	{
		return String.valueOf(getNumericValue());
	}
}
