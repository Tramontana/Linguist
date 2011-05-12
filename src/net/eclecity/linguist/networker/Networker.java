// Networker.java

package net.eclecity.linguist.networker;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

/*******************************************************************************
 * A service/client networking framework.
 * <p>
 * This class provides the infrastructure for a networked system comprising any
 * number of nodes (computers or simpler networked devices). Each node can be a
 * provider and/or consumer of 'services', as described below.
 * <p>
 * The system is intended for situations where small to moderate amounts of
 * network traffic are expected. It is not designed to be optimal where a large
 * throughput is needed. (Such a situation often implies that the data concerned
 * is in the wrong place to start with, and you should consider moving the
 * processing closer to its data.)
 * <p>
 * A service is a program or subprogram that performs a task on behalf of
 * another program. An example might be a time server; the service is the
 * provision of a time value. One or more clients can request the time whenever
 * they need it, or alternatively the service can be requested to send the time
 * at predetermined intervals (such as when it changes).
 * <p>
 * The Networker manages all network communications on behalf of the services
 * and clients running on its node. When it starts up it uses multicasting to
 * announce itself to the network; each other Networker running at that point
 * will respond by sending it a list of the services it offers, plus a request
 * for the recipient to send its own list back. In this way, all nodes
 * 'discover' each other as they start up and gain a complete list of all
 * services on offer.
 * <p>
 * A service is represented by the {@link NetworkerService}class.
 * <p>
 * To use a Networker, first create a new instance using its default constructor
 * or passing port numbers and addresses to substitute for the defaults (which
 * in most cases will not need to be changed). Next, call start() to start it
 * up. Now add all the services that will be provided on this node, and finally
 * call announce() to send the initial multicast message that allows other nodes
 * to discover this one. An example program is shipped with this package.
 */
public class Networker
{
	private ServerSocket serverSocket; // the socket for incoming messages
	private MulticastSocket msocket; // the multicast socket
	private InetAddress multicastGroup; // the group address
	private int serverPort; // the port used for regular messages
	private int multicastPort; // the port used for multicast messages
	private String multicastAddress; // the group address
	private String myAddress; // the IP address of this computer
	private String myName; // the name of this computer
	private String timestamp; // the timestamp for this computer
	private Vector serverQueue; //	a queue for incoming regular messages
	private Thread serverRunner; // a thread that manages the server queue
	private Hashtable nodeNames; // a table of discovered nodes, by name
	private Hashtable nodeAddresses; // a table of discovered nodes, by address
	private ServiceList allServices; // a list of all services
	private ServiceList myServices; // the list of my services
	private Hashtable localModules; // the modules running on this computer
	private Hashtable pendingMessages; // a table of pending 'ask' messages
	private Hashtable pingTable; // a table of currently known systems
	private NetworkerClient watchdogListener; // the client that will handle
	// watchdog messages

	private static final String PING = "!!";
	private static final String WATCHDOG = "**";
	private static Networker networker;

	/****************************************************************************
	 * Static factory method to return the one and only instance.
	 */
	public static Networker getInstance() throws IOException,
			UnknownHostException
	{
		if (networker == null) networker = new Networker();
		return networker;
	}

	/****************************************************************************
	 * Constructor. Initialize defaults.
	 */
	private Networker() throws IOException, UnknownHostException
	{
		// Define a default port to use.
		serverPort = 17348;
		multicastPort = 17348;
		// Define the default multicast group address.
		multicastAddress = "230.17.3.48";
		// Get my name.
		myName = InetAddress.getLocalHost().getHostName();
		// Get my IP address.
		myAddress = InetAddress.getLocalHost().getHostAddress();
		timestamp = "" + new File(System.getProperty("java.home")).lastModified();
		// Create tables to keep a list of all discovered nodes.
		nodeNames = new Hashtable();
		nodeAddresses = new Hashtable();
		// Create an empty service list.
		myServices = new ServiceList();
		// Create an empty service table.
		allServices = new ServiceList();
		// Create the local modules table.
		localModules = new Hashtable();
		// Create the table of pending 'ask' messages
		pendingMessages = new Hashtable();
		// Creat the table of known systems
		pingTable = new Hashtable();
		startNetworker();
	}

