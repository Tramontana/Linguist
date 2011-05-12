//	GraphicsVHeight.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.value;

import net.eclecity.linguist.graphics.handler.GraphicsHComponent;
import net.eclecity.linguist.graphics.handler.GraphicsHWindow;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Return the height of a component.
*/
public class GraphicsVHeight extends LVValue
{
	private GraphicsHComponent component;
	private GraphicsHWindow window;
	private boolean content;

	public GraphicsVHeight(GraphicsHComponent component)
	{
		this.component=component;
	}

	public GraphicsVHeight(GraphicsHWindow window,boolean content)
	{
		this.window=window;
		this.content=content;
	}

	public long getNumericValue() throws LRException
	{
		if (component!=null) return component.getHeight();
		if (window!=null)
		{
			if (content) return window.getContentHeight();
			return window.getHeight();
		}
		return 0;
	}

	public String getStringValue() throws LRException
	{
		return String.valueOf(getNumericValue());
	}
}
