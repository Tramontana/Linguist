//	NetworkKUpdate.java

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
import net.eclecity.linguist.network.NetworkLMessages;
import net.eclecity.linguist.network.handler.NetworkHClient;
import net.eclecity.linguist.network.handler.NetworkHUpdate;

/******************************************************************************
	<pre>
	update {client}
	<p>
	[1.001 GT]  15/05/01  New class.
	</pre>
*/
public class NetworkKUpdate extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	  	if (isSymbol())
	  	{
			LHHandler handler=getHandler();
			if (handler instanceof NetworkHClient)
			{
				// update {client}}
				return new NetworkHUpdate(line,(NetworkHClient)handler);
		   }
			warning(this,NetworkLMessages.clientExpected(getToken()));
		}
	   return null;
	}
}

