//	ServletRMessages.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.servlet.runtime;

public class ServletRMessages
{
	public static String cantWrite()
	{
		return "I can't write the output stream.";
	}

	public static String unsupportedEncoding(String name)
	{
		return "Unsupported encoding '"+name+"'.";
	}
}
