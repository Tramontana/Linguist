//	GraphicsVCenterOf.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.value;

import java.awt.Point;

import net.eclecity.linguist.graphics.handler.GraphicsHComponent;
import net.eclecity.linguist.runtime.LRException;


/******************************************************************************
	The center of a component.
*/
public class GraphicsVCenterOf extends GraphicsVLocation
{
	private GraphicsHComponent component=null;

	public GraphicsVCenterOf(GraphicsHComponent component)
	{
		this.component=component;
	}

	public Point getLocation() throws LRException
	{
		return new Point(getLeft(),getTop());
	}

	public int getLeft() throws LRException
	{
		return component.getLeft()+component.getWidth()/2;
	}

	public int getTop() throws LRException
	{
		return component.getTop()+component.getHeight()/2;
	}
}
