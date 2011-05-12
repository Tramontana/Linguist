//	ServletCNewSession.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.servlet.condition;

import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.runtime.LRProgram;
import net.eclecity.linguist.servlet.runtime.ServletRBackground;

/******************************************************************************
	Return true if the session is a new one.
*/
public class ServletCNewSession extends LCCondition
{
	private ServletRBackground background=null;
	private LRProgram program;

	public ServletCNewSession(LRProgram program)
	{
		this.program=program;
	}

	public boolean test() throws LRException
	{
		if (background==null) background=(ServletRBackground)program.getBackground("servlet");
		return background.isNewSession();
	}
}
