//	ServletVSessionValue.java

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
	Return a session value.  These are all strings.
*/
public class ServletVSessionValue extends LVValue
{
	private ServletRBackground background=null;
	private LRProgram program;
	private LVValue value;

	public ServletVSessionValue(LRProgram program,LVValue value)
	{
		this.program=program;
		this.value=value;
	}

	public long getNumericValue() throws LRException
	{
	   return longValue();
	}

	public String getStringValue() throws LRException
	{
		if (background==null) background=(ServletRBackground)program.getBackground("servlet");
		return background.getSessionValue(value);
	}
}
