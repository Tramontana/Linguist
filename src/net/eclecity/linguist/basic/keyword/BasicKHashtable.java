//	BasicKHashtable.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.handler.BasicHHashtable;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.keyword.LKVariableHandler;

/******************************************************************************
	hashtable {name} {elements}
*/
public class BasicKHashtable extends LKVariableHandler
{
	/***************************************************************************
		Return an instance of the runtime type.
	*/
	public LHVariableHandler getRuntimeClass() { return new BasicHHashtable(); }
}