	/****************************************************************************
	 * Get my address.
	 */
	public String getMyAddress()
	{
		return myAddress;
	}

	/****************************************************************************
	 * Get my list of modules.
	 */
	public Hashtable getModules()
	{
		return localModules;
	}

	/****************************************************************************
	 * Start the networker. Set up the server socket ready for incoming messages
	 * and prepare the multicast receiver.
	 */
	private void startNetworker() throws IOException, UnknownHostException
	{
		// Set up and manage the queue of incoming server messages.
		serverQueue = new Vector();
		serverRunner = new Thread(new Runnable()
		{
			public void run()
			{
				while (true)
				{
					while (!serverQueue.isEmpty())
					{
						Packet packet = (Packet) serverQueue.elementAt(0);
						String address = packet.address;
						String message = packet.message;
						//						System.out.println("Received message: "+message+" from
						// "+address);
						switch (message.charAt(0))
						{
							case '?': // requesting my service list
								try
								{
									sendMessage(address, "=" + myServices.toString());
								}
								catch (IOException e)
								{}
							case '=':
								// Add these services to my table
								addServices(address, message.substring(1));
								break;
							default:
								// A message from a one module to another.
								// The first item, enclosed in { }, is the unique
								// ID of the message. If this is zero it's a 'tell'
								// message, otherwise it's an 'ask'.
								// The name of the sender follows, also enclosed in { },
								// then that of the recipient, also enclosed in { },
								// then the message.
								//								System.out.println("Received message: "+message+"
								// from "+address);
								int n = 0;
								if (message.charAt(n++) == '{')
								{
									int m = message.indexOf('}', n);
									if (m > n)
									{
										long id = Long.parseLong(message.substring(n, m));
										n = m + 1;
										if (message.charAt(n++) == '{')
										{
											m = message.indexOf('}', n);
											if (m > n)
											{
												String senderName = message.substring(n, m);
												n = m + 1;
												if (message.charAt(n++) == '{')
												{
													m = message.indexOf('}', n);
													if (m > n)
													{
														String recipientName = message
																.substring(n, m);
														message = message.substring(m + 1);
														try
														{
															handleMessage(id, senderName,
																	address, recipientName,
																	message);
														}
														catch (IOException e)
														{
															System.out.println(e.getMessage());
														}
													}
												}
											}
										}
									}
								}
								break;
						}
						serverQueue.removeElementAt(0);
					}
					try
					{
						Thread.sleep(10000);
					}
					catch (InterruptedException e)
					{}
				}
			}
		});
		serverRunner.start();

		// Open a server socket and wait for incoming connections.
		// When a packet is received, put it into the server queue
		// so we are immediately ready for the next packet.
		// Note: There is no guard against queue overflow; the system
		// is not designed for very heavy traffic.
		serverSocket = new ServerSocket(serverPort);
		new Thread(new Runnable()
		{
			public void run()
			{
				while (true)
				{
					try
					{
						Socket socket = serverSocket.accept();
						socket.setSoTimeout(2000);
						socket.setTcpNoDelay(true);
						DataInputStream dis = new DataInputStream(socket
								.getInputStream());
						serverQueue.addElement(new Packet(socket.getInetAddress()
								.getHostAddress(), dis.readUTF()));
						serverRunner.interrupt();
						socket.close();
					}
					catch (SocketException se)
					{}
					catch (IOException ioe)
					{}
				}
			}
		}).start();

		// Prepare the multicast receiver.
		// When a multicast is received, send my service list with a request for
		// the node to return its own.
		multicastGroup = InetAddress.getByName(multicastAddress);
		msocket = new MulticastSocket(multicastPort);
		msocket.joinGroup(multicastGroup);
		System.out.println("Multicasting started.");
		new Thread(new Runnable()
		{
			public void run()
			{
				while (true)
				{
					try
					{
						byte[] buf = new byte[80];
						DatagramPacket packet = new DatagramPacket(buf, buf.length);
						msocket.receive(packet);
						StringTokenizer st = new StringTokenizer(new String(packet
								.getData()), " ");
						String name = st.nextToken();
						String ts = st.nextToken();
						InetAddress inadd = packet.getAddress();
						if (inadd != null)
						{
							String address = inadd.getHostAddress();
							if (ts.equals(timestamp)) myAddress = address;
							else
							{
								if (name.equals(PING)) // a ping transmission
								{
									// Update the ping value for this address
									pingTable.put(address, new Integer(3));
								}
								else if (name.equals(WATCHDOG)) // a watchdog
								// transmission
								{
									triggerWatchdog();
								}
								else
								{
									// Save this node by name and by address
									nodeNames.put(name, address);
									nodeAddresses.put(address, name);
									sendMessage(address, "?" + myServices.toString());
								}
							}
						}
					}
					catch (IOException e)
					{
						System.out.println("Send message fail: " + e.getMessage());
					}
				}
			}
		}).start();
		// Run a background thread that sends a multicast ping occasionally.
		new Thread(new Runnable()
		{
			public void run()
			{
				while (true)
				{
					try
					{
						Thread.sleep(1000 * 10);
					}
					catch (InterruptedException e)
					{}
					try
					{
						sendPingMulticast();
					}
					catch (Exception e)
					{}
				}
			}
		}).start();
		// Run another background thread that monitors the list of known
		// computers that have responded to multicasts. If any has not
		// pinged recently, remove it.
		new Thread(new Runnable()
		{
			public void run()
			{
				while (true)
				{
					try
					{
						Thread.sleep(1000 * 30);
					}
					catch (InterruptedException e)
					{}
					Vector v = new Vector();
					Enumeration enumeration = pingTable.keys();
					while (enumeration.hasMoreElements())
					{
						String address = (String) enumeration.nextElement();
						int value = ((Integer) pingTable.get(address)).intValue();
						if (--value <= 0) v.addElement(address);
						else pingTable.put(address, new Integer(value));
					}
					enumeration = v.elements();
					// Remove any addresses that have timed out
					while (enumeration.hasMoreElements())
					{
						deactivate((String) enumeration.nextElement());
					}
				}
			}
		}).start();
	}

