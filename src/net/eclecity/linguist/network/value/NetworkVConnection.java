//	NetworkVConnection.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.value;

import net.eclecity.linguist.network.handler.NetworkHConnection;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Get the response from an HTTP connection.  This causes the request to be sent.
*/
public class NetworkVConnection extends LVValue
{
	private NetworkHConnection connection;

	public NetworkVConnection(NetworkHConnection connection)
	{
		this.connection=connection;
	}

	public long getNumericValue() throws LRException
	{
		return longValue();
	}

	public String getStringValue() throws LRException
	{
		return connection.getResponse();
	}
}
