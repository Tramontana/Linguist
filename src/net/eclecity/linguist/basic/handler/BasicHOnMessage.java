// BasicHOnMessage.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHHandler;

/******************************************************************************
	Deal with a message.
*/
public class BasicHOnMessage extends LHHandler
{
	private int next;				// the next instruction

	public BasicHOnMessage(int line,int next)
	{
		super(line);
		this.next=next;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute()
	{
		program.setMessageHandler(pc+1);
		return next;
	}
}

