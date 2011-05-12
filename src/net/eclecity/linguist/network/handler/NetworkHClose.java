// NetworkHClose.java

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
	Close a socket.
*/
public class NetworkHClose extends LHHandler
{
	private NetworkHSocket socket=null;

	public NetworkHClose(int line,NetworkHSocket socket)
	{
		this.line=line;
		this.socket=socket;
	}

	public int execute() throws LRException
	{
		if (socket!=null) socket.close();
		return pc+1;
	}
}

