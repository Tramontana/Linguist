// NetworkHOn.java

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
	Deal with an event.
	<pre>
	[1.001 GT]  12/02/01  Pre-existing.
	</pre>
*/
public class NetworkHOn extends LHHandler
{
	private int next;				// the next instruction

	/***************************************************************************
		Handle an event from a socket.
	*/
	private NetworkHSocket socket;

	public NetworkHOn(int line,NetworkHSocket socket,int next)
	{
		this.line=line;
		this.socket=socket;
		this.next=next;
	}

	/***************************************************************************
		Handle an event from a client.
	*/
	private NetworkHClient client;
	private int opcode;

	public NetworkHOn(int line,NetworkHClient client,int opcode,int next)
	{
		this.line=line;
		this.client=client;
		this.opcode=opcode;
		this.next=next;
	}

	/***************************************************************************
		Handle an event from a service.
	*/
	private NetworkHService service;

	public NetworkHOn(int line,NetworkHService service,int opcode,int next)
	{
		this.line=line;
		this.service=service;
		this.opcode=opcode;
		this.next=next;
	}

	/***************************************************************************
		Handle an event from an FTP client.
	*/
	private NetworkHFTPClient ftpClient;

	public NetworkHOn(int line,NetworkHFTPClient ftpClient,int opcode,int next)
	{
		this.line=line;
		this.ftpClient=ftpClient;
		this.opcode=opcode;
		this.next=next;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		if (socket!=null) socket.onInput(pc+1);
		else if (service!=null) service.onEvent(opcode,pc+1);
		else if (client!=null) client.onEvent(opcode,pc+1);
		else if (ftpClient!=null) ftpClient.onEvent(opcode,pc+1);
		return next;
	}
}

