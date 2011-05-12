//	LRError.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.runtime;

public class LRException extends Exception
{
	private String message;

	public LRException(String message)
	{
		this.message=message;
	}

	public LRException(Exception e)
	{
		this.message=e.toString();
	}

	public String getMessage() { return message; }

///////////////////////////////////////////////////////////////////////////////

	public static String error()
	{
		return "Runtime error in module '";
	}

	public static String timeout(String name)
	{
		return "Unregistered script '"+name+"' timed out.";
	}

	public static String cantStartBackground(String name)
	{
		return "I can't start the background thread '"+name+"'.";
	}

	public static String backgroundNotStarted(String name)
	{
		return "Background '"+name+"' has not been started.";
	}

	public static String exportsDontMatchImports(String name1,String name2)
	{
		return "Exports from '"+name1+"' do not match imports to '"+name2+"'.";
	}

	public static String exportDoesNotMatchImport(String exName,String imName,String name)
	{
		return "Export '"+exName
					+"' does not match import '"+imName
					+"' to '"+name+"'.";
	}

	public static String cantRunScript(String name)
	{
		return "I can't run '"+name+"'.  File is missing/wrong version or a library can't be found.";
	}

	public static String indexOutOfRange(String name,int index)
	{
		return "Index (value = "+index+") out of range for variable '"+name+"'.";
	}

	public static String cantInstantiate(String className)
	{
		return "Error instantiating or accessing class '"+className+"'.";
	}

	public static String moduleNotFound(String name)
	{
		return "No module called '"+name+"' can be found.";
	}

	public static String moduleNotAssigned(String name)
	{
		return "Module variable '"+name+"' has not been assigned.";
	}

	public static String cantAssignAlias(String name)
	{
		return "I can't assign a value to alias '"+name+"'.";
	}

	public static String cantAccessAlias(String name)
	{
		return "I can't access the value of alias '"+name+"'.";
	}

	public static String incompatibleObjects()
	{
		return "The objects specified for this operation are not compatible.";
	}

	public static String stackUnderflow()
	{
		return "Stack underflow - probably a mismatched gosub-return.";
	}

	public static String notCreated(String name)
	{
		return "Variable '"+name+"' has not been created.";
	}

	public static String badURL(String name)
	{
		return "Bad URL in '"+name+"'.";
	}

	public static String unsupportedFunction(String name)
	{
		return "Function '"+name+"' is not supported.";
	}
}
