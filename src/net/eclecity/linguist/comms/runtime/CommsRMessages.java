//	CommsRMessages.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.comms.runtime;

public class CommsRMessages
{
	public static String portInUse(String name)
	{
		return "Serial port '"+name+"' is in use.";
	}

	public static String cantGetInputStream(String name)
	{
		return "Can't get an input stream for port '"+name+"'.";
	}

	public static String cantGetOutputStream(String name)
	{
		return "Can't get an output stream for port '"+name+"'.";
	}

	public static String cantOpenPort(String name)
	{
		return "Can't open port '"+name+"'. (Is the DLL missing?)";
	}

	public static String portNotOpen(String name)
	{
		return "Port '"+name+"' is not open.";
	}

	public static String unsupportedCommOperation(String name)
	{
		return "The requested operation is not valid for port '"+name+"'.";
	}

	public static String tooManyListenersException(String name)
	{
		return "Too many listeners requested for port '"+name+"'.";
	}

	public static String cantReadPort(String name)
	{
		return "I can't read from port '"+name+"'.";
	}

	public static String cantWritePort(String name)
	{
		return "I can't write to port '"+name+"'.";
	}
}
