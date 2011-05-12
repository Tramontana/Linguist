// CommsHWrite.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.comms.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Write to a port.
*/
public class CommsHWrite extends LHHandler
{
	private CommsHPort port=null;
	private LVValue value;

	public CommsHWrite(int line,CommsHPort port,LVValue value)
	{
		this.line=line;
		this.port=port;
		this.value=value;
	}

	public int execute() throws LRException
	{
		if (port!=null) port.write(value);
		return pc+1;
	}
}

