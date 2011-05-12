//	BasicCNotEqual.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.condition;

import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Test for not equal to.
*/
public class BasicCNotEqual extends LCCondition
{
	private LVValue value;			// the value to test
	private LVValue value2;			// the value to compare with

	public BasicCNotEqual(LVValue v,LVValue v2)
	{
		value=v;
		value2=v2;
	}

	public boolean test() throws LRException
	{
		try
		{
			if (value.isNumeric()) return value.getNumericValue()!=value2.getNumericValue();
			return (value.getStringValue().compareTo(value2.getStringValue())!=0);
		}
		catch (NullPointerException e) { return false; }
	}
}
