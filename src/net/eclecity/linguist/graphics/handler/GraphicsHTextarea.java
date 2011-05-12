// GraphicsHTextarea.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import javax.swing.JTextArea;

import net.eclecity.linguist.graphics.value.GraphicsVLocation;
import net.eclecity.linguist.graphics.value.GraphicsVSize;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Text area handler.
*/
public class GraphicsHTextarea extends GraphicsHTextControl
{
	public GraphicsHTextarea() {}

	public void create(GraphicsHComponent container,LVValue text,GraphicsVLocation location,
		GraphicsVSize size,boolean editable) throws LRException
	{
		JTextArea textArea=new JTextArea(text.getStringValue());
		create(container,textArea,location,size,editable);
	}
}
