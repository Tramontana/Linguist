// GraphicsHSetIcon.java

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
	Set an icon of a button, label or canvas.
	<pre>
	[1.002 GT]  01/01/01  Add canvas.
	[1.001 GT]  14/10/00  Pre-existing.
	</pre>
*/
public class GraphicsHSetIcon extends LHHandler
{
	private GraphicsHLabel label;
	private GraphicsHButton button;
	private GraphicsHCanvas canvas;
	private int view;
	private GraphicsHImage image;
	private LVValue name;
	private boolean scaled;

	public GraphicsHSetIcon(int line,GraphicsHLabel label,GraphicsHImage image,boolean scaled)
	{
		this.line=line;
		this.label=label;
		this.image=image;
		this.scaled=scaled;
	}

	public GraphicsHSetIcon(int line,GraphicsHLabel label,LVValue name,boolean scaled)
	{
		this.line=line;
		this.label=label;
		this.name=name;
		this.scaled=scaled;
	}

	public GraphicsHSetIcon(int line,GraphicsHButton button,int view,GraphicsHImage image)
	{
		this.line=line;
		this.button=button;
		this.view=view;
		this.image=image;
	}

	public GraphicsHSetIcon(int line,GraphicsHButton button,int view,LVValue name)
	{
		this.line=line;
		this.button=button;
		this.view=view;
		this.name=name;
	}

	public GraphicsHSetIcon(int line,GraphicsHCanvas canvas,GraphicsHImage image)
	{
		this.line=line;
		this.canvas=canvas;
		this.image=image;
	}

	public GraphicsHSetIcon(int line,GraphicsHCanvas canvas,LVValue name)
	{
		this.line=line;
		this.canvas=canvas;
		this.name=name;
	}

	/***************************************************************************
		(Runtime) Do it now.
	*/
	public int execute() throws LRException
	{
		if (image!=null)
		{
			if (label!=null) label.setIcon(image,scaled);
			else if (button!=null) button.setIcon(view,image);
			else if (canvas!=null) canvas.setIcon(image);
		}
		else if (name!=null)
		{
			if (label!=null) label.setIcon(name,scaled);
			else if (button!=null) button.setIcon(view,name);
			else if (canvas!=null) canvas.setIcon(name);
		}
		return pc+1;
	}
}

