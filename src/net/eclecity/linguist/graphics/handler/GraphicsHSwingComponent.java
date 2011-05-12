// GraphicsHSwingComponent.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import net.eclecity.linguist.graphics.value.GraphicsVFont;
import net.eclecity.linguist.graphics.value.GraphicsVLocation;
import net.eclecity.linguist.graphics.value.GraphicsVSize;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	The base class for all JComponents.
*/
public abstract class GraphicsHSwingComponent extends GraphicsHComponent
{
	public GraphicsHSwingComponent() {}

	/***************************************************************************
		Set the location of the component.
	*/
	public void setLocation(LVValue left,LVValue top) throws LRException
	{
		JComponent c=(JComponent)getComponentData().component;
		c.setLocation(left.getIntegerValue(),top.getIntegerValue());
	}
	public void setLocation(GraphicsVLocation location) throws LRException
	{
		JComponent c=(JComponent)getComponentData().component;
		c.setLocation(location.getLeft(),location.getTop());
	}

	/***************************************************************************
		Set the size of the component.
	*/
	public void setSize(LVValue width,LVValue height) throws LRException
	{
		setSize(new GraphicsVSize(width,height));
	}
	public void setSize(int width,int height) throws LRException
	{
		setSize(new GraphicsVSize(width,height));
	}

	/***************************************************************************
		Set the border of the component.
	*/
	public void setBorder(GraphicsHBorder border) throws LRException
	{
		JComponent c=(JComponent)getComponentData().component;
		Border b;
		if (border==null) b=new EmptyBorder(0,0,0,0);
		else b=border.getBorder();
		c.setBorder(b);
	}

	/***************************************************************************
		Set and get the font of the component.
	*/
	public void setFont(GraphicsVFont font) throws LRException
	{
		try
		{
			JComponent c=(JComponent)getComponentData().component;
			c.setFont(font.getFont());
			c.setForeground(font.getColor());
		}
		catch (NullPointerException e) {}
	}
	public Font getFont() throws LRException
	{
		JComponent component=(JComponent)getComponentData().component;
		return component.getFont();
	}
	public Color getColor() throws LRException
	{
		JComponent component=(JComponent)getComponentData().component;
		return component.getForeground();
	}

	/***************************************************************************
		Set the tooltip of the component.
	*/
	public void setToolTip(LVValue text) throws LRException
	{
		JComponent c=(JComponent)getComponentData().component;
		c.setToolTipText(text.getStringValue());
	}
}
