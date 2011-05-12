//	GraphicsKFrame.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.keyword;

import net.eclecity.linguist.graphics.handler.GraphicsHFrame;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.keyword.LKVariableHandler;

/******************************************************************************
	Frame handler.<br>
	This wraps a JFrame and gives access to much of its functionality.
	<p>Syntax: frame {name} {elements}
	<p><pre>
	[1.001 GT]  26/08/00  Created.
	</pre>
*/
public class GraphicsKFrame extends LKVariableHandler
{
	/***************************************************************************
		Return an instance of the runtime type.
	*/
	public LHVariableHandler getRuntimeClass() { return new GraphicsHFrame(); }
}
