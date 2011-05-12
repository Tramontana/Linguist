//	BasicVWordsIn.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.value;

import java.util.StringTokenizer;

import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Extract the number of words in a string.
*/
public class BasicVWordsIn extends LVValue
{
	LVValue string;			// the string

	public BasicVWordsIn(LVValue string)
	{
		this.string=string;
	}

	public long getNumericValue() throws LRException
	{
		StringTokenizer parser=new StringTokenizer(string.getStringValue());
		return parser.countTokens();
	}

	public String getStringValue() throws LRException
	{
		return String.valueOf(getNumericValue());
	}
}
