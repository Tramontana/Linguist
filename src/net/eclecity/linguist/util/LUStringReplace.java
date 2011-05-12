// LUStringReplace.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.util;

import net.eclecity.linguist.handler.LHStringHolder;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	A utility class that replaces all occurrences of one substring with another.
*/
public class LUStringReplace
{
	public LUStringReplace(LHStringHolder h,LVValue s1,LVValue s2) throws LRException
	{
		String holder=h.getStringValue();
		String string1=s1.getStringValue();
		String string2=s2.getStringValue();
		int length=string1.length();
		
		while (true)
		{
			int count=0;
			int n=holder.indexOf(string1);
			if (n>=0) holder=holder.substring(0,n)+string2+holder.substring(n+length);
			else break;
			if (++count>100000) break;		// prevents endless loops
		}
		h.setValue(holder);
	}
}

