//	NetworkVMessageName.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.value;

import net.eclecity.linguist.networker.NetworkerModule;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.runtime.LRProgram;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Get the name of the sender of a received message.
*/
public class NetworkVMessageName extends LVValue
{
	private LRProgram program;

	public NetworkVMessageName(LRProgram program)
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
		if (data instanceof Object[])
			return ((NetworkerModule)((Object[])data)[1]).getModuleName();
		return "";
	}
}
