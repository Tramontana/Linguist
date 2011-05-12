//	BasicVTimestamp.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.value;

import net.eclecity.linguist.handler.LHModule;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Get the timestamp of a module.
*/
public class BasicVTimestamp extends LVValue
{
	private LHModule module;

	public BasicVTimestamp(LHModule module)
	{
		this.module=module;
	}

	public long getNumericValue() throws LRException
	{
		return module.getModule().getTimestamp();
	}

	public String getStringValue() throws LRException
	{
		return String.valueOf(getNumericValue());
	}
}
