// GraphicsHSave.java

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
	Save the icon of a label.
	<pre>
	[1.001 GT]  22/04/03  New class.
	</pre>
*/
public class GraphicsHSave extends LHHandler
{
	private GraphicsHLabel label;
	private LVValue name;

	public GraphicsHSave(int line,GraphicsHLabel label,LVValue name)
	{
		this.line=line;
		this.label=label;
		this.name=name;
	}

	/***************************************************************************
		(Runtime) Do it now.
	*/
	public int execute() throws LRException
	{
		if (label!=null) label.saveIcon(name);
		return pc+1;
	}
}

