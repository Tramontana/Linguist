// GraphicsHSelect.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import net.eclecity.linguist.handler.LHHandler;

/******************************************************************************
	Select or deselect a button.
	<pre>
	[1.001 GT]  15/02/01  Pre-existing class.
	</pre>
*/
public class GraphicsHSelect extends LHHandler
{
	private GraphicsHButton button=null;
	private GraphicsHMenuitem menuItem=null;
	private boolean select;

	public GraphicsHSelect(int line,GraphicsHButton button,boolean select)
	{
		this.line=line;
		this.button=button;
		this.select=select;
	}

	public GraphicsHSelect(int line,GraphicsHMenuitem menuItem,boolean select)
	{
		this.line=line;
		this.menuItem=menuItem;
		this.select=select;
	}

	public int execute()
	{
		if (button!=null) button.setSelected(select);
		else if (menuItem!=null) menuItem.setSelected(select);
		return pc+1;
	}
}

