// CommsHFlush.java

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
	Flush a port.
*/
public class CommsHFlush extends LHHandler
{
	private CommsHPort port;

	public CommsHFlush(int line,CommsHPort port)
	{
		this.line=line;
		this.port=port;
	}

	public int execute() throws LRException
	{
		port.flush();
		return pc+1;
	}
}

