//	BasicVWord.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.value;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Extract a word from a string.
*/
public class BasicVWord extends LVValue
{
	LVValue value;				// the index of the word to extract
	LVValue string;			// the string to take it from

	public BasicVWord(LVValue string,LVValue value)
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
		if (s!=null)
		{
			StringTokenizer parser=new StringTokenizer(s);
			int n=value.getIntegerValue();
			if (n<0) n=parser.countTokens()-1;		// "last word"
			try
			{
				while (parser.hasMoreTokens())
				{
					s=parser.nextToken();
					n--;
					if (n<0) return s;
				}
			}
			catch (NoSuchElementException e) {}
		}
		return "";
	}
}
