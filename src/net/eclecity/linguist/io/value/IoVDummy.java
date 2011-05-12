//	IoVDummy.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.io.value;

import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Dummy class.
*/
public class IoVDummy extends LVValue
{
	public IoVDummy()
	{
	}

	public long getNumericValue()
	{
		return 0;
	}

	public String getStringValue()
	{
		return String.valueOf(getNumericValue());
	}
}
