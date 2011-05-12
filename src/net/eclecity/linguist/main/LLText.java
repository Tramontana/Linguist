//	LLText.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.main;

/******************************************************************************
	Strings that may need to change if a language
	other than English is wanted.
*/
public class LLText extends Exception
{
	public static String packageString()
	{
		return "package";
	}

	public static String compileString()
	{
		return "compile";
	}

	public static String includeString()
	{
		return "include";
	}

	public static String beginString()
	{
		return "begin";
	}

	public static String endString()
	{
		return "end";
	}

	public static String catString()
	{
		return "cat";
	}

	public static String warnings()
	{
		return "Warnings:";
	}

	public static String syntax()
	{
		return "Syntax:\n"
			+"   java net.eclecity.linguist.LS <script> <options>\n"
			+"Options:\n"
			+"-o           Compile to binary\n"
			+"-r           Compile and run\n"
			+"-a<string>   Specify a command-line argument\n"
			+"-p<package>  Specify a package.  If used, all must be specified.\n"
			+"\n"
			+"If no options are given the script will be compiled but not run.\n"
			+"If <script> ends in 'lrun' it will be loaded and run without compiling.\n";
	}
}
