// NetworkerService.java

package net.eclecity.linguist.networker;

import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Vector;

/******************************************************************************
	A service class.
*/
public abstract class NetworkerService extends NetworkerModule
{
	private Vector listeners;
	
	/***************************************************************************
		Default constructor.
	*/
	public NetworkerService()
	{
	}

	/***************************************************************************
		Constructor.
	*/
	public NetworkerService(String name,String address,String type)
	{
		super(name,address,type);
		listeners=new Vector();
	}

	/***************************************************************************
		Constructor.
	*/
	public NetworkerService(String name,String type) throws UnknownHostException
	{
		super(name,type);
		listeners=new Vector();
	}

	/***************************************************************************
		Add a new listener.
	*/
	public void addListener(NetworkerModule listener)
	{
		listeners.addElement(listener);
	}

	/***************************************************************************
		Remove a listener.
	*/
	public void removeListener(NetworkerModule listener)
	{
		Enumeration enumeration=listeners.elements();
		while (enumeration.hasMoreElements())
		{
			NetworkerModule module=(NetworkerModule)enumeration.nextElement();
			if (listener.equals(module)) listeners.removeElement(module);
		}
	}

	/***************************************************************************
		Return the list of listeners.
	*/
	public Vector getListeners() { return listeners; }

	/***************************************************************************
		Return an extra data item.
	*/
	public Object getData() { return null; }

	/************************************************************************
		Convert this service to a string.
		The format is {name}{type}
	*/
	public String toString()
	{
		StringBuffer sb=new StringBuffer();
		sb.append("{");
		sb.append(name);
		sb.append("}{");
		sb.append(type);
		sb.append("}");
		return sb.toString();
	}

	/***************************************************************************
		Someone is telling this module something.
	*/
	public abstract void told(NetworkerModule module,String message);

	/***************************************************************************
		Someone is telling this module something and wants an answer.
	*/
	public abstract String asked(NetworkerModule module,String message);
}

