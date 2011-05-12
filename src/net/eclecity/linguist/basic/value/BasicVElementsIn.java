//	BasicVElementsIn.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.value;

import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Extract the number of elements in an array.
*/
public class BasicVElementsIn extends LVValue
{
	LHVariableHandler handler;

	public BasicVElementsIn(LHVariableHandler handler)
	{
		this.handler=handler;
	}

	public long getNumericValue()
	{
		return handler.getElements();
	}

	public String getStringValue()
	{
		return String.valueOf(getNumericValue());
	}
}
