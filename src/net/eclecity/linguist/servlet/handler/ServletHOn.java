// ServletHOn.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.servlet.handler;

import net.eclecity.linguist.handler.LHHandler;

/******************************************************************************
	The onGet() and onPost() handlers arrive through here.
*/
public class ServletHOn extends LHHandler
{
	private int next;				// the next instruction

	public ServletHOn(int line,int next)
	{
		this.line=line;
		this.next=next;
	}

	public int execute()
	{
		program.setExternalEventHandler(pc+1);
		return next;
	}
}

