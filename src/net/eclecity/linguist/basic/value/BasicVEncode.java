//	BasicVEncode.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.value;

import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.util.LUEncoder;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Encode a string using URLEncoder/URLDecoder
	<pre>
	[1.001 GT]  30/09/00  New class.
	</pre>
*/
public class BasicVEncode extends LVValue
{
	private LVValue value;
	private String method;
	private boolean encode;

	public BasicVEncode(LVValue value,String method,boolean encode)
	{
		this.value=value;
		this.method=method;
		this.encode=encode;
	}

	public long getNumericValue() throws LRException
	{
		return value.getNumericValue();
	}

	public String getStringValue() throws LRException
	{
		String s=value.getStringValue();
//		try
//		{
			if (encode) return LUEncoder.encode(s,method);
			return LUEncoder.decode(s,method);
//		}
//		catch (Exception e)
//		{
//			throw new LRException(e);
//		}
	}
}
