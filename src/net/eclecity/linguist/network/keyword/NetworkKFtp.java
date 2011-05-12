//	NetworkKFtp.java

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
import net.eclecity.linguist.network.handler.NetworkHFTP;
import net.eclecity.linguist.network.handler.NetworkHFTPClient;

/******************************************************************************
	ftp {ftpclient} create {url}
	ftp {ftpclient} login {name} {password}
	ftp {ftpclient} chdir {path}
	ftp {ftpclient} type ascii/binary
	ftp {ftpclient} put {local path} {file name}
	ftp {ftpclient} get {local path} {file name}
	ftp {ftpclient} quit
*/
public class NetworkKFtp extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	  	if (isSymbol())
	  	{
			if (getHandler() instanceof NetworkHFTPClient)
			{
				NetworkHFTPClient ftpClient=(NetworkHFTPClient)getHandler();
				getNextToken();
				if (tokenIs("create"))
				{
					return new NetworkHFTP(line,ftpClient,NetworkHFTPClient.CREATE,getNextValue());
				}
				else if (tokenIs("login"))
				{
					return new NetworkHFTP(line,ftpClient,NetworkHFTPClient.LOGIN,getNextValue(),
						getNextValue());
				}
				else if (tokenIs("chdir"))
				{
					return new NetworkHFTP(line,ftpClient,NetworkHFTPClient.CHDIR,getNextValue());
				}
				else if (tokenIs("type"))
				{
					return new NetworkHFTP(line,ftpClient,NetworkHFTPClient.SET_TYPE,getNextValue());
				}
				else if (tokenIs("put"))
				{
					return new NetworkHFTP(line,ftpClient,NetworkHFTPClient.PUT_FILE,getNextValue(),
						getNextValue());
				}
				else if (tokenIs("get"))
				{
					return new NetworkHFTP(line,ftpClient,NetworkHFTPClient.GET_FILE,getNextValue(),
						getNextValue());
				}
				else if (tokenIs("quit"))
				{
					return new NetworkHFTP(line,ftpClient,NetworkHFTPClient.QUIT);
				}
			}
			warning(this,NetworkLMessages.badFTPCommand(getToken()));
		}
	   return null;
	}
}

