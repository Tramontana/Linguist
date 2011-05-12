// NetworkHDoNetworker.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.network.runtime.NetworkRBackground;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Do a networker operation.
*/
public class NetworkHDoNetworker extends LHHandler
{
	private int opcode;
	private Object data1;
	private Object data2;

	public NetworkHDoNetworker(int line,int opcode)
	{
		this(line,opcode,null,null);
	}

	public NetworkHDoNetworker(int line,int opcode,Object data1)
	{
		this(line,opcode,data1,null);
	}

	public NetworkHDoNetworker(int line,int opcode,Object data1,Object data2)
	{
		this.line=line;
		this.opcode=opcode;
		this.data1=data1;
		this.data2=data2;
	}

	public int execute() throws LRException
	{
		((NetworkRBackground)getBackground("network")).doNetworker(opcode,data1,data2);
		return pc+1;
	}
}

