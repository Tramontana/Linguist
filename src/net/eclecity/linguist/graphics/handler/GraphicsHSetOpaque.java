// GraphicsHSetOpaque.java

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
	Set the opacity of a component.
*/
public class GraphicsHSetOpaque extends LHHandler
{
	private GraphicsHComponent component;
	private boolean opaque;

	public GraphicsHSetOpaque(int line,GraphicsHComponent component,boolean opaque)
	{
		this.line=line;
		this.component=component;
		this.opaque=opaque;
	}

	public int execute() throws LRException
	{
		component.setOpaque(opaque);
		return pc+1;
	}
}

