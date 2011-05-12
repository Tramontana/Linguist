// LUErrors.java

package net.eclecity.linguist.util;

public class LUErrors
{
	public static String cantEncrypt()
	{
		return "Can't encrypt this item.";
	}

	public static String cantDecrypt()
	{
		return "Can't decrypt this item.";
	}

	public static String badEncodingMethod(String method)
	{
		return method+" is not a valid encoding method.";
	}
}
