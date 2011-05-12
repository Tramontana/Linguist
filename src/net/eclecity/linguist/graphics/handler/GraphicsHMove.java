// GraphicsHMove.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import net.eclecity.linguist.graphics.value.GraphicsVLocation;
import net.eclecity.linguist.graphics.value.GraphicsVSize;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Move a component.
*/
public class GraphicsHMove extends LHHandler
{
	private GraphicsHComponent component;
	private GraphicsVLocation location=null;
	private GraphicsVSize size=null;
	private LVValue value=null;
	private int direction;

	public GraphicsHMove(int line,GraphicsHComponent component,GraphicsVLocation location)
	{
		this.line=line;
		this.component=component;
		this.location=location;
	}

	public GraphicsHMove(int line,GraphicsHComponent component,GraphicsVSize size)
	{
		this.line=line;
		this.component=component;
		this.size=size;
	}

	public GraphicsHMove(int line,GraphicsHComponent component,LVValue value,int direction)
	{
		this.line=line;
		this.component=component;
		this.value=value;
		this.direction=direction;
	}

	public int execute() throws LRException
	{
		if (location!=null) component.moveTo(location);
		else if (size!=null) component.moveBy(size);
		else if (value!=null) component.moveBy(value,direction);
		return pc+1;
	}
}

