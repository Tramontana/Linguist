//	ServletLMessages.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.servlet;

public class ServletLMessages
{
	public static String elementExpected(String name)
	{
		return "'"+name+" is not an element.";
	}

	public static String cookieExpected(String name)
	{
		return "'"+name+"' is not a cookie.";
	}

	public static String rowExpected(String name)
	{
		return "'"+name+" is not a row.";
	}

	public static String parametersExpected(String name)
	{
		return "The value starting '"+name+"' is not a parameter table.";
	}
}
