//	NetworkVSenderInfo.java

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
	Get information about the sender.
*/
public class NetworkVSenderInfo extends LVValue
{
	private LRProgram program;
	private int opcode;

	public NetworkVSenderInfo(LRProgram program,int opcode)
	{
		this.program=program;
		this.opcode=opcode;
	}

	public long getNumericValue() throws LRException
	{
		return longValue();
	}

	public String getStringValue()
	{
		try
		{
			Object[] qData=(Object[])program.getQueueData(Object[].class);
			// In this packet, the first item is the sending module and the second is its message.
			NetworkerModule sender=(NetworkerModule)qData[1];
			switch (opcode)
			{
				case NAME:
					return sender.getModuleName();
				case ADDRESS:
					return sender.getModuleAddress();
				case TYPE:
					return sender.getModuleType();
			}
		}
		catch (NullPointerException e) {}
		return "";
	}

	public static final int
		NAME=1,
		ADDRESS=2,
		TYPE=3;
}
