//	DataLMessages.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.data;

public class DataLMessages
{
	public static String recordExpected(String name)
	{
		return "'"+name+"' is not a record variable.";
	}

	public static String specificationExpected(String name)
	{
		return "'"+name+"' is not a specification variable.";
	}

	public static String stringHolderExpected(String name)
	{
		return "'"+name+"' is not a string holder.";
	}

	public static String unknownFieldType(String name)
	{
		return "Type '"+name+"' is unknown.";
	}

	public static String requiredFieldsMissing(String name)
	{
		return "Fields missing: '"+name+"'.";
	}

	public static String alreadyGotField(String name)
	{
		return "I already have a field called '"+name+"'.";
	}

	public static String noIDField()
	{
		return "You haven't specified an ID field for this type of record.";
	}
}
