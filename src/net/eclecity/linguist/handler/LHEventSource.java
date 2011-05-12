// LHEventSource

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.handler;

import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

public interface LHEventSource
{
	public void setEventName(LVValue name) throws LRException;
	public String getEventName() throws LRException;
}
