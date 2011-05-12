// NetworkHRead.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHStringHolder;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Read from a socket.
	Unlike 'on socket', this blocks until data is available
	or until the socket times out.
	<pre>
	[1.001 GT]  12/02/01  New class.
	</pre>
*/
public class NetworkHRead extends LHHandler
{
	private NetworkHSocket socket=null;
	private LHStringHolder stringHolder;

	/***************************************************************************
		Read from a socket.
	*/
	public NetworkHRead(int line,LHStringHolder stringHolder,NetworkHSocket socket)
	{
		this.line=line;
		this.stringHolder=stringHolder;
		this.socket=socket;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		if (stringHolder!=null) stringHolder.setValue(socket.read());
		return pc+1;
	}
}

