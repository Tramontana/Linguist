//	NetworkVService.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.value;

import net.eclecity.linguist.network.handler.NetworkHClient;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Get information about services known to a client.
*/
public class NetworkVService extends LVValue
{
	private int opcode;
	private LVValue index;
	private NetworkHClient client;
	
	public static final int
		COUNT=1,
		NAME=2,
		ADDRESS=3,
		TYPE=4;

	public NetworkVService(int opcode,NetworkHClient client)
	{
		this.opcode=opcode;
		this.client=client;
	}

	public NetworkVService(int opcode,LVValue index,NetworkHClient client)
	{
		this.opcode=opcode;
		this.index=index;
		this.client=client;
	}

	public long getNumericValue() throws LRException
	{
		switch (opcode)
		{
			case COUNT:
				return client.getServices().length;
		}
		return 0;
	}

	public String getStringValue() throws LRException
	{
		switch (opcode)
		{
			case COUNT:
				return ""+client.getServices().length;
			case NAME:
				return client.getServices()[index.getIntegerValue()].getModuleName();
			case ADDRESS:
				return client.getServices()[index.getIntegerValue()].getModuleAddress();
			case TYPE:
				return client.getServices()[index.getIntegerValue()].getModuleType();
		}
		return "";
	}
}
