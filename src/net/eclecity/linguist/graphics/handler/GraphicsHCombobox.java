// GraphicsHCombobox.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import javax.swing.JComboBox;

import net.eclecity.linguist.graphics.value.GraphicsVLocation;
import net.eclecity.linguist.graphics.value.GraphicsVSize;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Combobox handler.
*/
public class GraphicsHCombobox extends GraphicsHSwingComponent
{
	public GraphicsHCombobox() {}

	public void create(GraphicsHComponent container,GraphicsVLocation location,GraphicsVSize size) throws LRException
	{
		JComboBox comboBox=new JComboBox();
		comboBox.setLocation(location.getLocation());
		if (size!=null) getComponentData().sizeSpecified=true;
		else size=new GraphicsVSize(50,20);
		comboBox.setSize(size.getWidth(),size.getHeight());
		comboBox.setBorder(null);
		create(container,comboBox,false);
		container.getContentPane().add(comboBox,0);
	}
	
	public void addItem(LVValue item) throws LRException
	{
		((JComboBox)getComponent()).addItem(item.getStringValue());
	}
	
	public int getSelectedIndex()
	{
		return ((JComboBox)getComponent()).getSelectedIndex();
	}
	
	public String getValue()
	{
		return (String)((JComboBox)getComponent()).getSelectedItem();
	}
}
