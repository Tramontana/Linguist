// GraphicsHMenu.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Menu handler.
*/
public class GraphicsHMenu extends GraphicsHMenuitem
{
	public GraphicsHMenu() {}

	/***************************************************************************
		Create a new menu.
	*/
	public void create(LVValue text) throws LRException
	{
		JMenu menu=new JMenu(text.getStringValue());
		create(null,menu,false);
		menu.setVisible(true);
	}
	
	/***************************************************************************
		Add a menu item to this menu.
		If 'menuItem' is null add a separator.
	*/
	public void addMenuItem(GraphicsHMenuitem menuItem)
	{
		JMenu menu=(JMenu)getComponent();
		if (menu!=null)
		{
			if (menuItem==null) menu.addSeparator();
			else
			{
				JMenuItem item=(JMenuItem)menuItem.getComponent();
				if (item!=null) menu.add(item);
			}
		}
	}
}
