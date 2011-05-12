// GraphicsHSetStatus.java

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
	Set the status of a component.
*/
public class GraphicsHSetStatus extends LHHandler
{
	private GraphicsHSwingComponent component;
	private LVValue status;

	public GraphicsHSetStatus(int line,GraphicsHSwingComponent component,LVValue status)
	{
		this.line=line;
		this.component=component;
		this.status=status;
	}

	public int execute() throws LRException
	{
		component.setStatus(status);
		return pc+1;
	}
}

