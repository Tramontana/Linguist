// NetworkHAdvisor.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.handler;

import java.io.IOException;

import net.eclecity.linguist.networker.NetworkerService;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.util.LUEncoder;
import net.eclecity.linguist.value.LVValue;

/*******************************************************************************
 * An advisor variable.
 * 
 * <pre>
 * 
 *  [1.001 GT]  03/05/01  New class.
 *  
 * </pre>
 */
public class NetworkHAdvisor extends NetworkHClient implements
		NetworkHClient.ClientListener
{
	public NetworkHAdvisor()
	{}

	/****************************************************************************
	 * Create this advisor.
	 */
	public void create(LVValue name, LVValue type, boolean notifyOnce)
			throws LRException
	{
		super.create(name, type, true);
		Client client = ((ClientData) getData()).client;
		client.listener = this;
	}

	/****************************************************************************
	 * Implement the NotifyListener. This will be called when a relevant rules
	 * service has been discovered.
	 */
	public void notifyListener()
	{
		try
		{
			Client client = ((ClientData) getData()).client;
			NetworkerService[] services = getServices();
			if (services.length > 0)
			{
				//				println("Service "+services[0]);
				client.service = services[0]; // use the first one found
				client.doNotify();
			}
		}
		catch (LRException e)
		{}
	}

	/****************************************************************************
	 * Set a property of this advisor. Do this by sending the key and data as a
	 * specially formatted message to the attached rules service. The format of
	 * the message is "{key} {data}" where both {key} and {data} are URL-encoded
	 * strings.
	 */
	public void setProperty(LVValue key, LVValue data) throws LRException
	{
		Client client = ((ClientData) getData()).client;
		try
		{
			client.networker.tell(client, client.service, LUEncoder.encode(key
					.getStringValue(), "url")
					+ " " + LUEncoder.encode(data.getStringValue(), "url"));
		}
		catch (IOException e)
		{}
	}

	/****************************************************************************
	 * Ask this advisor a question.
	 */
	public String ask(LVValue question) throws LRException
	{
		Client client = ((ClientData) getData()).client;
		try
		{
			return client.networker.ask(client, client.service, question
					.getStringValue());
		}
		catch (IOException e)
		{
			return "";
		}
	}
}

