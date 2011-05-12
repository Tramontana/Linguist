//	NetworkKCreate.java

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
import net.eclecity.linguist.network.handler.NetworkHCreate;
import net.eclecity.linguist.network.handler.NetworkHService;
import net.eclecity.linguist.value.LVStringConstant;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	<pre>
	create {service} name {name} type {type}
	create {client} name {name} type {type} [notify once]
	<p>
	[1.001 GT]  03/05/01  New class.
	</pre>
*/
public class NetworkKCreate extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
		if (isSymbol())
		{
			LVValue name=new LVStringConstant("<Untitled>");
			if (getHandler() instanceof NetworkHService)
			{
				NetworkHService service=(NetworkHService)getHandler();
				LVValue type=new LVStringConstant("<>");
				while (true)
				{
					getNextToken();
					if (tokenIs("name")) name=getNextValue();
					else if (tokenIs("type")) type=getNextValue();
					else
					{
						unGetToken();
						break;
					}
				}
				return new NetworkHCreate(line,service,name,type);
			}
			if (getHandler() instanceof NetworkHClient)
			{
				NetworkHClient client=(NetworkHClient)getHandler();
				LVValue type=new LVStringConstant("<>");
				boolean notifyOnce=false;
				while (true)
				{
					getNextToken();
					if (tokenIs("name")) name=getNextValue();
					else if (tokenIs("type")) type=getNextValue();
					else if (tokenIs("notify"))
					{
						getNextToken();
						if (tokenIs("once")) notifyOnce=true;
						else dontUnderstandToken();
					}
					else
					{
						unGetToken();
						break;
					}
				}
				return new NetworkHCreate(line,client,name,type,notifyOnce);
			}
		}
	   return null;
	}
}

