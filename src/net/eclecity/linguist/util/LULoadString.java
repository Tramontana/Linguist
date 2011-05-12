// LULoadString.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/******************************************************************************
	A utility class that loads a string from a file.
*/
public class LULoadString
{
	public LULoadString() {}

	public String load(String name)
	{
		URL url;
		File file=new File(name);
		if (file!=null)
		{
			try
			{
				BufferedReader in;
				if (name.startsWith("http://"))
				{
					url=new URL(name);
					InputStreamReader is=new InputStreamReader(url.openStream());
					in=new BufferedReader(is);
				}
				else
				{
					try { in=new BufferedReader(new FileReader(file)); }
					catch (IOException e)
					{
						// try loading as a resource
						url=LULoadString.class.getResource("/"+name);
						if (url==null) return "";
						InputStreamReader is=new InputStreamReader(url.openStream());
						in=new BufferedReader(is);
					}
				}
				StringBuffer sb=new StringBuffer("");
				String s1;
				while ((s1=in.readLine())!=null)
				{
					if (sb.length()>0) sb.append("\n");
					sb.append(s1);
				}
				in.close();
				return sb.toString();
			}
			catch (IOException e) {}
		}
		return "";
	}
}

