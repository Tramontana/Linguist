//	NetworkKOn.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.keyword;

import net.eclecity.linguist.handler.LHFlag;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHNoop;
import net.eclecity.linguist.handler.LHStop;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.network.handler.NetworkHClient;
import net.eclecity.linguist.network.handler.NetworkHFTPClient;
import net.eclecity.linguist.network.handler.NetworkHOn;
import net.eclecity.linguist.network.handler.NetworkHService;
import net.eclecity.linguist.network.handler.NetworkHSocket;

/******************************************************************************
	<pre>
	on {socket} {block}
	on notify {client} {block}
	on watchdog in {client} {block}
	on tell {service}/{client} {block}
	on ask {service}/{client} {block}
	on {ftpclient} error {block}
	<p>
	[1.001 GT]  12/02/01  Pre-existing.
	[1.002 GT]  25/05/01  Add 'on watchdog'.
	[1.003 GT]  09/07/03  Add 'on ftp error'.
	</pre>
*/
public class NetworkKOn extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	   if (isSymbol())
		{
			if (getHandler() instanceof NetworkHSocket)
			{
				// on {socket} {block}
				NetworkHSocket socket=(NetworkHSocket)getHandler();
				int here=getPC();
				addCommand(new LHNoop(0));
				doKeyword();
				addCommand(new LHStop(line));
				setCommandAt(new NetworkHOn(line,socket,getPC()),here);
				return new LHFlag();
			}
			if (getHandler() instanceof NetworkHFTPClient)
			{
				// on {ftpclient} error {block}
				NetworkHFTPClient ftpClient=(NetworkHFTPClient)getHandler();
				getNextToken();
				if (tokenIs("error"))
				{
					int here=getPC();
					addCommand(new LHNoop(0));
					doKeyword();
					addCommand(new LHStop(line));
					setCommandAt(new NetworkHOn(line,ftpClient,NetworkHFTPClient.ON_ERROR,getPC()),here);
					return new LHFlag();
				}
			}
		}
		else if (tokenIs("notify"))
		{
			getNextToken();
		   if (isSymbol())
			{
				if (getHandler() instanceof NetworkHClient)
				{
					// on notify {client} {block}
					NetworkHClient client=(NetworkHClient)getHandler();
					int here=getPC();
					addCommand(new LHNoop(0));
					doKeyword();
					addCommand(new LHStop(line));
					setCommandAt(new NetworkHOn(line,client,NetworkHClient.ON_NOTIFY,getPC()),here);
					return new LHFlag();
				}
			}
		}
		else if (tokenIs("watchdog"))
		{
			skip("in");
		   if (isSymbol())
			{
				if (getHandler() instanceof NetworkHClient)
				{
					// on watchdog in {client} {block}
					NetworkHClient client=(NetworkHClient)getHandler();
					int here=getPC();
					addCommand(new LHNoop(0));
					doKeyword();
					addCommand(new LHStop(line));
					setCommandAt(new NetworkHOn(line,client,NetworkHClient.ON_WATCHDOG,getPC()),here);
					return new LHFlag();
				}
			}
		}
		else if (tokenIs("tell"))
		{
			getNextToken();
		   if (isSymbol())
			{
				if (getHandler() instanceof NetworkHService)
				{
					// on tell {service} {block}
					NetworkHService service=(NetworkHService)getHandler();
					int here=getPC();
					addCommand(new LHNoop(0));
					doKeyword();
					addCommand(new LHStop(line));
					setCommandAt(new NetworkHOn(line,service,NetworkHService.ON_TELL,getPC()),here);
					return new LHFlag();
				}
				if (getHandler() instanceof NetworkHClient)
				{
					// on tell {client} {block}
					NetworkHClient client=(NetworkHClient)getHandler();
					int here=getPC();
					addCommand(new LHNoop(0));
					doKeyword();
					addCommand(new LHStop(line));
					setCommandAt(new NetworkHOn(line,client,NetworkHClient.ON_TELL,getPC()),here);
					return new LHFlag();
				}
			}
		}
		else if (tokenIs("ask"))
		{
			getNextToken();
		   if (isSymbol())
			{
				if (getHandler() instanceof NetworkHService)
				{
					// on ask {service} {block}
					NetworkHService service=(NetworkHService)getHandler();
					int here=getPC();
					addCommand(new LHNoop(0));
					doKeyword();
					addCommand(new LHStop(line));
					setCommandAt(new NetworkHOn(line,service,NetworkHService.ON_ASK,getPC()),here);
					return new LHFlag();
				}
				if (getHandler() instanceof NetworkHClient)
				{
					// on ask {client} {block}
					NetworkHClient client=(NetworkHClient)getHandler();
					int here=getPC();
					addCommand(new LHNoop(0));
					doKeyword();
					addCommand(new LHStop(line));
					setCommandAt(new NetworkHOn(line,client,NetworkHClient.ON_ASK,getPC()),here);
					return new LHFlag();
				}
			}
		}
		return null;
	}
}

