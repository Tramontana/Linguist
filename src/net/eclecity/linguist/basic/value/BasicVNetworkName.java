//	BasicVNetworkName.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.value;

import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.runtime.LRProgram;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Get the network name of this machine.
	This may have been defined using 'set network name';
	if not the name is read from the system.
*/
public class BasicVNetworkName extends LVValue
{
	private LRProgram program;

	public BasicVNetworkName(LRProgram program)
	{
		this.program=program;
	}

	public long getNumericValue()
	{
		return 0;
	}

	public String getStringValue() throws LRException
	{
		return program.getBackground("basic").getNetworkName();
	}
}
