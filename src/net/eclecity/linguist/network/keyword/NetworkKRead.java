//	NetworkKRead.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.keyword;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHStringHolder;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.network.NetworkLMessages;
import net.eclecity.linguist.network.handler.NetworkHRead;
import net.eclecity.linguist.network.handler.NetworkHSocket;

/******************************************************************************
	<pre>
	read {stringholder} from {socket}
	<p>
	[1.001 GT]  12/02/01  New class.
	</pre>
*/
public class NetworkKRead extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	   if (isSymbol())
	   {
	   	if (getHandler() instanceof LHStringHolder)
	   	{
		   	LHStringHolder stringHolder=(LHStringHolder)getHandler();
	   		skip("from");
   			if (isSymbol())
   			{
   				if (getHandler() instanceof NetworkHSocket)
   				{
						return new NetworkHRead(line,stringHolder,(NetworkHSocket)getHandler());
					}
					warning(this,NetworkLMessages.socketExpected(getToken()));
				}
			}
		}
	   return null;
	}
}

