// GraphicsHFindEvent.java

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
	Find which element of a component generated the last event.
*/
public class GraphicsHFindEvent extends LHHandler
{
	private GraphicsHComponent component;

	public GraphicsHFindEvent(int line,GraphicsHComponent component)
	{
		this.line=line;
		this.component=component;
	}

	public int execute() throws LRException
	{
		component.findEvent();
		return pc+1;
	}
}

