// GraphicsHSetColor.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import net.eclecity.linguist.graphics.value.GraphicsVColor;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Set the foreground or background color of a component.
*/
public class GraphicsHSetColor extends LHHandler
{
	private GraphicsHComponent component;
	private boolean foreground;
	private GraphicsVColor color;

	public GraphicsHSetColor(int line,GraphicsHComponent component,boolean foreground,GraphicsVColor color)
	{
		this.line=line;
		this.component=component;
		this.foreground=foreground;
		this.color=color;
	}

	public int execute() throws LRException
	{
		if (foreground) component.setForeground(color);
		else component.setBackground(color);
		return pc+1;
	}
}

