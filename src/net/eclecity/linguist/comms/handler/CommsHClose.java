// CommsHClose.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.comms.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Close a port.
*/
public class CommsHClose extends LHHandler
{
	private CommsHPort port=null;

	public CommsHClose(int line,CommsHPort port)
	{
		this.line=line;
		this.port=port;
	}

	public int execute() throws LRException
	{
		if (port!=null) port.close();
		return pc+1;
	}
}

