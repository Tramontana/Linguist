// GraphicsHPopupmenu.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import java.awt.Component;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import net.eclecity.linguist.graphics.value.GraphicsVLocation;
import net.eclecity.linguist.graphics.value.GraphicsVSize;
import net.eclecity.linguist.handler.LHEvent;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Popup menu handler.
*/
public class GraphicsHPopupmenu extends GraphicsHSwingComponent
{
	public GraphicsHPopupmenu() {}

	/***************************************************************************
		Create a new popup menu.
	*/
	public void create(GraphicsHComponent container) throws LRException
	{
		JPopupMenu popup=new JPopupMenu("title");
		create(container,popup,false);
		popup.setOpaque(true);
		popup.setLightWeightPopupEnabled(true);
	}

	/***************************************************************************
		Add a menu item to this popup menu.
		If 'menuItem' is null add a separator.
	*/
	public void addMenuItem(GraphicsHMenuitem menuItem)
	{
		JPopupMenu popup=(JPopupMenu)getComponent();
		if (menuItem==null) popup.addSeparator();
		else popup.add((JMenuItem)menuItem.getComponent());
	}

	/***************************************************************************
		Move the popup.
	*/
	public void moveTo(GraphicsVLocation location) throws LRException
	{
		GraphicsHComponentData myData=getComponentData();
		myData.left=location.getLeft();
		myData.top=location.getTop();
	}

	public void moveBy(GraphicsVSize size) throws LRException
	{
		GraphicsHComponentData myData=getComponentData();
		myData.left+=size.getWidth();
		myData.top+=size.getHeight();
	}

	public void moveBy(LVValue value,int direction) throws LRException
	{
		GraphicsHComponentData myData=getComponentData();
		switch (direction)
		{
		case LEFT:
			myData.left-=value.getIntegerValue();
			break;
		case RIGHT:
			myData.left+=value.getIntegerValue();
			break;
		case UP:
			myData.top-=value.getIntegerValue();
			break;
		case DOWN:
			myData.top+=value.getIntegerValue();
			break;
		}
	}

	/***************************************************************************
		Show or hide the popup menu.
	*/
	public void setVisible(boolean show) throws LRException
	{
		println("Show "+getName()+" "+show);
		if (!show) super.setVisible(false);
		GraphicsHComponentData myData=getComponentData();
		Object qData=program.getQueueData();
		if (qData instanceof LHEvent)
		{
			LHEvent event=(LHEvent)qData;
			if (event.getEvent() instanceof MouseEvent)
			{
				MouseEvent evt=(MouseEvent)event.getEvent();
				((JPopupMenu)getComponent()).show(evt.getComponent(),evt.getX(),evt.getY());
			}
		}
		else
		{
			JPopupMenu menu=(JPopupMenu)getComponent();
			Component parent=myData.parent.getComponent();
			menu.show(parent,parent.getX(),parent.getY());
		}
	}
}
