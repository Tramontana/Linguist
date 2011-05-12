// NetworkHPost.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;

public class NetworkHPost extends LHHandler
{
	private NetworkHConnection connection=null;

	/***************************************************************************
		Post a CGI request to a server.
	*/
	public NetworkHPost(int line,NetworkHConnection connection)
	{
		this.line=line;
		this.connection=connection;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		if (connection!=null) connection.post();
		return pc+1;
	}
}

