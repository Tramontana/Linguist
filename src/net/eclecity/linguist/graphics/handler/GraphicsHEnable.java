// GraphicsHEnable.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Enable or disable a button or label.
*/
public class GraphicsHEnable extends LHHandler
{
	private GraphicsHButton button=null;
	private GraphicsHLabel label=null;
	private boolean enable;

	public GraphicsHEnable(int line,GraphicsHButton button,boolean enable)
	{
		this.line=line;
		this.button=button;
		this.enable=enable;
	}

	public GraphicsHEnable(int line,GraphicsHLabel label,boolean enable)
	{
		this.line=line;
		this.label=label;
		this.enable=enable;
	}

	public int execute() throws LRException
	{
		if (button!=null) button.setEnabled(enable);
		else if (label!=null) label.setEnabled(enable);
		return pc+1;
	}
}

