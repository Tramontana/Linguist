// NetworkHConnection.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.Properties;

import net.eclecity.linguist.handler.LHData;
import net.eclecity.linguist.handler.LHReadOnlyValueHandler;
import net.eclecity.linguist.network.runtime.NetworkRMessages;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.util.LUHttpMessage;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	A network connection.
*/
public class NetworkHConnection extends LHReadOnlyValueHandler
{
	public NetworkHConnection() {}

	public Object newElement(Object extra) { return new NetworkHConnectionData(); }
	
	/***************************************************************************
		Set the URL of this connection.
	*/
	public void setURL(LVValue url) throws LRException
	{
		NetworkHConnectionData myData=(NetworkHConnectionData)getData();
		myData.url=url.getStringValue();
	}
	
	/***************************************************************************
		Set a property of this connection.
	*/
	public void setProperty(LVValue name,LVValue value) throws LRException
	{
		NetworkHConnectionData myData=(NetworkHConnectionData)getData();
		myData.properties.put(name.getStringValue(),value.getStringValue());
	}
	
	/***************************************************************************
		Set the mode of this connection.  Default is GET
	*/
	public void setMode(int mode) throws LRException
	{
		NetworkHConnectionData myData=(NetworkHConnectionData)getData();
		myData.mode=mode;
	}
	
	/***************************************************************************
		Send the message and get the response.
	*/
	public void post() throws LRException
	{
		NetworkHConnectionData myData=(NetworkHConnectionData)getData();
		getResponse(myData);
	}

	public String getResponse() throws LRException
	{
		NetworkHConnectionData myData=(NetworkHConnectionData)getData();
		return getResponse(myData);
	}

	public String getResponse(int n) throws LRException
	{
		NetworkHConnectionData myData=(NetworkHConnectionData)getData(n);
		return getResponse(myData);
	}

	private String getResponse(NetworkHConnectionData myData) throws LRException
	{
		LUHttpMessage msg=null;
		try { msg=new LUHttpMessage(myData.url); }
		catch (MalformedURLException e) { throw new LRException(NetworkRMessages.badURL(getName())); }
		try
		{
			InputStream is=null;
			if (myData.mode==POST) is=msg.sendPostMessage(myData.properties);
			else is=msg.sendGetMessage(myData.properties);
			BufferedReader result=new BufferedReader(new InputStreamReader(is));
			StringBuffer sb=new StringBuffer();
			int lines=0;
			while (true)
			{
				String s=result.readLine();
				if (s==null) break;
				if (lines>0) s+="\n";
				sb.append(s);
				lines++;
			}
			result.close();
			return sb.toString();
		}
		catch (IOException ignored) {}
		return "";
	}

	/***************************************************************************
		Unimplemented methods in LHReadOnlyValueHandler
	*/
	/***************************************************************************
		Get the current value of this item.
	*/
	public long getNumericValue() throws LRException
	{
		try {return Long.parseLong(getResponse()); }
		catch (NumberFormatException ignored) {}
		return 0;
	}
	public String getStringValue() throws LRException { return getResponse(); }

	/***************************************************************************
		Get a specific element of this item.
	*/
	public long getNumericValue(int n) throws LRException
	{
		try {return Long.parseLong(getResponse(n)); }
		catch (NumberFormatException ignored) {}
		return 0;
	}
	public String getStringValue(int n) throws LRException { return getResponse(n); }

	/***************************************************************************
		Return true if this is a numeric value holder.
	*/
	public boolean isNumeric() { return false; }
	
	public static final int
		GET=0,
		POST=1;
	
	/***************************************************************************
		A private class that holds data about an HTTP connection.
	*/
	class NetworkHConnectionData extends LHData
	{
		String url=null;
		Properties properties=new Properties();
		int mode=POST;
		
		NetworkHConnectionData() {}
	}
}