	/****************************************************************************
	 * Tell the caller if a specified address is in the ping table (i.e, the
	 * corresponding machine is active).
	 */
	public boolean isActive(String address)
	{
		if (address.equals(myAddress)) return true;
		return pingTable.containsKey(address);
	}

	/****************************************************************************
	 * Deactivate an address.
	 */
	public void deactivate(String address)
	{
		System.out.println("Removing inactive address: " + address);
		pingTable.remove(address);
		// Delete all existing services from this address.
		ServiceList sl = new ServiceList();
		synchronized (allServices)
		{
			Enumeration enum2 = allServices.elements();
			while (enum2.hasMoreElements())
			{
				NetworkerService service = (NetworkerService) enum2.nextElement();
				if (!service.getModuleAddress().equals(address)) sl.add(service);
			}
			allServices = sl;
		}
	}

	/****************************************************************************
	 * Set the name to be used by this Networker.
	 */
	public void setName(String name)
	{
		myName = name;
	}

	/****************************************************************************
	 * Send a multicast message announcing myself.
	 */
	public void announce() throws IOException
	{
		synchronized (allServices)
		{
			allServices.removeAllElements();
			Enumeration enumeration = myServices.elements();
			while (enumeration.hasMoreElements())
				allServices.addElement(enumeration.nextElement());
		}
		// System.out.println(s+"announce()");
		sendMulticast(myName);
	}

	/****************************************************************************
	 * Send a ping multicast message.
	 */
	private void sendPingMulticast() throws IOException
	{
		sendMulticast(PING);
	}

