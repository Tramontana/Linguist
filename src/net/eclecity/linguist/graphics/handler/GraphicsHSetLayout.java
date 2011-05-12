// GraphicsHSetLayout.java

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
	Set the layout manager of a component.
*/
public class GraphicsHSetLayout extends LHHandler
{
	private GraphicsHComponent component;
	private int layout;

	public GraphicsHSetLayout(int line,GraphicsHComponent component,int layout)
	{
		this.line=line;
		this.component=component;
		this.layout=layout;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		component.setLayout(layout);
		return pc+1;
	}
}

