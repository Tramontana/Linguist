// NetworkHDeactivate.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.networker.Networker;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Deactivate an IP address.
	This will happen anyway if the address stops responding to multicast
	ping messages, but this command forces it to happen immediately.
*/
public class NetworkHDeactivate extends LHHandler
{
	private LVValue address;

	public NetworkHDeactivate(int line,LVValue address)
	{
		this.line=line;
		this.address=address;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute()
	{
		try
		{
			Networker.getInstance().deactivate(address.getStringValue());
		}
		catch (Exception e) {}
		return pc+1;
	}
}

