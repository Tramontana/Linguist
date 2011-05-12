//	BasicVModuleName.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.value;

import net.eclecity.linguist.runtime.LRProgram;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Get the name of the current module script.
*/
public class BasicVModuleName extends LVValue
{
	private LRProgram program;

	public BasicVModuleName(LRProgram program)
	{
		this.program=program;
	}
		

	public long getNumericValue()
	{
		return 0;
	}

	public String getStringValue()
	{
		return program.getScriptName();
	}
}
