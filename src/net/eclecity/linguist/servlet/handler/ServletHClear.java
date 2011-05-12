// ServletHClear.java

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

/******************************************************************************
	Clear something.
*/
public class ServletHClear extends LHHandler
{
	private ServletHElement element=null;
	private ServletHTemplate template=null;

	public ServletHClear(int line)
	{
		this.line=line;
	}

	public ServletHClear(int line,ServletHElement element)
	{
		this.line=line;
		this.element=element;
	}

	public ServletHClear(int line,ServletHTemplate template)
	{
		this.line=line;
		this.template=template;
	}

	public int execute() throws LRException
	{
		if (element!=null) element.clear();
		else if (template!=null) template.clear();
		else ((ServletRBackground)getBackground("servlet")).clear();
		return pc+1;
	}
}

