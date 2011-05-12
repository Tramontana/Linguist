// GraphicsHCenter.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import net.eclecity.linguist.graphics.value.GraphicsVLocation;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Center a component at a location.
*/
public class GraphicsHCenter extends LHHandler
{
	private GraphicsHComponent component;
	private GraphicsVLocation location;

	public GraphicsHCenter(int line,GraphicsHComponent component,GraphicsVLocation location)
	{
		this.line=line;
		this.component=component;
		this.location=location;
	}

	public int execute() throws LRException
	{
		component.center(location);
		return pc+1;
	}
}

