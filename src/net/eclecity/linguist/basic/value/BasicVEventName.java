//	BasicVEventName.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.value;

import net.eclecity.linguist.handler.LHEvent;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.runtime.LRProgram;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Get the name of the event that started this thread.
*/
public class BasicVEventName extends LVValue
{
	LRProgram program;

	public BasicVEventName(LRProgram program)
	{
		this.program=program;
	}

	public long getNumericValue()
	{
		return 0;
	}

	public String getStringValue() throws LRException
	{
		return (((LHEvent)program.getQueueData()).getSource().getEventName());
	}
}
