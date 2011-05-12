//	GraphicsCStatus.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.condition;

import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.graphics.handler.GraphicsHButton;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Test the status of a component.
*/
public class GraphicsCStatus extends LCCondition
{
	private GraphicsHButton button=null;
	private boolean sense;

	public GraphicsCStatus(GraphicsHButton button,boolean sense)
	{
		this.button=button;
		this.sense=sense;
	}

	public boolean test() throws LRException
	{
		if (button!=null)
		{
			int status=button.getStatus();
			if (sense) return (status==1);
			return (status==0);
		}
		return false;
	}
}
