// GraphicsHSet.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import net.eclecity.linguist.graphics.value.GraphicsVColor;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Set something.
*/
public class GraphicsHSet extends LHHandler
{
	private GraphicsHWindow window=null;
	private GraphicsHFont font=null;
	private GraphicsHTextControl textControl=null;
	private GraphicsHTextfield textField=null;
	private LVValue title=null;
	private GraphicsVColor color=null;
	private LVValue columns=null;
	private int mode;

	/***************************************************************************
		Set the title of a window.
	*/
	public GraphicsHSet(int line,GraphicsHWindow window,LVValue title)
	{
		this.line=line;
		this.window=window;
		this.title=title;
	}

	/***************************************************************************
		Set the color of a font.
	*/
	public GraphicsHSet(int line,GraphicsHFont font,GraphicsVColor color)
	{
		this.line=line;
		this.font=font;
		this.color=color;
	}

	/***************************************************************************
		Set the caret color of a text component.
	*/
	public GraphicsHSet(int line,GraphicsHTextControl textControl,GraphicsVColor color)
	{
		this.line=line;
		this.textControl=textControl;
		this.color=color;
	}

	/***************************************************************************
		Set the mode of a text field.
	*/
	public GraphicsHSet(int line,GraphicsHTextfield textField,int mode)
	{
		this.line=line;
		this.textField=textField;
		this.mode=mode;
	}

	/***************************************************************************
		Set the columns of a text field.
	*/
	public GraphicsHSet(int line,GraphicsHTextfield textField,LVValue columns)
	{
		this.line=line;
		this.textField=textField;
		this.columns=columns;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		if (window!=null) window.setTitle(title);
		else if (font!=null) font.setColor(color);
		else if (textControl!=null) textControl.setCaretColor(color);
		else if (textField!=null)
		{
			if (columns!=null) textField.setColumns(columns);
			else textField.setMode(mode);
		}
		return pc+1;
	}
}

