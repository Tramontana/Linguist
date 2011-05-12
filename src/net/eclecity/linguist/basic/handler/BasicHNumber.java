// BasicHNumber.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHConstant;

/******************************************************************************
	A constant integer.
	These don't go into the compiled program.
*/
public class BasicHNumber extends LHConstant
{
	public BasicHNumber(int line,String name,Long value)
	{
		super(line,name,value,true);
	}

	public long getNumber() { return ((Long)getValue()).longValue(); }
	public int execute() { return pc+1; }
}

