//	NetworkCHasData.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.condition;

import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.network.handler.NetworkHSocket;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Test if a socket has data.
*/
public class NetworkCHasData extends LCCondition
{
	private NetworkHSocket socket=null;

	public NetworkCHasData(NetworkHSocket socket)
	{
		this.socket=socket;
	}

	public boolean test() throws LRException
	{
		if (socket!=null) return socket.hasData();
		return false;
	}
}
