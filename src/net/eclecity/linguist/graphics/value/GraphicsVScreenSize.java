//	GraphicsVScreenSize.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.value;

import java.awt.Dimension;
import java.awt.Toolkit;

/******************************************************************************
	Return the size of the screen.
*/
public class GraphicsVScreenSize extends GraphicsVSize
{
	public GraphicsVScreenSize()
	{
	}

	public Dimension getSize()
	{
		return Toolkit.getDefaultToolkit().getScreenSize();
	}
}
