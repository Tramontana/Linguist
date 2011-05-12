//	NetworkKAdd.java

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
import net.eclecity.linguist.network.handler.NetworkHClient;
import net.eclecity.linguist.network.handler.NetworkHDoNetworker;
import net.eclecity.linguist.network.handler.NetworkHService;
import net.eclecity.linguist.network.runtime.NetworkRBackground;

/******************************************************************************
	<pre>
	add {service}/{client}
	<p>
	[1.001 GT]  03/05/01  New class.
	</pre>
*/
public class NetworkKAdd extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	   if (isSymbol())
	   {
	   	if (getHandler() instanceof NetworkHService)
	   	{
	   		return new NetworkHDoNetworker(line,NetworkRBackground.ADD_SERVICE_TO_NETWORKER,getHandler());
			}
	   	if (getHandler() instanceof NetworkHClient)
	   	{
	   		return new NetworkHDoNetworker(line,NetworkRBackground.ADD_CLIENT_TO_NETWORKER,getHandler());
			}
		}
	   return null;
	}
}

