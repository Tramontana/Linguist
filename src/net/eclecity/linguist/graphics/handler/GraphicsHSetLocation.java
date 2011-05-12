// GraphicsHSetLocation.java

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
	Set the location of a component.
*/
public class GraphicsHSetLocation extends LHHandler
{
	private GraphicsHSwingComponent component=null;
	private GraphicsHWindow window=null;
	private GraphicsVLocation location;

	public GraphicsHSetLocation(int line,GraphicsHSwingComponent component,GraphicsVLocation location)
	{
		this.line=line;
		this.component=component;
		this.location=location;
	}

	public GraphicsHSetLocation(int line,GraphicsHWindow window,GraphicsVLocation location)
	{
		this.line=line;
		this.window=window;
		this.location=location;
	}

	public int execute() throws LRException
	{
		if (component!=null) component.setLocation(location);
		else if (window!=null) window.setLocation(location);
		return pc+1;
	}
}

