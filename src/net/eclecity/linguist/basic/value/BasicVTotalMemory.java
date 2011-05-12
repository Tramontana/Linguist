//	BasicVTotalMemory.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.value;

import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Get the available total memory.
*/
public class BasicVTotalMemory extends LVValue
{
	public BasicVTotalMemory()
	{
	}
	
	public long getNumericValue()
	{
		Runtime rt=Runtime.getRuntime();
//		rt.gc();
		return rt.totalMemory();
	}
	
	public String getStringValue()
	{
		return String.valueOf(getNumericValue());
	}
}
