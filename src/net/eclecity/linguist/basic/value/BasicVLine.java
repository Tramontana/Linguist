//	BasicVLine.java

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
	Extract a line from a string.
*/
public class BasicVLine extends LVValue
{
	LVValue value;				// the line to extract
	LVValue string;			// the string to take it from

	public BasicVLine(LVValue string,LVValue value)
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
		int target=value.getIntegerValue();
		int line=0;
		int length=s.length();
		for (int n=0; n<length; n++)
		{
			// If we've found the wanted line, return it
			if (line==target)
			{
				for (int m=n; m<length; m++)
				{
					switch (s.charAt(m))
					{
						case '\n':
						case '\r':
							return s.substring(n,m);
					}
				}
				return s.substring(n);		// no terminating CR or LF
			}
			switch (s.charAt(n))
			{
				case '\n':
					line++;
					break;
				case '\r':
					line++;
					if (n<length-1 && s.charAt(n+1)=='\n') n++;
					break;
				default:
					break;
			}
		}
		return "";
	}
}
