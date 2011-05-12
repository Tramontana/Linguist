//	BasicVHex.java

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
	Two-way Hex conversion.
*/
public class BasicVHex extends LVValue
{
	private LVValue value;		// the value to convert

	public BasicVHex(LVValue value)
	{
		this.value=value;
	}

	public long getNumericValue() throws LRException
	{
		try { return Long.parseLong(value.getStringValue(),16); }
		catch (NumberFormatException e) { return 0; }
	}

	public String getStringValue() throws LRException
	{
		long n=value.getNumericValue();
		return toHex16((int)(n>>16))+toHex16((int)(n&0x0000ffff));
	}

	private String toHex16(int n)
	{
		return toHex8(n>>8)+toHex8(n&0x00ff);
	}

	private String toHex8(int n)
	{
		return toHex4((n>>4)&0x0f)+toHex4(n&0x0f);
	}

	private String toHex4(int n)
	{
		if (n>9) n+=0x07;
		n+='0';
		return ""+(char)n;
	}
}
