// LHGoto.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.handler;

/******************************************************************************
	A goto instruction.
*/
public class LHGoto extends LHHandler
{
	private int target;		// where to go to

	public LHGoto(int line,int target)
	{
		super(line);
		this.target=target;
	}

	public int execute()
	{
		return target;
	}
}

