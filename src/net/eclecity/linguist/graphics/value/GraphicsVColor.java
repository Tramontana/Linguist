//	GraphicsVColor.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.value;

import java.awt.Color;

import net.eclecity.linguist.graphics.handler.GraphicsHColor;
import net.eclecity.linguist.handler.LHData;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVConstant;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Hold a Color.
*/
public class GraphicsVColor extends LHData
{
	private GraphicsHColor color=null;
	private LVValue red;
	private LVValue green;
	private LVValue blue;

	public GraphicsVColor(GraphicsHColor color)
	{
		this.color=color;
	}

	public GraphicsVColor(LVValue red,LVValue green,LVValue blue)
	{
		this.red=red;
		this.green=green;
		this.blue=blue;
	}

	public GraphicsVColor(int red,int green,int blue)
	{
		this.red=new LVConstant(red);
		this.green=new LVConstant(green);
		this.blue=new LVConstant(blue);
	}

	public GraphicsVColor(Color color)
	{
		red=new LVConstant(color.getRed());
		green=new LVConstant(color.getGreen());
		blue=new LVConstant(color.getBlue());
	}

	public Color getColor() throws LRException
	{
		if (color!=null) return color.getColor();
		return new Color(red.getIntegerValue(),green.getIntegerValue(),blue.getIntegerValue());
	}
}