	/****************************************************************************
	 * Send a watchdog multicast message.
	 */
	public void sendWatchdogMulticast() throws IOException
	{
		sendMulticast(WATCHDOG);
	}

	/****************************************************************************
	 * Send a multicast message. Append the system timestamp. This is needed for
	 * systems that have more than one IP address to recognise their own
	 * messages. Put a space on the end to force a clean termination on receive.
	 */
	private synchronized void sendMulticast(String s) throws IOException
	{
		s += (" " + timestamp + " ");
		DatagramSocket ds = new DatagramSocket();
		ds.send(new DatagramPacket(s.getBytes(), s.length(), multicastGroup,
				multicastPort));
		ds.close();
	}

	/****************************************************************************
	 * Send a message to an IP address.
	 */
	private void sendMessage(String address, String message) throws IOException
	{
		sendMessage(address, message, 2000);
	}

	private synchronized void sendMessage(String address, String message,
			int timeout) throws IOException
	{
		//		System.out.println("Networker: sending "+message+" to "+address);
		Socket socket = new Socket(address, serverPort);
		socket.setSoTimeout(timeout);
		socket.setTcpNoDelay(true);
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		out.writeUTF(message);
		out.close();
		socket.close();
	}

	/****************************************************************************
	 * Send a message from one module to another and don't wait for a reply. The
	 * message is handled specially if it's going to a module on the same
	 * computer as the sender.
	 * 
	 * @param sender the sender of the message.
	 * @param
	 * @recipient the recipient of the message.
	 * @param message the message text.
	 */
	public void tell(NetworkerModule sender, NetworkerModule recipient,
			String message) throws IOException
	{
		tell(sender, recipient, message, 2000, 10);
	}

	public void tell(NetworkerModule sender, NetworkerModule recipient,
			String message, int timeout, int retries) throws IOException
	{
		if (sender == null || recipient == null || message == null) return;
		boolean retry = false;
		int errorCount = 0;
		while (true)
		{
			try
			{
				if (recipient.getModuleAddress().equals(getMyAddress()))
				{
					//					System.out.println("Networker: Send '"+message+"' to local
					// module");
					handleMessage(0, sender.getModuleName(), sender
							.getModuleAddress(), recipient.getModuleName(), message);
				}
				else
				{
					//					System.out.println("Networker: Send '"+message+"' to remote
					// module");
					sendMessage(recipient.getModuleAddress(), "{0}{"
							+ sender.getModuleName() + "}{"
							+ recipient.getModuleName() + "}" + message, timeout);
				}
				break;
			}
			catch (IOException e)
			{
				if (++errorCount == retries)
				{
					System.out.println("Can't send '" + message + "' ("
							+ e.getMessage() + ")");
					throw e;
				}
			}
			retry = true;
		}
		if (retry) System.out.println("Networker: Message sent to "
				+ sender.getModuleName() + " on attempt " + (errorCount + 1) + ".");
	}

	/****************************************************************************
	 * Send a message from one module to another and wait for a reply. The
	 * message is handled specially if it's going to a module on the same
	 * computer as the sender.
	 * 
	 * @param sender the sender of the message.
	 * @param recipient the recipient of the message.
	 * @param message the message text.
	 * @return the reply
	 */
	public String ask(NetworkerModule sender, NetworkerModule recipient,
			String message) throws IOException
	{
		return ask(sender, recipient, message, 2000);
	}

