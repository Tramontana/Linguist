//	GraphicsVSelectionIndex.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.value;

import net.eclecity.linguist.graphics.handler.GraphicsHCombobox;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Return the index of the selected item in a combo box.
*/
public class GraphicsVSelectionIndex extends LVValue
{
	private GraphicsHCombobox comboBox=null;

	public GraphicsVSelectionIndex(GraphicsHCombobox comboBox)
	{
		this.comboBox=comboBox;
	}

	public long getNumericValue()
	{
		return comboBox.getSelectedIndex();
	}

	public String getStringValue()
	{
		return String.valueOf(getNumericValue());
	}
}
