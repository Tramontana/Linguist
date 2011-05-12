// NetworkHCreate.java

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
	Create something.
	<pre>
	[1.001 GT]  03/05/01  New class.
	</pre>
*/
public class NetworkHCreate extends LHHandler
{
	private NetworkHService service=null;
	private NetworkHClient client=null;
	private LVValue name;
	private LVValue type;
	private boolean notifyOnce;

	/***************************************************************************
		Create a service.
	*/
	public NetworkHCreate(int line,NetworkHService service,LVValue name,LVValue type)
	{
		this.line=line;
		this.service=service;
		this.name=name;
		this.type=type;
	}

	/***************************************************************************
		Create a client.
	*/
	public NetworkHCreate(int line,NetworkHClient client,LVValue name,LVValue type,boolean notifyOnce)
	{
		this.line=line;
		this.client=client;
		this.name=name;
		this.type=type;
		this.notifyOnce=notifyOnce;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		if (service!=null) service.create(name,type);
		else if (client!=null) client.create(name,type,notifyOnce);
		return pc+1;
	}
}

