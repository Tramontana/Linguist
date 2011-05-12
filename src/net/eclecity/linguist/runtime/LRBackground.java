// LRBackground.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.runtime;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Hashtable;

/******************************************************************************
	The base class of all background threads.
*/
public abstract class LRBackground implements Runnable, java.io.Serializable
{
	protected LRProgram program;
	protected Object backgroundData;
	protected String networkName;

	private String name;
	private boolean ready=false;
	private Thread myThread;
	private Hashtable userData;

	/***************************************************************************
		Constructor.
	*/
	public LRBackground() { }

	/***************************************************************************
		Initialize the background.
	*/
	public void init(LRProgram program,String name,Object data) throws LRException
	{
		this.program=program;
		initBackground(data);
		this.name=name;
		userData=new Hashtable();
		(myThread=new Thread(this,name)).start();
	}

	/***************************************************************************
		Pause for a while.
	*/
	public static synchronized void pause(int ms)
	{
		try { Thread.sleep(ms); }
		catch (InterruptedException e) {}
	}

	/***************************************************************************
		Signal the readiness of this background to run.
	*/
	public void run() { ready=true; }

	/***************************************************************************
		Finish this background and tidy up.
	*/
	public void finish()
	{
		myThread.interrupt();
		doFinishActions();
	}

	/***************************************************************************
		Get the network name of this computer.
	*/
	public String getNetworkName()
	{
		if (networkName!=null) return networkName;
		InetAddress ia=null;
		try { ia=InetAddress.getLocalHost(); }
		catch (UnknownHostException e) { return ""; }
		return ia.getHostName();
	}

	public void setBackgroundData(Object data) { backgroundData=data; }
	public Object getBackgroundData() { return backgroundData; }
	public boolean isReady() { return ready; }
	public void setReady() { ready=true; }
	public void setUserData(String key,Object data) { userData.put(key,data); }
	public String getName() { return name; }
	public Object getUserData(String key) { return userData.get(key); }
	public void print(String s) { System.out.print(s); }
	public void println(String s) { System.out.println(s); }
	public void println() { System.out.println(); }

	protected abstract void initBackground(Object data) throws LRException;
	public abstract void onMessage(LRProgram p,String message);
	public abstract void doFinishActions();
}