	/****************************************************************************
	 * Send a message from one module to another and wait for a reply. The
	 * message is handled specially if it's going to a module on the same
	 * computer as the sender.
	 * 
	 * @param sender the sender of the message.
	 * @param recipient the recipient of the message.
	 * @param message the message text.
	 * @param timeout the timeout in seconds before giving up.
	 * @return the reply
	 */
	public String ask(NetworkerModule sender, NetworkerModule recipient,
			String message, int timeout) throws IOException
	{
		if (recipient == null) throw new IOException("No recipient defined.");
		if (recipient.getModuleAddress().equals(getMyAddress()))
		{
			NetworkerModule module = (NetworkerModule) localModules.get(recipient
					.getModuleName());
			if (module != null) { return module.asked(sender, message); }
			return null;
		}
		int errorCount = 0;
		while (true)
		{
			try
			{
				// Generate a unique ID for this message.
				String id = "" + System.currentTimeMillis();
				while (pendingMessages.containsKey(id))
				{
					try
					{
						Thread.sleep(1);
					}
					catch (InterruptedException e)
					{}
					id = "" + System.currentTimeMillis();
				}
				Asker asker = new Asker(timeout);
				pendingMessages.put(id, asker);
				//			System.out.println(sender.getModuleName()+": Sending message
				// id="+id+": "+message);
				sendMessage(recipient.getModuleAddress(), "{" + id + "}{"
						+ sender.getModuleName() + "}{" + recipient.getModuleName()
						+ "}" + message);
				String reply = asker.ask(id);
				pendingMessages.remove(id);
				//			System.out.println("Message id="+id+" removed");
				return reply;
			}
			catch (Exception e)
			{
				if (++errorCount == 10) throw new IOException(e.getMessage());
			}
		}
	}

	/****************************************************************************
	 * Trigger the watchdog module on this computer.
	 */
	private void triggerWatchdog()
	{
		if (watchdogListener != null) watchdogListener.triggerWatchdog();
	}

	/****************************************************************************
	 * Register a client as the watchdog listener.
	 */
	public void setWatchdogListener(NetworkerClient client)
	{
		watchdogListener = client;
	}

	/****************************************************************************
	 * An inner class to implement the wait mechanism needed to wait for a reply
	 * from a module.
	 */
	class Asker
	{
		int timeout;

		Asker(int timeout)
		{
			this.timeout = timeout;
		}

		/*************************************************************************
		 * Wait for a reply from a remote module. Note: This method blocks the
		 * sending thread until a reply is received or until a timeout (default 2
		 * seconds) occurs.
		 * 
		 * @param id a unique id for the message.
		 * @return the reply
		 */
		synchronized String ask(String id) throws IOException
		{
			// If no reply comes throw an IOException.
			// Wait for up to the timeout to see if a reply came.
			// Objects are indexed by message ID.
			long timestamp = System.currentTimeMillis() + timeout;
			Object data = null;
			while (System.currentTimeMillis() < timestamp)
			{
				try
				{
					wait(200);
				}
				catch (InterruptedException ignored)
				{}
				data = pendingMessages.get(id);
				if (data instanceof String) return (String) data;
			}
			//			System.out.println("Timed out!");
			throw new IOException("Timeout!");
		}

		/*************************************************************************
		 * Wake the waiting thread so it can check if a reply has been received.
		 */
		synchronized void reply()
		{
			notify();
		}
	}

	/****************************************************************************
	 * Decode a message from a remote module to a local module.
	 * 
	 * @param the ID of the message
	 * @param senderName the name of the module that sent the message.
	 * @param senderAddress the address of the sender.
	 * @param recipientName the name of the module that is to receive the
	 * message.
	 * @param message the message text.
	 */
	private void handleMessage(long id, String senderName, String senderAddress,
			String recipientName, String message) throws IOException
	{
		try
		{
			//				System.out.println("Message from "+senderName+" at "+senderAddress
			//					+", id "+id+" to "+recipientName+": "+message);
			// See if the recipient is one of our services or clients.
			NetworkerModule module = (NetworkerModule) localModules
					.get(recipientName);
			if (module == null) throw new IOException("Module " + recipientName
					+ " unknown; Networker can't handle message.\n" + "Sender was "
					+ senderName);
			// Check the message ID.
			// If it's zero, this message is a simple 'tell'.
			// If it's greater than zero it's an 'ask'.
			// Otherwise it's a reply to an 'ask'.
			if (id == 0)
			{
				// Create a proxy for the module and pass on the message.
				module.told(new Proxy(senderName, senderAddress), message);
			}
			else if (id > 0)
			{
				// Ask the module and get its reply
				String reply = module.asked(new Proxy(senderName, senderAddress),
						message);
				// Send the reply back to the sender of this message
				// Negate the ID to indicate this is a reply.
				//					System.out.println("Sending reply to "+senderAddress+":
				// {"+(-id)+"}{"+recipientName+"}{"
				//						+senderName+"}"+reply);
				sendMessage(senderAddress, "{" + (-id) + "}{" + recipientName
						+ "}{" + senderName + "}" + reply);
			}
			else
			{
				//					System.out.println("Reply from "+senderName+" to
				// "+recipientName+": "+message);
				id = -id;
				String idString = "" + id;
				synchronized (pendingMessages)
				{
					Object msg = pendingMessages.get(idString);
					if (msg instanceof Asker)
					{
						Asker asker = (Asker) msg;
						if (asker != null)
						{
							pendingMessages.put(idString, message);
							asker.reply();
						}
						else
						{
							System.out.println("Reply from " + senderName + " to "
									+ recipientName + ": " + message);
							System.out.println("Message " + id + " not known");
						}
					}
				}
			}
		}
		catch (NullPointerException e)
		{}
	}

