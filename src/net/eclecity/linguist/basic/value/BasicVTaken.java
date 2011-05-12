//	BasicVTaken.java

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
	Return one value taken from another.
	<pre>
	[1.001 GT]  29/11/00  New class.
	</pre>
*/
public class BasicVTaken extends LVValue
{
	private LVValue value1;
	private LVValue value2;

	public BasicVTaken(LVValue value1,LVValue value2)
	{
		this.value1=value1;
		this.value2=value2;
	}

	public long getNumericValue() throws LRException
	{
		return value2.getNumericValue()-value1.getNumericValue();
	}

	public String getStringValue() throws LRException
	{
		return ""+getNumericValue();
	}
}
