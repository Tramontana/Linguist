//	GraphicsVSize.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.value;

import java.awt.Dimension;

import net.eclecity.linguist.graphics.handler.GraphicsHComponent;
import net.eclecity.linguist.graphics.handler.GraphicsHImage;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVConstant;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Hold a size.
*/
public class GraphicsVSize extends LVValue
{
	GraphicsHComponent component=null;
	GraphicsHImage image=null;
	LVValue width;
	LVValue height;
	
	public GraphicsVSize() {}

	public GraphicsVSize(GraphicsHComponent component)
	{
		this.component=component;
	}

	public GraphicsVSize(GraphicsHImage image)
	{
		this.image=image;
	}

	public GraphicsVSize(LVValue width,LVValue height)
	{
		this.width=width;
		this.height=height;
	}

	public GraphicsVSize(int width,int height)
	{
		this.width=new LVConstant(width);
		this.height=new LVConstant(height);
	}

	public int getWidth() throws LRException
	{
		return getSize().width;
	}
	
	public int getHeight() throws LRException
	{
		return getSize().height;
	}
	
	public Dimension getSize() throws LRException
	{
		if (component!=null)
			return new Dimension(component.getWidth(),component.getHeight());
		if (image!=null) return image.getSize();
		return new Dimension(width.getIntegerValue(),height.getIntegerValue());
	}
	
	public long getNumericValue() { return 0; }
	public String getStringValue() { return ""; }
}
