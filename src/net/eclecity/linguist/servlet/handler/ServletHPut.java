// ServletHPut.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.servlet.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Put something into an element.
*/
public class ServletHPut extends LHHandler
{
	private Object item=null;
	private ServletHElement container=null;

	public ServletHPut(int line,Object item,ServletHElement container)
	{
		this.line=line;
		this.item=item;
		this.container=container;
	}

	public int execute() throws LRException
	{
		if (container!=null)
		{
			if (item!=null) container.put(item);
		}
		return pc+1;
	}
}

