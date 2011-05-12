// GraphicsHSetVisible.java

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
	Set the visibility of a component.
*/
public class GraphicsHSetVisible extends LHHandler
{
	private GraphicsHComponent component;
	private LVValue left;
	private LVValue top;
	private boolean visible;
	private boolean hideAll;

	/***************************************************************************
		Set the visibility of a component.
	*/
	public GraphicsHSetVisible(int line,GraphicsHComponent component)
	{
		this.line=line;
		this.component=component;
		this.hideAll=true;
	}

	/***************************************************************************
		Set the visibility of a component.
	*/
	public GraphicsHSetVisible(int line,GraphicsHComponent component,boolean visible)
	{
		this.line=line;
		this.component=component;
		this.visible=visible;
	}

	/***************************************************************************
		Set the visibility of a component.
	*/
	public GraphicsHSetVisible(int line,GraphicsHComponent component,LVValue left,LVValue top,boolean visible)
	{
		this.line=line;
		this.component=component;
		this.visible=visible;
		this.left=left;
		this.top=top;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		if (component!=null)
		{
			if (hideAll) component.hideAll();
			component.setVisible(visible,left,top);
		}
		return pc+1;
	}
}

