// BasicHRun.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Run a class file.
*/
public class BasicHRun extends LHHandler
{
	private LVValue name;

	public BasicHRun(int line,LVValue name)
	{
		super(line);
		this.name=name;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute()
	{
		try
		{
			Class c=Class.forName(name.getStringValue());
			c.newInstance();
		}
		catch (Throwable ignored) {}
		return pc+1;
	}
}

