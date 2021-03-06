//	BasicCNegative.java

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
	Test for negative.
*/
public class BasicCNegative extends LCCondition
{
	private LVValue value;			// the value to test

	public BasicCNegative(LVValue v)
	{
		value=v;
	}

	public boolean test() throws LRException
	{
		if (value.isNumeric()) return value.getNumericValue()<0;
		return false;
	}
}
