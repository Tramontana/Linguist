// NetworkHOpen.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Open a file or socket.
*/
public class NetworkHOpen extends LHHandler
{
	private NetworkHSocket socket=null;

	public NetworkHOpen(int line,NetworkHSocket socket)
	{
		this.line=line;
		this.socket=socket;
	}

	public int execute() throws LRException
	{
		if (socket!=null) socket.open();
		return pc+1;
	}
}

