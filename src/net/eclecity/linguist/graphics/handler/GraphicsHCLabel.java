// GraphicsHCLabel.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import java.awt.Color;

import javax.swing.JLabel;

/******************************************************************************
	A special kind of JLabel.
	It acknowledges that some of its children may overlap it.
*/
public class GraphicsHCLabel extends JLabel
{
	public GraphicsHCLabel()
	{
		this("");
	}

	public GraphicsHCLabel(String text)
	{
		super(text);
		setBackground(new Color(0,0,0));
		setBorder(null);
	}

	public boolean isOptimizedDrawingEnabled()
	{
		return false;
	}

	public boolean isDoubleBuffered()
	{
		return true;
	}
}
