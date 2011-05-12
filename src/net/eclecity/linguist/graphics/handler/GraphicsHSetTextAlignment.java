// GraphicsHSetTextAlignment.java

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
	Set the text alignment of a text component.
*/
public class GraphicsHSetTextAlignment extends LHHandler
{
	private GraphicsHTextComponent component;
	private boolean vertical;
	private int alignment;

	public GraphicsHSetTextAlignment(int line,GraphicsHTextComponent component,boolean vertical,int alignment)
	{
		this.line=line;
		this.component=component;
		this.vertical=vertical;
		this.alignment=alignment;
	}

	public int execute() throws LRException
	{
		if (vertical) component.setVerticalTextAlignment(alignment);
		else component.setHorizontalTextAlignment(alignment);
		return pc+1;
	}
}

