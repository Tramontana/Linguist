// GraphicsHAdd.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import javax.swing.AbstractButton;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Add something to something.
	<pre>
	[1.002 GT]  15/02/01  Add to a button group.
	[1.001 GT]  17/10/00  Pre-existing.
	</pre>
*/
public class GraphicsHAdd extends LHHandler
{
	/***************************************************************************
		Add a menu to a window.
	*/
	private GraphicsHWindow window=null;
	private GraphicsHMenu menu=null;

	public GraphicsHAdd(int line,GraphicsHWindow window,GraphicsHMenu menu)
	{
		this.line=line;
		this.window=window;
		this.menu=menu;
	}

	/***************************************************************************
		Add a menu or menu item to a popup menu.
	*/
	private GraphicsHPopupmenu popupMenu=null;
	private GraphicsHMenuitem menuItem=null;

	public GraphicsHAdd(int line,GraphicsHPopupmenu popupMenu,GraphicsHMenuitem menuItem)
	{
		this.line=line;
		this.popupMenu=popupMenu;
		this.menuItem=menuItem;
	}

	/***************************************************************************
		Add a menu or menu item to a menu.
	*/

	public GraphicsHAdd(int line,GraphicsHMenu menu,GraphicsHMenuitem menuItem)
	{
		this.line=line;
		this.menu=menu;
		this.menuItem=menuItem;
	}

	/***************************************************************************
		Add a style to a textpanel.
	*/
	private GraphicsHTextPanel panel=null;
	private GraphicsHStyle style;

	public GraphicsHAdd(int line,GraphicsHTextPanel panel,GraphicsHStyle style)
	{
		this.line=line;
		this.panel=panel;
		this.style=style;
	}

	/***************************************************************************
		Add a style to a textpanel.
	*/
	private GraphicsHCombobox comboBox=null;
	private LVValue value;

	public GraphicsHAdd(int line,GraphicsHCombobox comboBox,LVValue value)
	{
		this.line=line;
		this.comboBox=comboBox;
		this.value=value;
	}

	/***************************************************************************
		Add a component to a container.
	*/
	private GraphicsHComponent component=null;
	private GraphicsHComponent container=null;
	private Object constraints=null;

	public GraphicsHAdd(int line,GraphicsHComponent component,GraphicsHComponent container,Object constraints)
	{
		this.line=line;
		this.component=component;
		this.container=container;
		this.constraints=constraints;
	}

	/***************************************************************************
		Add to a button group.
	*/
	private GraphicsHButtonGroup buttonGroup=null;
	private GraphicsHComponent button=null;

	public GraphicsHAdd(int line,GraphicsHButtonGroup buttonGroup,GraphicsHComponent button)
	{
		this.line=line;
		this.buttonGroup=buttonGroup;
		this.button=button;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		if (window!=null) window.addMenu(menu);
		else if (popupMenu!=null) popupMenu.addMenuItem(menuItem);
		else if (menu!=null) menu.addMenuItem(menuItem);
		else if (panel!=null) panel.addStyle(style);
		else if (comboBox!=null) comboBox.addItem(value);
		else if (component!=null) container.add(component,constraints);
		else if (buttonGroup!=null) buttonGroup.add((AbstractButton)button.getComponent());
		return pc+1;
	}
}

