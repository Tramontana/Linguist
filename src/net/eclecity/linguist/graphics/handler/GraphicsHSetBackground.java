// GraphicsHSetBackground.java

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
	Set the background of a panel.
	<pre>
	[1.002 GT]  30/10/00  Remove setting a background image for a window.
	[1.001 GT]  19/10/00  Pre-existing.
	</pre>
*/
public class GraphicsHSetBackground extends LHHandler
{
	private GraphicsHPanel panel;
	private GraphicsHImage image=null;
	private LVValue name=null;

	public GraphicsHSetBackground(int line,GraphicsHPanel panel,GraphicsHImage image)
	{
		this.line=line;
		this.panel=panel;
		this.image=image;
	}

	public GraphicsHSetBackground(int line,GraphicsHPanel panel,LVValue name)
	{
		this.line=line;
		this.panel=panel;
		this.name=name;
	}

	/***************************************************************************
		(Runtime) Do it now.
	*/
	public int execute() throws LRException
	{
		if (panel!=null)
		{
			if (image!=null) panel.setBackground(image);
			else if (name!=null) panel.setBackground(name);
		}
		return pc+1;
	}
}

