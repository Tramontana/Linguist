//	DataVStatus.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.data.value;

import net.eclecity.linguist.data.runtime.DataRBackground;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.runtime.LRProgram;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Get the current status.
*/
public class DataVStatus extends LVValue
{
	private LRProgram program;

	public DataVStatus(LRProgram program)
	{
		this.program=program;
	}

	public long getNumericValue() throws LRException
	{
	   return longValue();
	}

	public String getStringValue() throws LRException
	{
		return ((DataRBackground)program.getBackground("data")).getStatus();
	}
}
