//	ServletVEncoded.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.servlet.value;

import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.util.LUEncoder;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Return a URLEncoded value.
*/
public class ServletVEncoded extends LVValue
{
	private LVValue name;

	public ServletVEncoded(LVValue name)
	{
		this.name=name;
	}

	public long getNumericValue()
	{
	   return 0;
	}

	public String getStringValue() throws LRException
	{
		String s=name.getStringValue();
		return LUEncoder.encode(s);
	}
}
