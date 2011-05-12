// BasicHPackage.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	A package variable.  (Incomplete)
*/
public class BasicHPackage extends LHVariableHandler
{
	public Object newElement(Object extra) { return new Integer(0); }
	
	public void load(LVValue file)
	{
	}
}

