// ServletHGet.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.servlet.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.servlet.runtime.ServletRBackground;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Get something.
*/
public class ServletHGet extends LHHandler
{
	private ServletHCookie cookie=null;
	private LVValue value;

	/***************************************************************************
		Get the session object.
	*/
	public ServletHGet(int line)
	{
		this.line=line;
	}

	/***************************************************************************
		Get a cookie.
	*/
	public ServletHGet(int line,ServletHCookie cookie,LVValue value)
	{
		this.line=line;
		this.cookie=cookie;
		this.value=value;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		if (cookie!=null) cookie.get(value);
		else ((ServletRBackground)getBackground("servlet")).getSession();
		return pc+1;
	}
}

