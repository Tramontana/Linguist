// BasicHSetEventName.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHEventSource;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Set the name of an event handler.
*/
public class BasicHSetEventName extends LHHandler
{
	private LHEventSource source;
	private LVValue name;

	public BasicHSetEventName(int line,LHEventSource source,LVValue name)
	{
		super(line);
		this.source=source;
		this.name=name;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		source.setEventName(name);
		return pc+1;
	}
}

