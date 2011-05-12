// NetworkHSet.java

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
	Set something.
	<pre>
	[1.001 GT]  12/02/01  Pre-existing.
	</pre>
*/
public class NetworkHSet extends LHHandler
{
	private NetworkHConnection connection=null;
	private NetworkHAdvisor advisor=null;
	private NetworkHSocket socket=null;
	private NetworkHClient client=null;
	private NetworkHMessage message=null;
	private LVValue name=null;
	private LVValue value=null;
	private Object data;
	private Object data2;
	private int opcode;
	private int mode;

	/***************************************************************************
		Set the URL of a connection.
	*/
	public NetworkHSet(int line,NetworkHConnection connection,LVValue value)
	{
		this.line=line;
		this.connection=connection;
		this.value=value;
	}

	/***************************************************************************
		Set a property of a connection.
	*/
	public NetworkHSet(int line,NetworkHConnection connection,LVValue name,LVValue value)
	{
		this.line=line;
		this.connection=connection;
		this.name=name;
		this.value=value;
	}

	/***************************************************************************
		Set a property of an advisor.
	*/
	public NetworkHSet(int line,NetworkHAdvisor advisor,LVValue name,LVValue value)
	{
		this.line=line;
		this.advisor=advisor;
		this.name=name;
		this.value=value;
	}

	/***************************************************************************
		Set the mode of a connection.  Default is GET.
	*/
	public NetworkHSet(int line,NetworkHConnection connection,int mode)
	{
		this.line=line;
		this.mode=mode;
	}

	/***************************************************************************
		Set the mode of a socket.
	*/
	public NetworkHSet(int line,NetworkHSocket socket,int mode)
	{
		this.line=line;
		this.socket=socket;
		this.mode=mode;
	}

	/***************************************************************************
		Set the address or port of a socket.
	*/
	public NetworkHSet(int line,NetworkHSocket socket,LVValue value,int mode)
	{
		this.line=line;
		this.socket=socket;
		this.value=value;
		this.mode=mode;
	}

	/***************************************************************************
		Set an attribute of a client.
	*/
	public NetworkHSet(int line,NetworkHClient client,int opcode,Object data)
	{
		this.line=line;
		this.client=client;
		this.opcode=opcode;
		this.data=data;
	}

	/***************************************************************************
		Set an attribute of a message.
	*/
	public NetworkHSet(int line,NetworkHMessage message,int opcode,Object data)
	{
		this.line=line;
		this.message=message;
		this.opcode=opcode;
		this.data=data;
	}

	/***************************************************************************
		Set an attribute of a message.
	*/
	public NetworkHSet(int line,NetworkHMessage message,int opcode,Object data,Object data2)
	{
		this.line=line;
		this.message=message;
		this.opcode=opcode;
		this.data=data;
		this.data2=data2;
	}

	/***************************************************************************
		(Runtime)  Call the appropriate method.
	*/
	public int execute() throws LRException
	{
		if (connection!=null)
		{
			if (name!=null) connection.setProperty(name,value);
			else if (value!=null) connection.setURL(value);
			else connection.setMode(mode);
		}
		else if (advisor!=null) advisor.setProperty(name,value);
		else if (socket!=null)
		{
			if (value!=null) socket.setValue(value,mode);
			else socket.setMode(mode);
		}
		else if (client!=null) client.set(opcode,data);
		else if (message!=null) message.set(opcode,data,data2);
		return pc+1;
	}
}

