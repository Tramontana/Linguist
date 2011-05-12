// LHStop.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.handler;

/******************************************************************************
	Stop this thread.
*/
public class LHStop extends LHHandler
{
	public LHStop(int line)
	{
		super(line);
	}

	public int execute()
	{
		return 0;
	}
}

