//	NetworkCHasError.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.condition;

import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.network.handler.NetworkHMessage;
import net.eclecity.linguist.network.handler.NetworkHSocket;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Test if a socket or message has an error.
	<pre>
	[1.001 GT]  12/02/01  New class.
	</pre>
*/
public class NetworkCHasError extends LCCondition
{
	private NetworkHSocket socket=null;
	private NetworkHMessage message=null;

	public NetworkCHasError(NetworkHSocket socket)
	{
		this.socket=socket;
	}

	public NetworkCHasError(NetworkHMessage message)
	{
		this.message=message;
	}

	public boolean test() throws LRException
	{
		if (socket!=null) return socket.hasError();
		else if (message!=null) return message.hasError();
		else return false;
	}
}
