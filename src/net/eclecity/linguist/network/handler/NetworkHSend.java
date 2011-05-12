// NetworkHSend.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Send a message.
	<pre>
	[1.001 GT]  17/02/01  New class.
	</pre>
*/
public class NetworkHSend extends LHHandler
{
	private NetworkHMessage message=null;
	private LVValue text;

	/***************************************************************************
		Send a message.
	*/
	public NetworkHSend(int line,NetworkHMessage message,LVValue text)
	{
		this.line=line;
		this.message=message;
		this.text=text;
	}

	/***************************************************************************
		(Runtime)  Call the appropriate method.
	*/
	public int execute() throws LRException
	{
		if (message!=null) message.send(text);
		return pc+1;
	}
}

