// GraphicsHSetSize.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import net.eclecity.linguist.graphics.value.GraphicsVSize;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

public class GraphicsHSetSize extends LHHandler
{
	private GraphicsHSwingComponent component=null;
	private GraphicsHPanel panel=null;
	private GraphicsHWindow window=null;
	private GraphicsVSize size=null;
	private LVValue width=null;
	private LVValue height=null;

	/***************************************************************************
		Set the size of a component.
	*/
	public GraphicsHSetSize(int line,GraphicsHSwingComponent component,GraphicsVSize size)
	{
		this.line=line;
		this.component=component;
		this.size=size;
	}

	/***************************************************************************
		Set the width or height of a component.
	*/
	public GraphicsHSetSize(int line,GraphicsHSwingComponent component,LVValue value,boolean isWidth)
	{
		this.line=line;
		this.component=component;
		if (isWidth)width=value;
		else height=value;
	}

	/***************************************************************************
		Set the size of a panel.
	*/
	public GraphicsHSetSize(int line,GraphicsHPanel panel,GraphicsVSize size)
	{
		this.line=line;
		this.panel=panel;
		this.size=size;
	}

	/***************************************************************************
		Set the size of a window.
	*/
	public GraphicsHSetSize(int line,GraphicsHWindow window,GraphicsVSize size)
	{
		this.line=line;
		this.window=window;
		this.size=size;
	}

	/***************************************************************************
		Set the width or height of a window.
	*/
	public GraphicsHSetSize(int line,GraphicsHWindow window,LVValue value,boolean isWidth)
	{
		this.line=line;
		this.window=window;
		if (isWidth)width=value;
		else height=value;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		if (component!=null)
		{
			if (size!=null) component.setSize(size);
			else if (width!=null) component.setWidth(width);
			else if (height!=null) component.setHeight(height);
		}
		else if (panel!=null)
		{
			if (size!=null) panel.setSize(size);
		}
		else if (window!=null)
		{
			if (size!=null) window.setSize(size);
			else if (width!=null) window.setWidth(width);
			else if (height!=null) window.setHeight(height);
		}
		return pc+1;
	}
}

