// LHSort.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.handler;

/******************************************************************************
	Sort an array.
*/
public class LHSort extends LHHandler
{
	private LHVariableHandler handler;

	public LHSort(int line,LHVariableHandler handler)
	{
		super(line);
		this.handler=handler;
	}

	public int execute()
	{
		handler.sort();
		return pc+1;
	}
}

