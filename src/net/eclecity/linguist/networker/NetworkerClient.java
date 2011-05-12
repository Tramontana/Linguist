// NetworkerClient.java

package net.eclecity.linguist.networker;

import java.net.UnknownHostException;

/******************************************************************************
	A client class.
*/
public abstract class NetworkerClient extends NetworkerModule
{
	private NetworkerService[] services=null;

	/***************************************************************************
		Default constructor.
	*/
	public NetworkerClient()
	{
	}

	/***************************************************************************
		Constructor.
	*/
	public NetworkerClient(String name,String address,String type)
	{
		super(name,address,type);
	}

	/***************************************************************************
		Constructor.
	*/
	public NetworkerClient(String name,String type) throws UnknownHostException
	{
		super(name,type);
	}

	/***************************************************************************
		This is called by Networker when new services are announced.
		We save the array of services and inform the client.
		Note that we send a notification even if there are no relevant services,
		since there may previously have been some that are no longer online.
		@param services an array of services (of a single type) supplied by Networker.
	*/
	public synchronized void notify(NetworkerService[] services)
	{
//		System.out.println(name+": "+services.length+" service(s) found");
//		for (int n=0; n<services.length; n++)
//			System.out.println("  "+services[n].getModuleAddress()
//				+" "+services[n].getModuleName()+" "
//				+services[n].getModuleType());
		this.services=services;
		newServices();
	}

	/***************************************************************************
		This is called by Networker when a watchdog multicast is received.
		The client must previously have registered itself with the networker
		using Networker.setWatchdogListener(this).
	*/
	public void triggerWatchdog()
	{
	}

	/***************************************************************************
		Return the list of services available.
	*/
	public NetworkerService[] getServices() { return services; }

	/***************************************************************************
		Tell the class that new services are available.
	*/
	public abstract void newServices();

	/***************************************************************************
		Someone is telling this module something.
	*/
	public abstract void told(NetworkerModule module,String message);

	/***************************************************************************
		Someone is telling this module something and wants an answer.
	*/
	public abstract String asked(NetworkerModule module,String message);
}
