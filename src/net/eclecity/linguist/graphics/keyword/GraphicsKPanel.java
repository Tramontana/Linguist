//	GraphicsKPanel.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.keyword;

import net.eclecity.linguist.graphics.handler.GraphicsHPanel;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.keyword.LKVariableHandler;

/******************************************************************************
	<pre>
	panel {name} {elements}
	<p>
	[1.001 GT]  19/10/00  New class.
	</pre>
*/
public class GraphicsKPanel extends LKVariableHandler
{
	/***************************************************************************
		Return an instance of the runtime type.
	*/
	public LHVariableHandler getRuntimeClass() { return new GraphicsHPanel(); }
}
