// GraphicsHSetDescription.java

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
	Set the description of a component.
*/
public class GraphicsHSetDescription extends LHHandler
{
	private GraphicsHSwingComponent component;
	private LVValue text;

	public GraphicsHSetDescription(int line,GraphicsHSwingComponent component,LVValue text)
	{
		this.line=line;
		this.component=component;
		this.text=text;
	}

	public int execute() throws LRException
	{
		component.setDescription(text);
		return pc+1;
	}
}

