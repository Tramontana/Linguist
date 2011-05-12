//	ServletCExists.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.servlet.condition;

import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.servlet.handler.ServletHCookie;

/******************************************************************************
	Test if a cookie exists.
*/
public class ServletCExists extends LCCondition
{
	private ServletHCookie cookie;
	private boolean flag;

	public ServletCExists(ServletHCookie cookie,boolean flag)
	{
		this.cookie=cookie;
		this.flag=flag;
	}

	public boolean test() throws LRException
	{
		Object ob=cookie.getCookie();
		return flag?(ob!=null):(ob==null);
	}
}
