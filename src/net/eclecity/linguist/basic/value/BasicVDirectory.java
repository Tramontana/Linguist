//	BasicVDirectory.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.value;

import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Get the name of the user directory.
*/
public class BasicVDirectory extends LVValue
{
	public BasicVDirectory() {}

	public long getNumericValue()
	{
		return 0;
	}

	public String getStringValue()
	{
		String s=System.getProperty("user.dir");
		if (!s.endsWith(System.getProperty("file.separator"))) s+="/";
		return s;
	}
}
