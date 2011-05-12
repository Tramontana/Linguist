//	DataRMessages.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.data.runtime;

public class DataRMessages
{
	public static String nullRecord(String name)
	{
		return "Record '"+name+"' has not been created.";
	}

	public static String hasNoSpec(String name)
	{
		return "Record '"+name+"' has no specification.";
	}
}
