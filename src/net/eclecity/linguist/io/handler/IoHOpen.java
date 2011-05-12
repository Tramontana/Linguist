// IoHOpen.java

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
	Open a device.
*/
public class IoHOpen extends LHHandler
{
	private IoHDevice device=null;
	LVValue name;

	public IoHOpen(int line,IoHDevice device,LVValue name)
	{
		this.line=line;
		this.device=device;
		this.name=name;
	}

	public int execute() throws LRException
	{
		if (device!=null)
		{
			device.open(name);
		}
		return pc+1;
	}
}

