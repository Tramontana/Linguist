//	NetworkCNetError.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.condition;

import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.runtime.LRProgram;

/******************************************************************************
	Test if the last network operation returned an error.
	Used after 'send {packet}'.
	<pre>
	[1.001 GT]  18/02/01  New class.
	</pre>
*/
public class NetworkCNetError extends LCCondition
{
	private LRProgram program=null;

	public NetworkCNetError(LRProgram program)
	{
		this.program=program;
	}

	public boolean test()
	{
		Boolean status=(Boolean)program.getQueueData(Boolean.class);
		if (status==null) return false;
		return status.booleanValue();
	}
}
