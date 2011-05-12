// LUUtil.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.util;


/******************************************************************************
	A utility class.
*/
public class LUUtil
{
	public static final int getInt(String s)
	{
		return getInt(s,0);
	}

	public static final int getInt(String s,int def)
	{
		try
		{
			return Integer.parseInt(s);
		}
		catch (NumberFormatException e) {}
		return def;
	}

	public static final long getLong(String s)
	{
		return getLong(s,0);
	}

	public static final long getLong(String s,int def)
	{
		try
		{
			return Long.parseLong(s);
		}
		catch (NumberFormatException e) {}
		return def;
	}

	public static final String toString(byte[] data)
	{
		StringBuffer sb=new StringBuffer();
		for (int n=0; n<data.length; n++)
		{
			int c=((data[n])>>4)&0x0f;
			if (c>9) c+=7;
			c+='0';
			sb.append(c);
			c=(data[n])&0x0f;
			if (c>9) c+=7;
			c+='0';
			sb.append(c);
		}
		return sb.toString();
	}
}

