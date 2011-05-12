//	GraphicsCVisible.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.condition;

import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.graphics.handler.GraphicsHComponent;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Test if a component is visible.
*/
public class GraphicsCVisible extends LCCondition
{
	private GraphicsHComponent component;
	private boolean sense;

	public GraphicsCVisible(GraphicsHComponent component,boolean sense)
	{
		this.component=component;
		this.sense=sense;
	}

	public boolean test() throws LRException
	{
		boolean state=component.isVisible();
		return sense?state:!state;
	}
}
