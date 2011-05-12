//	GraphicsCRightButton.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.condition;

import java.awt.event.InputEvent;

import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.handler.LHEvent;
import net.eclecity.linguist.runtime.LRProgram;


/******************************************************************************
	Test if the right mouse button is pressed.
*/
public class GraphicsCRightButton extends LCCondition
{
	private LRProgram program;
	private boolean sense;

	public GraphicsCRightButton(LRProgram program,boolean sense)
	{
		this.program=program;
		this.sense=sense;
	}

	public boolean test()
	{
		LHEvent evt=(LHEvent)program.getQueueData(LHEvent.class);
		InputEvent event=(InputEvent)evt.getEvent();
		if (event==null) return false;
		boolean state=((event.getModifiers()&InputEvent.META_MASK)!=0);
		return sense?state:!state;
	}
}
