//	ServletVSessionID.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.servlet.value;

import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.runtime.LRProgram;
import net.eclecity.linguist.servlet.runtime.ServletRBackground;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Return the session id.
*/
public class ServletVSessionID extends LVValue
{
	private ServletRBackground background=null;
	private LRProgram program;

	public ServletVSessionID(LRProgram program)
	{
		this.program=program;
	}

	public long getNumericValue()
	{
	   return 0;
	}

	public String getStringValue() throws LRException
	{
		if (background==null) background=(ServletRBackground)program.getBackground("servlet");
		return background.getSessionID();
	}
}
