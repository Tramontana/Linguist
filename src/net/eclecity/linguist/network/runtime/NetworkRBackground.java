// NetworkRBackground.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.runtime;

import java.io.IOException;

import net.eclecity.linguist.network.handler.NetworkHClient;
import net.eclecity.linguist.network.handler.NetworkHService;
import net.eclecity.linguist.networker.Networker;
import net.eclecity.linguist.runtime.LRBackground;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.runtime.LRProgram;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Handle background actions for the network package.
	<pre>
	[1.002 GT]  03/05/01  Add Networker.
	[1.001 GT]  17/02/01  Pre-existing.
	</pre>
*/
public class NetworkRBackground extends LRBackground
{
	private static Networker networker;

	/***************************************************************************
		Methods that must be implemented.
	*/
	public void initBackground(Object data) {}
 	public void onMessage(LRProgram p,String message) {}
	public void doFinishActions() {}

	/***************************************************************************
		Do a Networker operation.
	*/
	public void doNetworker(int opcode,Object data1,Object data2) throws LRException
	{
		switch (opcode)
		{
			case START_NETWORKER:
				if (networker==null)
				{
					try
					{
						networker=Networker.getInstance();
					}
					catch (Exception e) { throw new LRException(NetworkRMessages.cantCreateNetworker()+": "+e.getMessage()); }
				}
				break;
			case SET_NETWORK_NAME:
				networkName=((LVValue)data1).getStringValue();
				networker.setName(networkName);
				break;
			case ADD_SERVICE_TO_NETWORKER:
				((NetworkHService)data1).addTo(networker);
				break;
			case ADD_CLIENT_TO_NETWORKER:
				((NetworkHClient)data1).addTo(networker);
				break;
			case START_WATCHDOG:
				new Thread(new Runnable()
				{
					public void run()
					{
						System.out.println("Networker watchdog running.");
						while (true)
						{
							try { Thread.sleep(10000); }
							catch (InterruptedException e) {}
							try { networker.sendWatchdogMulticast(); }
							catch (IOException e) {}
						}
					}
				}).start();
				break;
		}
	}

	/***************************************************************************
		Get this Networker.
	*/
	public Networker getNetworker()
	{
		return networker;
	}

	/***************************************************************************
		Opcodes.
	*/
	public static final int
		START_NETWORKER=1,
		SET_NETWORK_NAME=2,
		ADD_SERVICE_TO_NETWORKER=3,
		ADD_CLIENT_TO_NETWORKER=4,
		START_WATCHDOG=5;
}

