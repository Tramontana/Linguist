// IoHRead.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.io.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Read a port on a device.
*/
public class IoHRead extends LHHandler
{
	private IoHDevice device=null;
	LVValue port=null;

	public IoHRead(int line,IoHDevice device,LVValue port)
	{
		this.line=line;
		this.device=device;
		this.port=port;
	}

	public int execute() throws LRException
	{
		if (device!=null)
		{
			if (port!=null) device.read(port);
		}
		return pc+1;
	}
}

