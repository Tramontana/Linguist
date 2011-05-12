//	GraphicsVMouseLocation.java

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


/******************************************************************************
	The location of the mouse.
	If absoute is true, return the screen position,
	else return the position in the window.
*/
public class GraphicsVMouseLocation extends GraphicsVLocation
{
	private LRProgram program;
	private boolean absolute;

	public GraphicsVMouseLocation(LRProgram program,boolean absolute)
	{
		this.program=program;
		this.absolute=absolute;
	}

	public Point getLocation()
	{
		LHEvent event=(LHEvent)program.getQueueData();
		MouseEvent evt=(MouseEvent)event.getEvent();
		Container container=(Container)evt.getComponent();
		Point location=new Point(evt.getX()-container.getInsets().left,
			evt.getY()-container.getInsets().top);
		// Add the location of the component that fired the event
		if (!(container instanceof Window))
			location.translate(container.getLocation().x,container.getLocation().y);
		if (absolute)
		{
			// Add the location of the window
			while (!(container instanceof Window)) container=container.getParent();
			location.translate(container.getLocation().x,container.getLocation().y);
		}
		return location;
	}

	public int getLeft()
	{
		return getLocation().x;
	}

	public int getTop()
	{
		return getLocation().y;
	}
}
