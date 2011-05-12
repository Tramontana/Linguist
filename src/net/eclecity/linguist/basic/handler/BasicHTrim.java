// BasicHTrim.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHStringHolder;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Trim a string.
*/
public class BasicHTrim extends LHHandler
{
	private LHStringHolder handler;

	public BasicHTrim(int line,LHStringHolder handler)
	{
		super(line);
		this.handler=handler;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		handler.trim();
		return pc+1;
	}
}

