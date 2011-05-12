//	NetworkRMessages.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.runtime;

public class NetworkRMessages
{
	public static String asciiNumberExpected()
	{
		return "Numeric ASCII value expected.";
	}

	public static String socketException(String name)
	{
		return "Socket exception occurred in '"+name+"'.";
	}

	public static String cantOpenSocket(String name)
	{
		return "I can't open socket '"+name+"'.";
	}

	public static String cantWriteSocket(String name)
	{
		return "I can't write to socket '"+name+"'.";
	}

	public static String unknownHost(String host)
	{
		return "'"+host+"' is an unknown host.";
	}

	public static String noAddressGiven(String name)
	{
		return "No address has been given for '"+name+"'.";
	}

	public static String noPortGiven(String name)
	{
		return "No port has been given for '"+name+"'.";
	}

	public static String socketNotOpen(String name)
	{
		return "Socket '"+name+"' is not open.";
	}

	public static String cantCreateProcess(String name)
	{
		return "I can't create process '"+name+"'.";
	}

	public static String cantCreateNetworker()
	{
		return "I can't create the networker.";
	}

	public static String cantStartNetworker()
	{
		return "I can't start the networker.";
	}

	public static String cantAnnounceNetworker()
	{
		return "I can't announce the networker.";
	}

	public static String cantCreateService(String name)
	{
		return "I can't create service '"+name+"'.";
	}

	public static String cantCreateClient(String name)
	{
		return "I can't create client '"+name+"'.";
	}

	public static String cantAddService(String name)
	{
		return "I can't add service '"+name+"'.";
	}

	public static String cantAddClient(String name)
	{
		return "I can't add client '"+name+"'.";
	}

	public static String noDataAvailable(String name)
	{
		return "No more data is available from '"+name+"'.";
	}

	public static String incompatibleType(String name)
	{
		return "Retrieved data is incompatible with '"+name+"'.";
	}

	public static String badContext()
	{
		return "Command context is invalid.";
	}

	public static String badURL(String name)
	{
		return "Bad URL in '"+name+"'.";
	}

	public static String nullSource(String name)
	{
		return "Source module not defined in '"+name+"'.";
	}

	public static String nullDestination(String name)
	{
		return "Destination module not defined in '"+name+"'.";
	}

	public static String unsupportedEncoding(String name)
	{
		return "Unsupported encoding '"+name+"'.";
	}

	public static String ftpError(int code)
	{
		return "Unable to perform FTP request (code="+code+").";
	}
}
