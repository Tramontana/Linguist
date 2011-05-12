//	NetworkVMessage.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.value;

import net.eclecity.linguist.network.handler.NetworkHMessage;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Get information from a message.
*/
public class NetworkVMessage extends LVValue
{
	private NetworkHMessage message;
	private int opcode;

	public NetworkVMessage(NetworkHMessage message,int opcode)
	{
		this.message=message;
		this.opcode=opcode;
	}

	public long getNumericValue() throws LRException
	{
		return longValue();
	}

	public String getStringValue() throws LRException
	{
		return message.get(opcode);
	}
}
