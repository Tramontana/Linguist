//	NetworkKWrite.java

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
import net.eclecity.linguist.network.handler.NetworkHSocket;
import net.eclecity.linguist.network.handler.NetworkHWrite;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	<pre>
	write {value} to {socket}
	<p>
	[1.001 GT]  12/02/01  Pre-existing.
	</pre>
*/
public class NetworkKWrite extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	   LVValue value=null;
	   try { value=getValue(); } catch (LLException e) { return null; }
	   skip("to");
   	if (isSymbol())
   	{
   		if (getHandler() instanceof NetworkHSocket)
   		{
				return new NetworkHWrite(line,(NetworkHSocket)getHandler(),value);
			}
			warning(this,NetworkLMessages.socketExpected(getToken()));
		}
	   return null;
	}
}

