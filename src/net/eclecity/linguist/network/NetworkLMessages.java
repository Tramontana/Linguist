//	NetworkLMessages.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network;

public class NetworkLMessages
{
	public static String socketExpected(String token)
	{
		return "'"+token+"' is not a socket variable.";
	}

	public static String connectionExpected(String name)
	{
		return "'"+name+"' is not an HTTP connection.";
	}

	public static String clientExpected(String name)
	{
		return "'"+name+"' is not a client.";
	}

	public static String serviceOrClientExpected(String name)
	{
		return "'"+name+"' is not a service or a client.";
	}

	public static String messageExpected(String name)
	{
		return "'"+name+"' is not a message.";
	}

	public static String badFTPCommand(String name)
	{
		return "Bad FTP command: '"+name+"'.";
	}
}
