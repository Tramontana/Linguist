// GraphicsHColor.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import java.awt.Color;

import net.eclecity.linguist.graphics.value.GraphicsVColor;
import net.eclecity.linguist.handler.LHData;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.runtime.LRException;


/******************************************************************************
	Color handler.
*/
public class GraphicsHColor extends LHVariableHandler
{
	public GraphicsHColor() {}

	public Object newElement(Object extra) { return new GraphicsHColorData(); }
	
	public void create(GraphicsVColor color) throws LRException
	{
		((GraphicsHColorData)getData()).color=color.getColor();
	}
	
	public Color getColor() throws LRException
	{
		return ((GraphicsHColorData)getData()).color;
	}

	public void setColor(Color color) throws LRException
	{
		((GraphicsHColorData)getData()).color=color;
	}

	class GraphicsHColorData extends LHData
	{
		Color color;
	}
}
