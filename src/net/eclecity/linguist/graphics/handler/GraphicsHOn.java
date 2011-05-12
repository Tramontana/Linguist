// GraphicsHOn.java

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
	Process a graphics action.
	<pre>
	[1.002 GT]  20/10/00  Deal with spreadsheet events.
	[1.001 GT]  14/10/00  Pre-existing.
	</pre>
*/
public class GraphicsHOn extends LHHandler
{
	private GraphicsHWindow window=null;
	private GraphicsHFrame frame=null;
	private GraphicsHButton button=null;
	private GraphicsHLabel label=null;
	private GraphicsHTextControl textControl=null;
	private GraphicsHStyle style=null;
	private GraphicsHMenuitem menuItem=null;
	private GraphicsHComponent component=null;
	private int opcode;
	private int eventType;
	private int next;

	/***************************************************************************
		Deal with a window closing.
	*/
	public GraphicsHOn(int line,int opcode,GraphicsHWindow window,int next)
	{
		this.line=line;
		this.opcode=opcode;
		this.window=window;
		this.next=next;
	}

	/***************************************************************************
		Deal with a frame closing.
	*/
	public GraphicsHOn(int line,int opcode,GraphicsHFrame frame,int next)
	{
		this.line=line;
		this.opcode=opcode;
		this.frame=frame;
		this.next=next;
	}

	/***************************************************************************
		Deal with a button click.
	*/
	public GraphicsHOn(int line,GraphicsHButton button,int next)
	{
		this.line=line;
		this.button=button;
		this.next=next;
	}

	/***************************************************************************
		Deal with a label click.
	*/
	public GraphicsHOn(int line,GraphicsHLabel label,int next)
	{
		this.line=line;
		this.label=label;
		this.next=next;
	}

	/***************************************************************************
		Deal with a change in a text area.
	*/
	public GraphicsHOn(int line,GraphicsHTextControl textControl,int next)
	{
		this.line=line;
		this.textControl=textControl;
		this.next=next;
	}

	/***************************************************************************
		Deal with a style event.
	*/
	public GraphicsHOn(int line,GraphicsHStyle style,int next)
	{
		this.line=line;
		this.style=style;
		this.next=next;
	}

	/***************************************************************************
		Deal with a popup menu event.
	*/
	public GraphicsHOn(int line,GraphicsHMenuitem menuItem,int next)
	{
		this.line=line;
		this.menuItem=menuItem;
		this.next=next;
	}

	/***************************************************************************
		Deal with an event in a component.
	*/
	public GraphicsHOn(int line,GraphicsHComponent component,int eventType,int next)
	{
		this.line=line;
		this.component=component;
		this.eventType=eventType;
		this.next=next;
	}

	/***************************************************************************
		(Runtime) Do it now.
	*/
	public int execute() throws LRException
	{
		if (window!=null)
		{
			switch (opcode)
			{
				case GraphicsHComponent.ON_CLOSE:
					window.onWindowClose(pc+1);
					break;
				case GraphicsHComponent.ON_RESIZE:
					window.onWindowResize(pc+1);
					break;
				case GraphicsHComponent.ON_ICONIFY:
					window.onWindowIconify(pc+1);
					break;
				case GraphicsHComponent.ON_DEICONIFY:
					window.onWindowDeiconify(pc+1);
					break;
			}
		}
		else if (frame!=null)
		{
			switch (opcode)
			{
				case GraphicsHComponent.ON_CLOSE:
					frame.onWindowClose(pc+1);
					break;
				case GraphicsHComponent.ON_RESIZE:
					frame.onWindowResize(pc+1);
					break;
			}
		}
		else if (button!=null) button.onEvent(GraphicsHComponent.ACTION,pc+1);
		else if (label!=null) label.onEvent(GraphicsHComponent.MOUSE_DOWN,pc+1);
		else if (textControl!=null) textControl.onEvent(GraphicsHComponent.KEY_TYPED,pc+1);
		else if (style!=null) style.onAction(pc+1);
		else if (menuItem!=null) menuItem.onEvent(GraphicsHComponent.ACTION,pc+1);
		else if (component!=null) component.onEvent(eventType,pc+1);
		return next;
	}
}

