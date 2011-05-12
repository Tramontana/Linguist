//	CommsVPort.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.comms.value;

import net.eclecity.linguist.comms.handler.CommsHPort;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Get the input from a port.
*/
public class CommsVPort extends LVValue
{
	private CommsHPort port;

	public CommsVPort(CommsHPort port)
	{
		this.port=port;
	}

	public long getNumericValue() throws LRException
	{
		return port.getNextByte();
	}

	public String getStringValue() throws LRException
	{
		return port.getNextLine();
	}
}
