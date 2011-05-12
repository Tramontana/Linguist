// GraphicsHSetCursor.java

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
	Set an cursor of a component,
	to be shown when the mouse enters this component.
*/
public class GraphicsHSetCursor extends LHHandler
{
	private GraphicsHComponent component=null;
	private GraphicsHStyle style=null;
	private GraphicsHCursor cursor;

	public GraphicsHSetCursor(int line,GraphicsHComponent component,GraphicsHCursor cursor)
	{
		this.line=line;
		this.component=component;
		this.cursor=cursor;
	}

	public GraphicsHSetCursor(int line,GraphicsHStyle style,GraphicsHCursor cursor)
	{
		this.line=line;
		this.style=style;
		this.cursor=cursor;
	}

	public int execute() throws LRException
	{
		if (component!=null) component.setCursor(cursor);
		else if (style!=null) style.setCursor(cursor);
		return pc+1;
	}
}

