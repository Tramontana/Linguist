// CommsHOn.java

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
	Deal with an event.
*/
public class CommsHOn extends LHHandler
{
	private CommsHPort port=null;
	private int next;				// the next instruction

	public CommsHOn(int line,CommsHPort port,int next)
	{
		this.line=line;
		this.next=next;
		this.port=port;
	}

	public int execute() throws LRException
	{
		if (port!=null) port.onInput(pc+1);
		return next;
	}
}

