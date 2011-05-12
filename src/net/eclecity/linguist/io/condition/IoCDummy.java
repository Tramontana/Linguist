//	IoCDummy.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.io.condition;

import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Test if one string contains another.
*/
public class IoCDummy extends LCCondition
{
	private LVValue value;			// the value to test
	private LVValue value2;			// the value to compare with

	public IoCDummy(LVValue v,LVValue v2)
	{
		value=v;
		value2=v2;
	}

	public boolean test() throws LRException
	{
		if (value.isNumeric()) return false;
		return (value.getStringValue().indexOf(value2.getStringValue())>=0);
	}
}
