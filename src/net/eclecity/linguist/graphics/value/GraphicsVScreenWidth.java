//	GraphicsVScreenWidth.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.value;

import java.awt.Frame;

import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Return the width of the screen.
*/
public class GraphicsVScreenWidth extends LVValue
{
	public GraphicsVScreenWidth()
	{
	}

	public long getNumericValue()
	{
		return (new Frame().getToolkit().getScreenSize().width);
	}

	public String getStringValue()
	{
		return String.valueOf(getNumericValue());
	}
}
