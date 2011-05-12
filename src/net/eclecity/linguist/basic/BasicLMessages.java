//	BasicLMessages.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic;

public class BasicLMessages
{
	public static String numericNotAllowed(String token)
	{
		return "A numeric value is not allowed after '"+token+"'.";
	}

	public static String syntaxError()
	{
		return "Syntax error.";
	}

	public static String valueExpected(String token)
	{
		return "'"+token+"' is not a known value in this package.";
	}

	public static String fileExpected(String token)
	{
		return "'"+token+"' is not a file variable.";
	}

	public static String tableExpected(String token)
	{
		return "'"+token+"' is not a hashtable variable.";
	}

	public static String valueHolderExpected(String token)
	{
		return "'"+token+"' is not a value holder.";
	}

	public static String stringHolderExpected(String token)
	{
		return "'"+token+"' is not a string holder.";
	}

	public static String notArithmetic(String token)
	{
		return "'"+token+"' does not have an arithmetic value.";
	}

	public static String notAddableTo(String token)
	{
		return "'"+token+"' cannot be added to.";
	}

	public static String fileNotFound(String token)
	{
		return "File '"+token+"' cannot be found.";
	}

	public static String unknownVariableType(String token)
	{
		return "Variable '"+token+"' is of an unknown type.";
	}

	public static String unknownPackage(String token)
	{
		return "Package '"+token+"' cannot be found.";
	}
}
