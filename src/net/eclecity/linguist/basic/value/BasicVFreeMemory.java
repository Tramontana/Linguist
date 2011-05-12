//	BasicVFreeMemory.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.value;

import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Get the available free memory.
*/
public class BasicVFreeMemory extends LVValue
{
	public BasicVFreeMemory()
	{
	}
	
	public long getNumericValue()
	{
		Runtime rt=Runtime.getRuntime();
//		rt.gc();
		return rt.freeMemory();
	}
	
	public String getStringValue()
	{
		return String.valueOf(getNumericValue());
	}
}
