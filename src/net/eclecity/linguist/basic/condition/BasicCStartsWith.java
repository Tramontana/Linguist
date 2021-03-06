//	BasicCStartsWith.java

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
	Test for one string starting with another.
*/
public class BasicCStartsWith extends LCCondition
{
	private LVValue value;			// the value to test
	private LVValue value2;			// the value to compare with

	public BasicCStartsWith(LVValue v,LVValue v2)
	{
		value=v;
		value2=v2;
	}

	public boolean test() throws LRException
	{
		if (value.isNumeric()) return false;
		return (value.getStringValue().startsWith(value2.getStringValue()));
	}
}
