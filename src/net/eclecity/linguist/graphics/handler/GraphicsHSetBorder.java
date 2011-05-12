// GraphicsHSetBorder.java

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
	Set the border of a component.
*/
public class GraphicsHSetBorder extends LHHandler
{
	private GraphicsHSwingComponent component;
	private GraphicsHBorder border;

	public GraphicsHSetBorder(int line,GraphicsHSwingComponent component,GraphicsHBorder border)
	{
		this.line=line;
		this.component=component;
		this.border=border;
	}

	public int execute() throws LRException
	{
		component.setBorder(border);
		return pc+1;
	}
}

