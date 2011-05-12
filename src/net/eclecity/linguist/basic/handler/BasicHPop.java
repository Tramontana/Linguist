// BasicHPop.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

public class BasicHPop extends LHHandler
{
	private LHVariableHandler handler=null;

	/***************************************************************************
		Pop the return address of a subroutine (and continue).
	*/
	public BasicHPop(int line)
	{
		super(line);
	}

	/***************************************************************************
		Pop a variable from the program stack.
	*/
	public BasicHPop(int line,LHVariableHandler handler)
	{
		super(line);
		this.handler=handler;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		if (handler!=null)
		{
			Object value=program.pop();
			if (value instanceof LVValue)
			{
				if (handler instanceof BasicHVariable)
					((BasicHVariable)handler).setValue((LVValue)value);
				if (handler instanceof BasicHBuffer)
					((BasicHBuffer)handler).setValue((LVValue)value);
			}
			else if (value instanceof Object[])
			{
				handler.setDataArray((Object[])value);
			}
		}
		else program.popPC();
		return pc+1;
	}
}

