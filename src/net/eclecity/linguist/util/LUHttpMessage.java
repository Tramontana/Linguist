// LUHttpMessage.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Properties;

import net.eclecity.linguist.handler.LHData;


/***************************************************************************
	A class that manages an HTTP message.
*/
public class LUHttpMessage extends LHData
{
	URL url=null;

	public LUHttpMessage(String urlString) throws MalformedURLException
	{
		url=new URL(urlString);
	}

	public InputStream sendGetMessage(Properties props) throws IOException
	{
		if (props.size()>0) url=new URL(url.toExternalForm()+"?"+toEncodedString(props));
		URLConnection con=url.openConnection();
		con.setUseCaches(false);
		return con.getInputStream();
	}

	public InputStream sendPostMessage(Properties props) throws IOException
	{
		URLConnection con=url.openConnection();
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);
		con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		DataOutputStream out=new DataOutputStream(con.getOutputStream());
		out.writeBytes(toEncodedString(props));
		out.flush();
		out.close();
		return con.getInputStream();
	}

	public String toEncodedString(Properties props) throws UnsupportedEncodingException
	{
		StringBuffer buf=new StringBuffer();
		Enumeration names=props.propertyNames();
		while (names.hasMoreElements())
		{
			String name=(String)names.nextElement();
			String value=props.getProperty(name);
			buf.append(URLEncoder.encode(name,"UTF-8")+"="+URLEncoder.encode(value,"UTF-8"));
			if (names.hasMoreElements()) buf.append("&");
		}
		return buf.toString();
	}
}
