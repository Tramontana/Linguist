//	NetworkKDeactivate.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.keyword;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.network.handler.NetworkHDeactivate;

/******************************************************************************
	<pre>
	deactivate {address}
	<p>
	[1.001 GT]  13/05/03  New class.
	</pre>
*/
public class NetworkKDeactivate extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   return new NetworkHDeactivate(line,getNextValue());
	}
}

