// NetworkHUpdate.java

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
	Update a client (force a rebuild of its server list).
	<pre>
	[1.001 GT]  15/05/01  New class.
	</pre>
*/
public class NetworkHUpdate extends LHHandler
{
	private NetworkHClient client=null;

	public NetworkHUpdate(int line,NetworkHClient client)
	{
		this.line=line;
		this.client=client;
	}

	public int execute() throws LRException
	{
		if (client!=null) client.update();
		return pc+1;
	}
}

