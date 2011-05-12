// GraphicsHFont.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import java.awt.Color;
import java.awt.Font;

import net.eclecity.linguist.graphics.value.GraphicsVColor;
import net.eclecity.linguist.graphics.value.GraphicsVFont;
import net.eclecity.linguist.handler.LHData;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.runtime.LRException;


/******************************************************************************
	Font handler.
*/
public class GraphicsHFont extends LHVariableHandler
{
	public GraphicsHFont() {}

	public Object newElement(Object extra) { return new GraphicsHFontData(); }
	
	public void create(GraphicsVFont font) throws LRException
	{
		GraphicsHFontData myData=(GraphicsHFontData)getData();
		myData.font=font.getFont();
		myData.color=font.getColor();
	}
	
	public Font getFont() throws LRException
	{
		return ((GraphicsHFontData)getData()).font;
	}

	public void setFont(Font font) throws LRException
	{
		((GraphicsHFontData)getData()).font=font;
	}

	public Color getColor() throws LRException
	{
		return ((GraphicsHFontData)getData()).color;
	}

	public void setColor(GraphicsVColor color) throws LRException
	{
		((GraphicsHFontData)getData()).color=color.getColor();
	}

	class GraphicsHFontData extends LHData
	{
		Font font;
		Color color;
	}
}
