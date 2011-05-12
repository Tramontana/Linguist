//	GraphicsVAction.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.value;

import net.eclecity.linguist.graphics.handler.GraphicsHButton;
import net.eclecity.linguist.graphics.handler.GraphicsHDialog;
import net.eclecity.linguist.graphics.handler.GraphicsHMenuitem;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Return the action value of a component.
*/
public class GraphicsVAction extends LVValue
{
	private GraphicsHButton button;
	private GraphicsHMenuitem menuItem;
	private GraphicsHDialog dialog;
	private int code;

	public GraphicsVAction(GraphicsHButton button)
	{
		this.button=button;
	}

	public GraphicsVAction(GraphicsHMenuitem menuItem)
	{
		this.menuItem=menuItem;
	}

	public GraphicsVAction(GraphicsHDialog dialog,int code)
	{
		this.dialog=dialog;
		this.code=code;
	}

	public long getNumericValue() throws LRException
	{
		if (dialog!=null) return dialog.getNumericValue(code);
		return longValue();
	}

	public String getStringValue() throws LRException
	{
		if (button!=null) return button.getStringValue();
		else if (menuItem!=null) return menuItem.getStringValue();
		else if (dialog!=null) return dialog.getStringValue(code);
		return "";
	}
}
