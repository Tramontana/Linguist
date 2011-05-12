//	NetworkKStart.java

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
import net.eclecity.linguist.network.handler.NetworkHDoNetworker;
import net.eclecity.linguist.network.runtime.NetworkRBackground;

/******************************************************************************
	<pre>
	start networker
	start watchdog
	<p>
	[1.001 GT]  25/05/01  New class: start watchdog.
	</pre>
*/
public class NetworkKStart extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		getNextToken();
	   if (tokenIs("the")) getNextToken();
	   if (tokenIs("networker"))
	   {
			return new NetworkHDoNetworker(line,NetworkRBackground.START_NETWORKER);
	   }
	   if (tokenIs("watchdog"))
	   {
			return new NetworkHDoNetworker(line,NetworkRBackground.START_WATCHDOG);
	   }
      return null;
	}
}

