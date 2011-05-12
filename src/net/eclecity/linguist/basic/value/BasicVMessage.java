//	BasicVMessage.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.value;

import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.runtime.LRMessage;
import net.eclecity.linguist.runtime.LRProgram;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Get the last message received.
*/
public class BasicVMessage extends LVValue
{
	LRProgram program;

	public BasicVMessage(LRProgram program)
	{
		this.program=program;
	}

	public long getNumericValue() throws LRException
	{
		return longValue();
	}

	/***************************************************************************
		Retrieve the message from the queue data.
	*/
	public String getStringValue()
	{
		// Retrieve the queue data.
		// This is a LRMessage object that contains the message and the caller.
		LRMessage msg=(LRMessage)program.getQueueData();
		if (msg==null) return "";
		return msg.message;
	}
}
