// ServletHParameter.java

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
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Add a parameter for testing.
*/
public class ServletHParameter extends LHHandler
{
	private LVValue key;
	private LVValue data;

	public ServletHParameter(int line,LVValue key,LVValue data)
	{
		this.line=line;
		this.key=key;
		this.data=data;
	}

	public int execute() throws LRException
	{
		((ServletRBackground)getBackground("servlet")).putParameter(key.getStringValue(),data.getStringValue());
		return pc+1;
	}
}

