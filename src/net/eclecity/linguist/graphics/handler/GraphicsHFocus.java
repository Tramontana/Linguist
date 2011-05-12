// GraphicsHFocus.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Request the focus for a component.
*/
public class GraphicsHFocus extends LHHandler
{
	private GraphicsHComponent component;

	public GraphicsHFocus(int line,GraphicsHComponent component)
	{
		this.line=line;
		this.component=component;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		if (component!=null) component.focus();
		return pc+1;
	}
}

