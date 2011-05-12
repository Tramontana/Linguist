// IoHSet.java

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
	Set a port on a device.
*/
public class IoHSet extends LHHandler
{
	private IoHDevice device=null;
	LVValue port=null;
	LVValue value=null;

	public IoHSet(int line,IoHDevice device,LVValue port,LVValue value)
	{
		this.line=line;
		this.device=device;
		this.port=port;
		this.value=value;
	}

	public int execute() throws LRException
	{
		if (device!=null)
		{
			if (port!=null) device.set(port,value);
		}
		return pc+1;
	}
}

