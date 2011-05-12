// BasicHPush.java

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
import net.eclecity.linguist.value.LVConstant;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Push a value onto the user stack.
*/
public class BasicHPush extends LHHandler
{
	private LVValue value;
	private LHVariableHandler handler;

	/***************************************************************************
		Push a value.
	*/
	public BasicHPush(int line,LVValue value)
	{
		super(line);
		this.value=value;
	}

	/***************************************************************************
		Push a variable handler.
	*/
	public BasicHPush(int line,LHVariableHandler handler)
	{
		super(line);
		this.handler=handler;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		if (value!=null) program.push(new LVConstant(value.getNumericValue()));
		else if (handler!=null) handler.push(program);
		return pc+1;
	}
}

