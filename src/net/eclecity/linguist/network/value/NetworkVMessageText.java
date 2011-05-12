//	NetworkVMessageText.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.value;

import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.runtime.LRProgram;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Get the text of a received message.
*/
public class NetworkVMessageText extends LVValue
{
	private LRProgram program;

	public NetworkVMessageText(LRProgram program)
	{
		this.program=program;
	}

	public long getNumericValue() throws LRException
	{
		return longValue();
	}

	public String getStringValue()
	{
		Object data=program.getQueueData();
		try
		{
			if (data instanceof Object[]) return (String)((Object[])data)[2];
		}
		catch (Exception e) {}
		return "";
	}
}
