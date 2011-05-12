// BasicHReplace.java

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
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Replace one string with another in a string holder.
*/
public class BasicHReplace extends LHHandler
{
	private LHStringHolder holder;		// the string holder
	private LVValue string1;				// the first string
	private LVValue string2;				// the second string

	public BasicHReplace(int line,LHStringHolder holder,LVValue string1,LVValue string2)
	{
		super(line);
		this.holder=holder;
		this.string1=string1;
		this.string2=string2;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		holder.replace(string1,string2);
		return pc+1;
	}
}

