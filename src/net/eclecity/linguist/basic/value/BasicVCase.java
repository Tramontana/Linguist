//	BasicVCase.java

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
	Convert a value to upper or lower case.
*/
public class BasicVCase extends LVValue
{
	private LVValue value;
	private boolean toupper;

	public BasicVCase(LVValue value,boolean toupper)
	{
		this.value=value;
		this.toupper=toupper;
	}

	public long getNumericValue() throws LRException
	{
		return value.getNumericValue();
	}

	public String getStringValue() throws LRException
	{
		if (toupper) return value.getStringValue().toUpperCase();
		return value.getStringValue().toLowerCase();
	}
}
