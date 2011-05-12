//	GraphicsVFont.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.value;

import java.awt.Color;
import java.awt.Font;

import net.eclecity.linguist.graphics.handler.GraphicsHFont;
import net.eclecity.linguist.handler.LHData;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVConstant;
import net.eclecity.linguist.value.LVStringConstant;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Hold a Font.
*/
public class GraphicsVFont extends LHData
{
	private GraphicsHFont font=null;
	private LVValue name=new LVStringConstant("Dialog");
	private LVValue style=new LVConstant(Font.PLAIN);
	private LVValue size=new LVConstant(14);
	private GraphicsVColor color=new GraphicsVColor(Color.black);
	
	public GraphicsVFont() {}

	public GraphicsVFont(GraphicsHFont font)
	{
		this.font=font;
	}

	public GraphicsVFont(LVValue name,LVValue style,LVValue size,GraphicsVColor color)
	{
		this.name=name;
		this.style=style;
		this.size=size;
		this.color=color;
	}
	
	public GraphicsVFont(Font f,Color c)
	{
		name=new LVStringConstant(f.getName());
		style=new LVConstant(f.getStyle());
		size=new LVConstant(f.getSize());
		color=new GraphicsVColor(c);
	}

	public Font getFont() throws LRException
	{
		if (font!=null) return font.getFont();
		return new Font(name.getStringValue(),style.getIntegerValue(),size.getIntegerValue());
	}
	
	public Color getColor() throws LRException
	{
		if (font!=null) return font.getColor();
		return color.getColor();
	}
}