	/****************************************************************************
	 * Add a local service or client to my lists. After adding, we re-announce
	 * ourselves so the rest of the network knows about the new module and the
	 * new module gets to hear about the rest of the world. If the service is on
	 * our own computer we need to announce it separately to local clients.
	 * 
	 * @param module the module being added.
	 */
	public synchronized void add(NetworkerModule module)
	{
		localModules.put(module.getModuleName(), module);
		if (module instanceof NetworkerService)
		{
			myServices.add((NetworkerService) module);
			allServices.add((NetworkerService) module);
			if (module.getModuleAddress().equals(myAddress))
			{
				addServices(myAddress, "{" + module.getModuleName() + "}{"
						+ module.getModuleType() + "}");
			}
		}
		else
		{
			// If it's a client, notify it of all known services.
			NetworkerClient client = (NetworkerClient) module;
			Vector v = new Vector();
			synchronized (allServices)
			{
				//				System.out.println(""+allServices.size()+" service(s) found");
				Enumeration enumeration = allServices.elements();
				while (enumeration.hasMoreElements())
				{
					NetworkerService service = (NetworkerService) enumeration.nextElement();
					//					System.out.println(" "+service.getModuleAddress()
					//						+" "+service.getModuleName()+" "
					//						+service.getModuleType());
					if (service.getModuleType().equals(client.getModuleType()))
					{
						// If we haven't already seen this one, add it to the list.
						if (!v.contains(service))
						{
							v
									.addElement(new ProxyService(
											service.getModuleName(), service
													.getModuleAddress(), service
													.getModuleType()));
						}
					}
				}
			}
			NetworkerService[] s = new NetworkerService[v.size()];
			// Java 1.2 if (s.length>0) v.toArray(s);
			for (int n = 0; n < s.length; n++)
				s[n] = (NetworkerService) v.elementAt(n);
			new Notifier(client, s);
		}
		try
		{
			announce();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/****************************************************************************
	 * Add a service list (from another node) to our master list of services.
	 * Notify all local clients about services that match its type. Send a
	 * notification even if there are no services. This deals with services that
	 * have gone offline.
	 * 
	 * @param address the address of the node.
	 * @param list the list of services at this node.
	 */
	private synchronized void addServices(String address, String list)
	{
		// First delete all existing services from the originating node.
		ServiceList sl = new ServiceList();
		Enumeration enumeration;
		synchronized (allServices)
		{
			enumeration = allServices.elements();
			while (enumeration.hasMoreElements())
			{
				NetworkerService service = (NetworkerService) enumeration.nextElement();
				if (!service.getModuleAddress().equals(address)) sl.add(service);
			}
			allServices = sl;
		}
		// Now add the new services.
		// Enumerate the services listed and add each to our main service list.
		int index = 0;
		enumeration = (new ServiceList(address, list)).elements();
		while (enumeration.hasMoreElements())
		{
			allServices
					.add(new ProxyService((NetworkerService) enumeration.nextElement()));
			index++;
		}
		// If any services were added, notify all clients.
		if (index > 0) notifyClients();
	}

	/****************************************************************************
	 * Notify all clients about added services. Note that this will often result
	 * in a renotification of existing services.
	 */
	private void notifyClients()
	{
		//		System.out.println("Networker: notifyClients");
		Enumeration enumeration = localModules.elements();
		while (enumeration.hasMoreElements())
		{
			NetworkerModule module = (NetworkerModule) enumeration.nextElement();
			if (module instanceof NetworkerClient)
			{
				NetworkerClient client = (NetworkerClient) module;
				Vector v = new Vector();
				synchronized (allServices)
				{
					//					System.out.println("\n"+allServices.size()+" service(s)
					// found");
					Enumeration enum2 = allServices.elements();
					while (enum2.hasMoreElements())
					{
						NetworkerService service = (NetworkerService) enum2
								.nextElement();
						//						System.out.println(" "+service.getModuleAddress()
						//							+" "+service.getModuleName()+" "
						//							+service.getModuleType());
						if (service.getModuleType().equals(client.getModuleType()))
						{
							// If we haven't already seen this one, add it to the list.
							if (!v.contains(service)) v.addElement(new ProxyService(
									service.getModuleName(), service.getModuleAddress(),
									service.getModuleType()));
						}
					}
				}
				NetworkerService[] ss = new NetworkerService[v.size()];
				// Java 1.2 if (s.length>0) v.toArray(s);
				for (int n = 0; n < ss.length; n++)
					ss[n] = (NetworkerService) v.elementAt(n);
				new Notifier(client, ss);
			}
		}
	}

	/****************************************************************************
	 * This inner class holds the notification to a single client.
	 */
	class Notifier implements Runnable
	{
		NetworkerClient client;
		NetworkerService[] services;

		Notifier(NetworkerClient client, NetworkerService[] services)
		{
			this.client = client;
			this.services = services;
			new Thread(this).start();
		}

		public void run()
		{
			//			System.out.println("Notify client of "+services.length+" new
			// service(s)");
			client.notify(services);
		}
	}

	/****************************************************************************
	 * This inner class encapsulates the service list of this node. This class
	 * should be able to extend Vector but for some reason the Jeode engine on
	 * Zaurus can't handle that.
	 */
	class ServiceList
	{
		Vector list = new Vector();

		/*************************************************************************
		 * Default constructor.
		 */
		ServiceList()
		{}

		/*************************************************************************
		 * Construct a service list from a string.
		 */
		ServiceList(String address, String list)
		{
			int n = 0;
			try
			{
				while (n < list.length())
				{
					// Get the title of the service
					if (list.charAt(n++) != '{') break;
					int m = list.indexOf('}', n);
					if (m < 0) break;
					String title = new String(list.substring(n, m));
					n = m + 1;
					// Get the service type
					if (list.charAt(n++) != '{') break;
					m = list.indexOf('}', n);
					if (m < 0) break;
					String type = new String(list.substring(n, m));
					// Create a new service object and add it to our list.
					add(title, type, address);
					n = m + 1;
				}
			}
			catch (IndexOutOfBoundsException e)
			{}
		}

		void removeAllElements()
		{
			list.removeAllElements();
		}

		void addElement(Object data)
		{
			list.addElement(data);
		}

		void removeElementAt(int index)
		{
			list.removeElementAt(index);
		}

		Object elementAt(int index)
		{
			return list.elementAt(index);
		}

		int size()
		{
			return list.size();
		}

		Enumeration elements()
		{
			return list.elements();
		}

		/*************************************************************************
		 * Add a new service to this list.
		 */
		void add(NetworkerService service)
		{
			add(service.getModuleName(), service.getModuleType(), service
					.getModuleAddress());
		}

		/*************************************************************************
		 * Add a new service to this list.
		 */
		void add(String name, String type, String address)
		{
			list.addElement(new ProxyService(name, address, type));
		}

		/*************************************************************************
		 * Convert this service list to a string.
		 */
		public String toString()
		{
			StringBuffer sb = new StringBuffer();
			Enumeration enumeration = elements();
			while (enumeration.hasMoreElements())
			{
				sb.append(((NetworkerService) enumeration.nextElement()).toString());
			}
			return sb.toString();
		}
	}

	/****************************************************************************
	 * This inner class encapsulates a proxy module. This contains all the
	 * important network-related information of the class it's proxying, and
	 * avoids the need to use RMI.
	 */
	class Proxy extends NetworkerModule
	{
		public Proxy(String name, String address)
		{
			super(name, address, "");
		}

		/*************************************************************************
		 * Someone is telling this module something.
		 */
		public void told(NetworkerModule module, String message)
		{
			System.out.println("Proxy: Module telling me something!");
		}

		/*************************************************************************
		 * Someone is asking this module something.
		 */
		public String asked(NetworkerModule module, String message)
		{
			System.out.println("Proxy: Module asking me something!");
			return null;
		}
	}

	/****************************************************************************
	 * This inner class encapsulates a proxy service. This contains all the
	 * important network-related information of the class it's proxying, and
	 * avoids the need to use RMI. Since it's an inner class of Networker it has
	 * access to the table that contains all services known to this computer.
	 * <p>
	 * A service comprises the following items:
	 * 
	 * <pre>
	 * 
	 *  
	 *  	 1 A name string.  This is a name by which this service is known, allowing
	 *  	 it to be distinguished from other services of the same type (see next)
	 *  	 on the same or other nodes.
	 *  	 
	 *  
	 * <br>
	 * 
	 *  
	 *  	 2 The IP address of the computer the service is running on.
	 *  	 
	 *  
	 * <br>
	 * 
	 *  
	 *  	 3 A type code that identifies the kind of service being offered. A system is
	 *  	 free to use whatever codes it pleases, so a clock service might be called
	 *  	 simply 'Clock'.  Clients use these codes to offer their users a choice of
	 *  	 services of a particular type; the name (above) should then identify
	 *  	 which is which in a meaningful manner.
	 *  	 
	 *  
	 * </pre>
	 */
	class ProxyService extends NetworkerService
	{
		ProxyService(NetworkerService service)
		{
			this(service.getModuleName(), service.getModuleAddress(), service
					.getModuleType());
		}

		ProxyService(String name, String address, String type)
		{
			super(name, address, type);
		}

		/*************************************************************************
		 * Someone is telling this service something.
		 */
		public void told(NetworkerModule module, String message)
		{
			System.out.println("ProxyService: Module telling me something!");
		}

		/*************************************************************************
		 * Someone is asking this module something.
		 */
		public String asked(NetworkerModule module, String message)
		{
			System.out.println("ProxyService: Module asking me something!");
			return null;
		}
	}

	/****************************************************************************
	 * This inner class encapsulates a proxy client. This contains all the
	 * important network-related information of the class it's proxying, and
	 * avoids the need to use RMI. Since it's an inner class of Networker it has
	 * access to the table that contains all services known to this computer.
	 */
	class ProxyClient extends NetworkerClient
	{
		public ProxyClient(String name, String address)
		{
			super(name, address, "");
		}

		public void notify(NetworkerService[] services)
		{}

		/*************************************************************************
		 * Someone is telling this client something.
		 */
		public void told(NetworkerModule module, String message)
		{
			System.out.println("ProxyClient: Module telling me something!");
		}

		/*************************************************************************
		 * Someone is asking this module something.
		 */
		public String asked(NetworkerModule module, String message)
		{
			System.out.println("ProxyClient: Module asking me something!");
			return null;
		}

		public void newServices()
		{}
	}

	/****************************************************************************
	 * This inner class encapsulates a packet for transmission.
	 */
	class Packet
	{
		String address;
		String message;

		Packet(String address, String message)
		{
			this.address = address;
			this.message = message;
		}
	}
}