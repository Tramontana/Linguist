//	BasicRMessages.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.runtime;

public class BasicRMessages
{
	public static String asciiNumberExpected()
	{
		return "Numeric ASCII value expected.";
	}

	public static String cantOpenFile(String name)
	{
		return "I can't open file '"+name+"'.";
	}

	public static String cantCreateFile(String name)
	{
		return "I can't create file '"+name+"'.";
	}

	public static String cantDeleteFile(String name)
	{
		return "I can't delete file '"+name+"'.";
	}

	public static String cantCopyFile(String name1,String name2)
	{
		return "I can't copy file '"+name1+"' to '"+name2+"'.";
	}

	public static String cantRenameFile(String name1,String name2)
	{
		return "I can't rename file '"+name1+"' to '"+name2+"'.";
	}

	public static String fileDoesNotExist(String name)
	{
		return "File '"+name+"' does not exist.";
	}

	public static String fileNotOpen(String name)
	{
		return "File '"+name+"' is not open.";
	}

	public static String cantReadFile(String name)
	{
		return "I can't read from file '"+name+"'.";
	}

	public static String cantWriteFile(String name)
	{
		return "I can't write to file '"+name+"'.";
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

	public static String cantEncodeHashtable()
	{
		return "I can't encode a hashtable.";
	}

	public static String cantLoadHashtable()
	{
		return "I can't load a hashtable.";
	}

	public static String cantSaveHashtable()
	{
		return "I can't save a hashtable.";
	}

	public static String cantDecodeHashtable()
	{
		return "I can't decode a hashtable.";
	}

	public static String noDataAvailable(String name)
	{
		return "No more data is available from '"+name+"'.";
	}

	public static String incompatibleType(String name)
	{
		return "Retrieved data is incompatible with '"+name+"'.";
	}

	public static String badURL(String name)
	{
		return "Bad URL in '"+name+"'.";
	}

	public static String unsupportedEncoding(String name)
	{
		return "Unsupported encoding '"+name+"'.";
	}
}
