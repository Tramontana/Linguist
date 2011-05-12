//	ServletVCookie.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.servlet.value;

import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.servlet.handler.ServletHCookie;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Return the value from a cookie.
*/
public class ServletVCookie extends LVValue
{
	private ServletHCookie cookie;

	public ServletVCookie(ServletHCookie cookie)
	{
		this.cookie=cookie;
	}

	public long getNumericValue() throws LRException
	{
	   return longValue();
	}

	public String getStringValue() throws LRException
	{
		return cookie.getStringValue();
	}
}
