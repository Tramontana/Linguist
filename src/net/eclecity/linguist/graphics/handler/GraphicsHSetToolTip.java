// GraphicsHSetToolTip.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Set the tooltip of a component.
*/
public class GraphicsHSetToolTip extends LHHandler
{
	private GraphicsHSwingComponent component;
	private LVValue text;

	public GraphicsHSetToolTip(int line,GraphicsHSwingComponent component,LVValue text)
	{
		this.line=line;
		this.component=component;
		this.text=text;
	}

	public int execute() throws LRException
	{
		component.setToolTip(text);
		return pc+1;
	}
}

