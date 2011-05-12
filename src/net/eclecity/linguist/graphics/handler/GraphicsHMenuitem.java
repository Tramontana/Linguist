// GraphicsHMenuitem.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Popup menu handler.
	<pre>
	[1.001 GT]  15/02/01  Pre-existing.
	</pre>
*/
public class GraphicsHMenuitem extends GraphicsHSwingComponent
{
	public GraphicsHMenuitem() {}

	/***************************************************************************
		Create a new menu item.
	*/
	public void create(LVValue text,int style) throws LRException
	{
		if (text!=null)
		{
			JMenuItem item=null;
			switch (style)
			{
				case PLAIN:
					item=new JMenuItem(text.getStringValue());
					break;
				case CHECKBOX:
					item=new JCheckBoxMenuItem(text.getStringValue());
					break;
				case RADIOBUTTON:
					item=new JRadioButtonMenuItem(text.getStringValue());
					break;
			}
			create(null,item,false);
			item.setVisible(true);
			item.addActionListener(this);
		}
	}
	
	/***************************************************************************
		Get the style of this component.
	*/
	public int getStyle()
	{
		JMenuItem item=(JMenuItem)getComponent();
		if (item instanceof JCheckBoxMenuItem) return CHECKBOX;
		if (item instanceof JRadioButtonMenuItem) return RADIOBUTTON;
		return PLAIN;
	}
	
	/***************************************************************************
		Select or deselect this component.
	*/
	public void setSelected(boolean selected)
	{
		JMenuItem item=(JMenuItem)getComponent();
		if (item instanceof JCheckBoxMenuItem)
			((JCheckBoxMenuItem)item).doClick();
		else if (item instanceof JRadioButtonMenuItem)
			((JRadioButtonMenuItem)item).doClick();
	}

	/***************************************************************************
		Return the 'value' of this item.
	*/
	public String getStringValue()
	{
		JMenuItem item=(JMenuItem)getComponent();
		return item.getActionCommand();
	}
	
	public static final int
		PLAIN=0,
		CHECKBOX=1,
		RADIOBUTTON=2;
}
