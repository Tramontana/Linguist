// NetworkHFTP.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

public class NetworkHFTP extends LHHandler
{
	private NetworkHFTPClient ftpClient;
	private int code;
	private LVValue value1;
	private LVValue value2;

	/***************************************************************************
		Hande an FTP action.
	*/
	public NetworkHFTP(int line,NetworkHFTPClient ftpClient,int code)
	{
		this(line,ftpClient,code,null,null);
	}

	public NetworkHFTP(int line,NetworkHFTPClient ftpClient,int code,LVValue value)
	{
		this(line,ftpClient,code,value,null);
	}

	public NetworkHFTP(int line,NetworkHFTPClient ftpClient,int code,LVValue value1,LVValue value2)
	{
		this.line=line;
		this.ftpClient=ftpClient;
		this.code=code;
		this.value1=value1;
		this.value2=value2;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		if (ftpClient!=null) ftpClient.doFTP(code,value1,value2);
		return pc+1;
	}
}

