//	BasicVSystemName.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.value;

import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Get the name of the operating system.
*/
public class BasicVSystemName extends LVValue
{
	public BasicVSystemName() {	}

	public long getNumericValue()
	{
		return 0;
	}

	public String getStringValue()
	{
		return System.getProperty("os.name");
	}
}
