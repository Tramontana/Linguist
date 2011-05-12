//	IoKDevice.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.io.keyword;

import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.io.handler.IoHDevice;
import net.eclecity.linguist.keyword.LKVariableHandler;

/******************************************************************************
	device {name} {elements}
*/
public class IoKDevice extends LKVariableHandler
{
	/***************************************************************************
		Return an instance of the runtime type.
	*/
	public LHVariableHandler getRuntimeClass() { return new IoHDevice(); }
}
