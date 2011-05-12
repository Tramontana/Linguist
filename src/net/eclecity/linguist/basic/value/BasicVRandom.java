//	BasicVRandom.java

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
	Generate a random number.
*/
public class BasicVRandom extends LVValue
{
	LVValue value;			// range is 0 to value-1

	public BasicVRandom(LVValue value)
	{
		this.value=value;
	}
	
	public long getNumericValue() throws LRException
	{
		return (long)(Math.random()*value.getNumericValue());
	}
	
	public String getStringValue() throws LRException
	{
		return String.valueOf(getNumericValue());
	}
}
