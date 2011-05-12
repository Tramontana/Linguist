//	NetworkCSenderIs.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.condition;

import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.network.handler.NetworkHMessage;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Test if the sender of a message is the same as the source or destination
	of a message.
	<pre>
	[1.001 GT]  12/02/01  New class.
	</pre>
*/
public class NetworkCSenderIs extends LCCondition
{
	private NetworkHMessage message=null;
	private boolean source;
	private boolean sense;

	public NetworkCSenderIs(NetworkHMessage message,boolean source,boolean sense)
	{
		this.message=message;
		this.source=source;
		this.sense=sense;
	}

	public boolean test() throws LRException
	{
		boolean result=false;
		if (message!=null)
		{
			result=message.isSender(source);
			if (!sense) result=!result;
		}
		return result;
	}
}
