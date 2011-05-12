//	BasicVStringConstant.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.value;

import net.eclecity.linguist.handler.LHConstant;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	The value of a string constant.
*/
public class BasicVStringConstant extends LVValue
{
	private LHConstant constant;

	public BasicVStringConstant(LHConstant constant)
	{
		this.constant=constant;
		setNumeric(false);
	}

	public long getNumericValue() throws LRException
	{
		return longValue();
	}

	public String getStringValue()
	{
		return (String)constant.getValue();
	}
}
