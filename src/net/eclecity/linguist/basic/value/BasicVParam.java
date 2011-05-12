//	BasicVParam.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.value;

import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.runtime.LRProgram;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Get the command-line parameter.
*/
public class BasicVParam extends LVValue
{
	LRProgram program;

	public BasicVParam(LRProgram program)
	{
		this.program=program;
	}

	public long getNumericValue() throws LRException
	{
		return longValue();
	}

	public String getStringValue()
	{
		Object param=program.getParam();
		if (param instanceof String) return (String)param;
		return "";
	}
}
