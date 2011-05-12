//	GraphicsVLeft.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.value;

import net.eclecity.linguist.graphics.handler.GraphicsHComponent;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Return the left position of a component.
*/
public class GraphicsVLeft extends LVValue
{
	private GraphicsHComponent component;
	private boolean inset;

	public GraphicsVLeft(GraphicsHComponent component,boolean inset)
	{
		this.component=component;
		this.inset=inset;
	}

	public long getNumericValue() throws LRException
	{
		return component.getLeft(inset);
	}

	public String getStringValue() throws LRException
	{
		return String.valueOf(getNumericValue());
	}
}
