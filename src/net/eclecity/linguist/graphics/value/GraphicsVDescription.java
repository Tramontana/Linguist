//	GraphicsVDescription.java

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
	Return the description of a component.
*/
public class GraphicsVDescription extends LVValue
{
	private GraphicsHComponent component;

	public GraphicsVDescription(GraphicsHComponent component)
	{
		this.component=component;
	}

	public long getNumericValue() throws LRException
	{
		return longValue();
	}

	public String getStringValue() throws LRException
	{
		return component.getDescription();
	}
}
