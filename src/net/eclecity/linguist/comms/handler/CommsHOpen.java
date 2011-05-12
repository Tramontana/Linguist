// CommsHOpen.java

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
	Open a port.
*/
public class CommsHOpen extends LHHandler
{
	private CommsHPort port=null;
	private LVValue name;
	boolean rawMode;
	boolean input;
	boolean output;

	public CommsHOpen(int line,CommsHPort port,LVValue name,boolean rawMode,
		boolean input,boolean output)
	{
		this.line=line;
		this.port=port;
		this.name=name;
		this.rawMode=rawMode;
		this.input=input;
		this.output=output;
	}

	public int execute() throws LRException
	{
		if (port!=null) port.open(name,rawMode,input,output);
		return pc+1;
	}
}

