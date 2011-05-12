// NetworkHWrite.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Write to a socket.
	<pre>
	[1.001 GT]  12/02/01  Pre-existing.
	</pre>
*/
public class NetworkHWrite extends LHHandler
{
	private NetworkHSocket socket=null;
	private LVValue value;

/******************************************************************************
	Write data to a socket.
*/
	public NetworkHWrite(int line,NetworkHSocket socket,LVValue value)
	{
		this.line=line;
		this.socket=socket;
		this.value=value;
	}

/******************************************************************************
	(Runtime)  Do it now.
*/
	public int execute() throws LRException
	{
		if (socket!=null) socket.write(value);
		return pc+1;
	}
}

