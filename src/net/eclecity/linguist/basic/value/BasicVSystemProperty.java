//	BasicVSystemProperty.java

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
	Get the command-line parameter.
*/
public class BasicVSystemProperty extends LVValue
{
	private LVValue key;

	public BasicVSystemProperty(LVValue key)
	{
		this.key=key;
	}

	public long getNumericValue() throws LRException
	{
		return longValue();
	}

	public String getStringValue() throws LRException
	{
		return System.getProperty(key.getStringValue());
	}
}
