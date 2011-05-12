// GraphicsHTextControl.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

import net.eclecity.linguist.graphics.value.GraphicsVColor;
import net.eclecity.linguist.graphics.value.GraphicsVLocation;
import net.eclecity.linguist.graphics.value.GraphicsVSize;
import net.eclecity.linguist.handler.LHStringHolder;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.util.LUStringReplace;
import net.eclecity.linguist.value.LVValue;


/*******************************************************************************
 * Text area and text field handler.
 */
public class GraphicsHTextControl extends GraphicsHSwingComponent implements
		GraphicsHTextComponent, LHStringHolder
{
	public GraphicsHTextControl()
	{}

	public void create(GraphicsHComponent container, JComponent component,
			GraphicsVLocation location, GraphicsVSize size, boolean editable)
			throws LRException
	{
		component.setLocation(location.getLocation());
		if (size != null) getComponentData().sizeSpecified = true;
		else size = new GraphicsVSize(50, 20);
		component.setSize(size.getWidth(), size.getHeight());
		component.setBorder(null);
		create(container, component, false);
		container.getContentPane().add(component, 0);
		JTextComponent textComponent = (JTextComponent) component;
		textComponent.setEditable(editable);
		textComponent.setFocusable(editable);
	}

	public void setCaretColor(GraphicsVColor color) throws LRException
	{
		((JTextComponent) getComponent()).setCaretColor(color.getColor());
	}

	public void setText(LVValue text) throws LRException
	{
		setValue(text.getStringValue());
	}

	public String getText()
	{
		return getStringValue();
	}

	/****************************************************************************
	 * Implement the LHValueHolder interface.
	 */
	public void setValue(LVValue value) throws LRException
	{
		setValue(value.getStringValue());
	}

	public void setValue(long value)
	{
		setValue(String.valueOf(value));
	}

	public void setValue(String value)
	{
		((JTextComponent) getComponent()).setText(value);
	}

	public long getNumericValue()
	{
		try
		{
			return Long.parseLong(getStringValue());
		}
		catch (NumberFormatException e)
		{}
		return 0;
	}

	public String getStringValue()
	{
		return ((JTextComponent) getComponent()).getText();
	}

	public long getNumericValue(int n)
	{
		try
		{
			return Long.parseLong(getStringValue(n));
		}
		catch (NumberFormatException e)
		{
			return 0;
		}
	}

	public String getStringValue(int n)
	{
		return ((JTextComponent) getComponent(n)).getText();
	}

	public boolean isNumeric()
	{
		return false;
	}

	/****************************************************************************
	 * Clear the current element.
	 */
	public void clear()
	{
		setValue("");
	}

	/****************************************************************************
	 * Clear all elements.
	 */
	public void clearAll()
	{
		for (int n = 0; n < elements; n++)
			((JTextComponent) getComponent(n)).setText("");
	}

	////////////////////////////////////////////////////////////////////////////

	/****************************************************************************
	 * Implement the LHStringHolder interface.
	 */
	public void append(LVValue value) throws LRException
	{
		setValue(getStringValue() + value.getStringValue());
	}

	public void replace(LVValue string1, LVValue string2) throws LRException
	{
		new LUStringReplace(this, string1, string2);
	}

	public void trim()
	{
		setValue(getStringValue().trim());
	}

	/* (non-Javadoc)
	 * @see net.eclecity.linguist.graphics.handler.GraphicsHTextComponent#setHorizontalTextAlignment(int)
	 */
	public void setHorizontalTextAlignment(int alignment) throws LRException
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see net.eclecity.linguist.graphics.handler.GraphicsHTextComponent#setVerticalTextAlignment(int)
	 */
	public void setVerticalTextAlignment(int alignment) throws LRException
	{
		// TODO Auto-generated method stub
		
	}
}