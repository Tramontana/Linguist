//	GraphicsKCombobox.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.keyword;

import net.eclecity.linguist.graphics.handler.GraphicsHCombobox;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.keyword.LKVariableHandler;

/******************************************************************************
	combobox {name} {elements}
*/
public class GraphicsKCombobox extends LKVariableHandler
{
	/***************************************************************************
		Return an instance of the runtime type.
	*/
	public LHVariableHandler getRuntimeClass() { return new GraphicsHCombobox(); }
}
