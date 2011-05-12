// NetworkHFTPClient.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.handler;

import net.eclecity.linguist.handler.LHData;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.network.runtime.NetworkRMessages;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPTransferType;


/******************************************************************************
	An FTP client variable.
	<pre>
	[1.001 GT]  22/04/03  New class.
	</pre>
*/
public class NetworkHFTPClient extends LHVariableHandler
{
	public NetworkHFTPClient() {}

	public Object newElement(Object extra) { return new Data(); }

	/***************************************************************************
		Tell callers this type doesn't return a value.
	*/
	public boolean hasValue() { return false; }

	/***************************************************************************
		Handle an FTP request.
	*/
	public void doFTP(int opcode) throws LRException
	{
		doFTP(opcode,(LVValue)null,(LVValue)null);
	}
	public void doFTP(int opcode,LVValue value1) throws LRException
	{
		doFTP(opcode,value1,(LVValue)null);
	}
	public void doFTP(int opcode,LVValue value1,LVValue value2) throws LRException
	{
		String s1="";
		if (value1!=null) s1=value1.getStringValue();
		String s2="";
		if (value2!=null) s2=value2.getStringValue();
		doFTP(opcode,s1,s2);
	}
	private void doFTP(int opcode,String value1,String value2) throws LRException
	{
//		println("FTP: Do code "+opcode+" "+value1+" "+value2);
		Data myData=(Data)getData();
		try
		{
			switch (opcode)
			{
				case CREATE:
					myData.create(value1);
					break;
				case LOGIN:
					myData.login(value1,value2);
					break;
				case CHDIR:
					myData.chdir(value1);
					break;
				case SET_TYPE:
					myData.setType(value1);
					break;
				case PUT_FILE:
					myData.put(value1,value2);
					break;
				case GET_FILE:
					myData.get(value1,value2);
					break;
				case QUIT:
					myData.quit();
					break;
			}
		}
		catch (Exception e)
		{
			if (myData.onError!=0) addQueue(myData.onError);
			else throw new LRException(NetworkRMessages.ftpError(opcode));
		}
	}
	
	public void onEvent(int opcode,int where) throws LRException
	{
		Data myData=(Data)getData();
		switch (opcode)
		{
			case ON_ERROR:
				myData.onError=where;
				break;
		}
	}

	public static final int
		CREATE=1,
		LOGIN=2,
		CHDIR=3,
		SET_TYPE=4,
		PUT_FILE=5,
		GET_FILE=6,
		QUIT=7,
		ON_ERROR=8;

	public static final void main(String[] args)
	{
		NetworkHFTPClient ftpClient=new NetworkHFTPClient();
		ftpClient.init();
		try
		{
			ftpClient.doFTP(CREATE,"tini1","");
			ftpClient.doFTP(LOGIN,"root","tini");
//			ftpClient.doFTP(CHDIR,"test","");
			ftpClient.doFTP(SET_TYPE,"binary","");
			ftpClient.doFTP(PUT_FILE,"lib/networker.jar","networker.zip");
			ftpClient.doFTP(QUIT,"","");
		}
		catch (Exception e) { e.printStackTrace(); }
	}

	/***************************************************************************
		An inner class that manages FTP.
	*/
	class Data extends LHData
	{
		FTPClient ftpClient=null;
		int onError;

		Data() {}

		void create(String host) throws Exception
		{
			ftpClient=new FTPClient(host);
		}

		void login(String name,String password) throws Exception
		{
			if (ftpClient==null) throw new Exception();
			ftpClient.login(name,password);
		}

		void chdir(String directory) throws Exception
		{
			if (ftpClient==null) throw new Exception();
			ftpClient.chdir(directory);
		}

		void setType(String type) throws Exception
		{
			if (ftpClient==null) throw new Exception();
			ftpClient.setType(type.equals("ascii")?FTPTransferType.ASCII:FTPTransferType.BINARY);
		}

		void put(String localPath,String fileName) throws Exception
		{
			if (ftpClient==null) throw new Exception();
			ftpClient.put(localPath,fileName);
		}

		void get(String localPath,String fileName) throws Exception
		{
			if (ftpClient==null) throw new Exception();
			ftpClient.get(localPath,fileName);
		}

		void quit() throws Exception
		{
			if (ftpClient==null) throw new Exception();
			ftpClient.quit();
			ftpClient=null;
		}
	}
}

