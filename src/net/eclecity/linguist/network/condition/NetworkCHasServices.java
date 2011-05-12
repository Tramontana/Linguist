//	NetworkCHasServices.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.condition;

import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.network.handler.NetworkHClient;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Test if a client has been notified of any services.
	<pre>
	[1.001 GT]  12/02/01  New class.
	</pre>
*/
public class NetworkCHasServices extends LCCondition
{
	private NetworkHClient client=null;

	public NetworkCHasServices(NetworkHClient client)
	{
		this.client=client;
	}

	public boolean test() throws LRException
	{
		if (client!=null) return client.hasServices();
		return false;
	}
}
