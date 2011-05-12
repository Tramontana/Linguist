// ServletHDoService.java

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
import net.eclecity.linguist.servlet.runtime.ServletRServletParams;

/******************************************************************************
	Invoke a 'do get' or 'do post' callback under script control.
*/
public class ServletHDoService extends LHHandler
{
	private boolean isPost;

	public ServletHDoService(int line,boolean isPost)
	{
		this.line=line;
		this.isPost=isPost;
	}

	public int execute() throws LRException
	{
		program.handleExternalEvent(
			new ServletRServletParams(((ServletRBackground)getBackground("servlet")).getParameters(),isPost));
		return pc+1;
	}
}

