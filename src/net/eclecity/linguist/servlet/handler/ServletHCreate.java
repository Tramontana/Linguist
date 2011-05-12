// ServletHCreate.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.servlet.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Create something.
*/
public class ServletHCreate extends LHHandler
{
	private ServletHCookie cookie=null;
	private LVValue value;

	/***************************************************************************
		Create a cookie.
	*/
	public ServletHCreate(int line,ServletHCookie cookie,LVValue value)
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
		if (cookie!=null) cookie.create(value);
		return pc+1;
	}
}

