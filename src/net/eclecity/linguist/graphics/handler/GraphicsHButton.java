// GraphicsHButton.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;

import net.eclecity.linguist.graphics.value.GraphicsVLocation;
import net.eclecity.linguist.graphics.value.GraphicsVSize;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Button handler.
*/
public class GraphicsHButton extends GraphicsHLabel
{
	public GraphicsHButton() {}

	public void create(GraphicsHComponent container,int type,LVValue text,GraphicsVLocation location,GraphicsVSize size)
		throws LRException
	{
		AbstractButton button=null;
		switch (type)
		{
		case TYPE_DEFAULT:
			button=new JButton(text.getStringValue());
			break;
		case TYPE_TOGGraphicsLE:
			button=new JToggleButton(text.getStringValue());
			break;
		case TYPE_RADIO:
			button=new JRadioButton(text.getStringValue());
			break;
		case TYPE_CHECKBOX:
			button=new JCheckBox(text.getStringValue());
			break;
		}
		button.setOpaque(false);
		create(container,button,location,size);
		button.validate();
		button.addActionListener(this);
		if (container!=null) container.add(this);
	}
	
	public void setText(LVValue text) throws LRException
	{
		((JButton)getComponent()).setText(text.getStringValue());
		setSize();
	}
	
	public void setIcon(int view,GraphicsHImage image) throws LRException
	{
		setIcon(view,image.getIcon());
	}
	
	public void setIcon(int view,LVValue name) throws LRException
	{
		setIcon(view,new ImageIcon(name.getStringValue()));
	}
	
	public void setIcon(int view,ImageIcon icon) throws LRException
	{
		AbstractButton button=(AbstractButton)getComponent();
		if (button==null) throw new LRException(LRException.notCreated(getName()));
		switch (view)
		{
		case DEFAULT_ICON:
			button.setIcon(icon);
			break;
		case SELECTED_ICON:
			button.setSelectedIcon(icon);
			break;
		case PRESSED_ICON:
			button.setPressedIcon(icon);
			break;
		case DISABLED_ICON:
			button.setDisabledIcon(icon);
			break;
		case DISABLED_SELECTED_ICON:
			button.setDisabledSelectedIcon(icon);
			break;
		case ROLLOVER_ICON:
			button.setRolloverIcon(icon);
			break;
		case ROLLOVER_SELECTED_ICON:
			button.setRolloverSelectedIcon(icon);
			break;
		}
		setSize();
	}
	
	private void setSize() throws LRException
	{
		AbstractButton button=(AbstractButton)getComponent();
		setSize(button,button.getText(),button.getIcon(),
			button.getVerticalTextPosition(),button.getHorizontalTextPosition());
	}
	
	public void setEnabled(boolean enable) throws LRException
	{
		((AbstractButton)getComponent()).setEnabled(enable);
		super.setEnabled(enable,false);
	}
	
	public void setSelected(boolean select)
	{
		((AbstractButton)getComponent()).setSelected(select);
	}
	
	public boolean isSelected()
	{
		return ((AbstractButton)getComponent()).isSelected();
	}
	
	public void setHorizontalTextAlignment(int alignment)
	{
		((AbstractButton)getComponent()).setHorizontalAlignment(alignment);
	}
	
	public void setVerticalTextAlignment(int alignment)
	{
		((AbstractButton)getComponent()).setVerticalAlignment(alignment);
	}

	/***************************************************************************
		Return the 'value' of this item.
	*/
	public String getStringValue()
	{
		AbstractButton item=(AbstractButton)getComponent();
		return item.getActionCommand();
	}

	public static final int
		DEFAULT_ICON=0,
		SELECTED_ICON=1,
		PRESSED_ICON=2,
		DISABLED_ICON=3,
		DISABLED_SELECTED_ICON=4,
		ROLLOVER_ICON=5,
		ROLLOVER_SELECTED_ICON=6;

	public static final int
		TYPE_DEFAULT=0,
		TYPE_TOGGraphicsLE=1,
		TYPE_RADIO=2,
		TYPE_CHECKBOX=3;
}
