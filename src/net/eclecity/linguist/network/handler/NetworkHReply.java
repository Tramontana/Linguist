// NetworkHReply.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Reply to a Networker module.
	This places a return value in the program which will be given back
	to the whoever started this thread using LRProgram.execute().
	<pre>
	[1.001 GT]  06/05/01  New class.
	</pre>
*/
public class NetworkHReply extends LHHandler
{
	private LVValue value;

	/***************************************************************************
		Send the packet.
	*/
	public NetworkHReply(int line,LVValue value)
	{
		this.line=line;
		this.value=value;
	}

	/***************************************************************************
		(Runtime)  Call the appropriate method.
	*/
	public int execute()
	{
		program.setReturnValue(value);
		return pc+1;
	}
}

