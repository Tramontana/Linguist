//	GraphicsCSelected.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.condition;

import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.graphics.handler.GraphicsHButton;

/******************************************************************************
	Test a button is selected.
*/
public class GraphicsCSelected extends LCCondition
{
	private GraphicsHButton button;
	private boolean sense;

	public GraphicsCSelected(GraphicsHButton button,boolean sense)
	{
		this.button=button;
		this.sense=sense;
	}

	public boolean test()
	{
		boolean state=button.isSelected();
		return sense?state:!state;
	}
}
