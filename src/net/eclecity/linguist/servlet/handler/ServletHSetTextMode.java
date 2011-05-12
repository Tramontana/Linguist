// ServletHSetTextMode.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.servlet.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.servlet.runtime.ServletRBackground;

/******************************************************************************
	Set the response mode to text.
*/
public class ServletHSetTextMode extends LHHandler
{
	public ServletHSetTextMode(int line)
	{
		this.line=line;
	}

	/***************************************************************************
		(Runtime)  Call the appropriate method.
	*/
	public int execute() throws LRException
	{
		ServletRBackground background=(ServletRBackground)getBackground("servlet");
		background.setTextMode();
		return pc+1;
	}
}

