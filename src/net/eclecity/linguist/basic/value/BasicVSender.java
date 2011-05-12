//	BasicVSender.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.value;

import net.eclecity.linguist.runtime.LRMessage;
import net.eclecity.linguist.runtime.LRProgram;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Get the sender of the last message.
*/
public class BasicVSender extends LVValue
{
	LRProgram program;

	public BasicVSender(LRProgram program)
	{
		this.program=program;
	}

	public long getNumericValue()
	{
		return 0;
	}

	public String getStringValue()
	{
		LRMessage message=(LRMessage)program.getQueueData();
		return message.caller.getScriptName();
	}
}
