//	BasicVTimerID.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.value;

import net.eclecity.linguist.basic.handler.BasicHTimer;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.runtime.LRProgram;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Return the ID of the timer that just fired.
*/
public class BasicVTimerID extends LVValue
{
	private LRProgram program;

	public BasicVTimerID(LRProgram program)
	{
		this.program=program;
	}

	public long getNumericValue() throws LRException
	{
		return ((BasicHTimer)program.getQueueData(BasicHTimer.class)).getID();
	}

	public String getStringValue() throws LRException
	{
		return ""+getNumericValue();
	}
}
