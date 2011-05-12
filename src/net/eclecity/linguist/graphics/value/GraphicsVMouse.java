//	GraphicsVMouse.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.value;

import java.awt.Container;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.MouseEvent;

import net.eclecity.linguist.handler.LHEvent;
import net.eclecity.linguist.runtime.LRProgram;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Return the mouse left or top.
*/
public class GraphicsVMouse extends LVValue
{
	private LRProgram program;
	private boolean top;

	public GraphicsVMouse(LRProgram program,boolean top)
	{
		this.program=program;
		this.top=top;
	}

	public long getNumericValue()
	{
		LHEvent event=(LHEvent)program.getQueueData(LHEvent.class);
		MouseEvent evt=(MouseEvent)event.getEvent();
		Container container=(Container)evt.getComponent();
		Point location=new Point(evt.getX()-container.getInsets().left,
			evt.getY()-container.getInsets().top);
		// Add the location of the component that fired the event
		if (!(container instanceof Window))
			location.translate(container.getLocation().x,container.getLocation().y);
		return (top?location.y:location.x);
	}

//	public long getNumericValue() throws LRError
//	{
//		LHEvent event=(LHEvent)((LRProgram)program).getQueueData(LHEvent.class);
//		MouseEvent evt=(MouseEvent)event.getEvent();
//		Component component=evt.getComponent();
//		int x=evt.getX();
//		int y=evt.getY();
//		while (component!=null)
//		{
//			x+=component.getX();
//			y+=component.getY();
//			component=component.getParent();
//		}
//		return (long)(top?y:x);
//	}

	public String getStringValue()
	{
		return String.valueOf(getNumericValue());
	}
}
