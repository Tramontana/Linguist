// NetworkerModule.java

package net.eclecity.linguist.networker;

import java.net.InetAddress;
import java.net.UnknownHostException;

/******************************************************************************
	A module.
*/
public abstract class NetworkerModule
{
	protected String name;
	protected String address;
	protected String type;

	/***************************************************************************
		Default constructor.
	*/
	public NetworkerModule()
	{
	}

	/***************************************************************************
		Constructor.
	*/
	public NetworkerModule(String name,String type) throws UnknownHostException
	{
		this(name,InetAddress.getLocalHost().getHostAddress(),type);
	}

	/***************************************************************************
		Constructor.
	*/
	public NetworkerModule(String name,String address,String type)
	{
		this.name=name;
		this.address=address;
		this.type=type;
	}

	/***************************************************************************
		Return the name by which this module can be identified.
	*/
	public String getModuleName() { return name; }

	/***************************************************************************
		Return the IP address of the machine this module is running on.
	*/
	public String getModuleAddress() { return address; }

	/***************************************************************************
		Return the type of this module.
		For a service, this is the type that is announced.
		For a client, it's the type we wish to be notified about.
	*/
	public String getModuleType() { return type; }

	/***************************************************************************
		Compare this module with another.
	*/
	public boolean equals(NetworkerModule module)
	{
		if (module==null) return false;
		return (address.equals(module.getModuleAddress()) && name.equals(module.getModuleName()));
	}

	/***************************************************************************
		Someone is telling this module something.
	*/
	public void told(NetworkerModule module,String message) {}

	/***************************************************************************
		Someone is telling this module something and wants an answer.
	*/
	public abstract String asked(NetworkerModule module,String message);
}
