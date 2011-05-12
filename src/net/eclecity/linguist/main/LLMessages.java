//	LLMessages.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.main;

public class LLMessages
{
	public static String aborted()
	{
		return "Compilation aborted.";
	}

	public static String scriptNotFound(String name)
	{
		return "Script file '"+name+"' (or an included file) could not be found.";
	}

	public static String compilationComplete(String name)
	{
		return "Compilation of "+name+" done.\nNo error(s) detected.";
	}

	public static String outputWritten(String name,String extension)
	{
		return "Output file '"+name+extension+"' written.";
	}

	public static String unhandledKeyword(String token)
	{
		return "I can't process the command starting with '"+token+"'.";
	}

	public static String unhandledValue(String token)
	{
		return "I can't process the value starting with '"+token+"'.";
	}

	public static String unhandledPair(String token)
	{
		return "I can't process the pair of values starting with '"+token+"'.";
	}

	public static String unhandledCondition(String token)
	{
		return "I can't process the condition starting with '"+token+"'.";
	}

	public static String unhandledVariable(String token)
	{
		return "Variable '"+token+"' is not handled by any package.";
	}

	public static String cantInstantiate(String className)
	{
		return "Error instantiating or accessing class '"+className+"'.";
	}

	public static String nameAlreadyUsed(String name)
	{
		return "Name '"+name+"' has already been used.";
	}

	public static String dontUnderstand(String token)
	{
		return "I don't understand '"+token+"'.";
	}

	public static String inappropriateType(String token)
	{
		return "Variable '"+token+"' is an inappropriate type.";
	}

	public static String constantExpected(String token)
	{
		return "'"+token+"' is not a constant.";
	}

	public static String constantNotNumeric(String token)
	{
		return "'"+token+"' is not a numeric constant.";
	}

	public static String undefinedSymbol(String token)
	{
		return "Undefined Symbol '"+token+"'";
	}

	public static String noSummary()
	{
		return "No summary available.";
	}

	public static String badCharacter(String token)
	{
		return "Bad character in '"+token+"'.";
	}

	public static String unhandledType(String name)
	{
		return "Variable '"+name+"' is not a type handled here.";
	}

	public static String notHandled(String token)
	{
		return "Cannot process '"+token+"'.";
	}

	public static String variableExpected(String token)
	{
		return "'"+token+"' is not a variable.";
	}

	public static String moduleExpected(String token)
	{
		return "'"+token+"' is not a module identifier.";
	}

	public static String notLabel(String token)
	{
		return "'"+token+"' is not a label.";
	}

	public static String unknownVariable(String token)
	{
		return "'"+token+"' has not been declared.";
	}

	public static String noRuntimeHandler(String className)
	{
		return "Can't find a handler for '"+className+"'.";
	}

	public static String switchNotNumeric()
	{
		return "Switch value must be numeric.";
	}

	public static String numericNotAllowed(String token)
	{
		return "A numeric value is not allowed after '"+token+"'.";
	}

	public static String syntaxError()
	{
		return "Syntax error.";
	}

	public static String setModuleWhat()
	{
		return "Set module what?";
	}

	public static String setPortWhat()
	{
		return "Set port what?";
	}

	public static String unsupportedPortConfig()
	{
		return "Unsupported port configuration";
	}

	public static String noValue(String name)
	{
		return "Variable '"+name+"' does not return a value.";
	}
}
