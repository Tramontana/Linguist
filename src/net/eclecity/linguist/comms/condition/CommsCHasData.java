//	CommsCHasData.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.comms.condition;

import net.eclecity.linguist.comms.handler.CommsHPort;
import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Test if a port has data.
*/
public class CommsCHasData extends LCCondition
{
	private CommsHPort port=null;

	public CommsCHasData(CommsHPort port)
	{
		this.port=port;
	}

	public boolean test() throws LRException
	{
		if (port!=null) return port.hasData();
		return false;
	}
}
