// GraphicsHTextComponent.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import java.awt.Color;
import java.awt.Font;

import net.eclecity.linguist.graphics.value.GraphicsVFont;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	An interface defining a component that uses text.
*/
public interface GraphicsHTextComponent
{
	public void setText(LVValue text) throws LRException;
	public String getText() throws LRException;
	public void setFont(GraphicsVFont font) throws LRException;
	public Font getFont() throws LRException;
	public Color getColor() throws LRException;

	// Not used by all implementations
	public void setHorizontalTextAlignment(int alignment) throws LRException;
	public void setVerticalTextAlignment(int alignment) throws LRException;
}
