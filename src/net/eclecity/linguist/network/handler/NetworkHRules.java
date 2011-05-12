// NetworkHRules.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.handler;

import java.util.Properties;

import net.eclecity.linguist.network.runtime.NetworkRMessages;
import net.eclecity.linguist.networker.NetworkerModule;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.util.LUEncoder;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	A rules variable.
	<pre>
	[1.001 GT]  17/05/01  New class.
	</pre>
*/
public class NetworkHRules extends NetworkHService implements NetworkHService.ServiceListener
{
	public NetworkHRules() {}

	/***************************************************************************
		Create this ruleset.
	*/
	public void create(LVValue name,LVValue type) throws LRException
	{
		super.create(name,type);
		Service service=((ServiceData)getData()).service;
		service.listener=this;
		service.data=new Properties();
	}

	/***************************************************************************
		Get a property from this ruleset.
	*/
	public String getProperty(LVValue key) throws LRException
	{
		Service service=((ServiceData)getData()).service;
		Properties props=(Properties)service.data;
		String s=props.getProperty(key.getStringValue());
		try
		{
			return LUEncoder.decode(s,"url");
		}
		catch (Exception e)
		{
			throw new LRException(NetworkRMessages.unsupportedEncoding(s));
		}
	}

	/***************************************************************************
		Here when a message is sent by a client.
		The message is assumed to be a name/value pair to go into the
		property list.
	*/
	public void told(NetworkerModule module,String message)
	{
		try
		{
			Service service=((ServiceData)getData()).service;
			Properties props=(Properties)service.data;
			int n=message.indexOf(' ');
			if (n>0)
			{
				props.setProperty(new String(message.substring(0,n)),
					new String(message.substring(n+1)));
			}
		}
		catch (Exception e) {}
	}

	/***************************************************************************
		Here when a message requiring an answer is sent by a client.
	*/
	public String asked(NetworkerModule module,String message)
	{
		try
		{
			Service service=((ServiceData)getData()).service;
			if (message.equals("name")) return service.getModuleName();
			return service.doAsked(module,message);
		}
		catch (Exception e) {}
		return "";
	}
}

