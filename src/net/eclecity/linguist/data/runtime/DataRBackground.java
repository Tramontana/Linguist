// DataRBackground.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.data.runtime;

import net.eclecity.linguist.persist.LDBClient;
import net.eclecity.linguist.persist.LDBException;
import net.eclecity.linguist.runtime.LRBackground;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.runtime.LRProgram;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Handle background actions for the data package.
	Here we keep a client object and handle
	connecting to the database.
	
	In a servlet environment there will be several
	instances of LRProgram, each with its own
	set of backgrounds.  Each DataRBackground has
	its own client, which it uses for all
	transactions following a call to doGet()
	or doPost();
*/
public class DataRBackground extends LRBackground
{
	private LDBClient client=new LDBClient();		// a private client for this instance
	private LDBException status=null;				// the most recent exception code

	public DataRBackground() {}

	protected void initBackground(Object data) {}
 	public void onMessage(LRProgram p,String message) {}
	public void doFinishActions() {}

	/***************************************************************************
		Initialize the database.
	*/
	public void initDB(LVValue name) throws LRException
	{
		try
		{
			client.connect(name.getStringValue());
		}
		catch (LDBException e)
		{
			e.printStackTrace();
			throw new LRException(e);
		}
		client.clear();
	}
	
	/***************************************************************************
		Start a new transaction (clear the client).
	*/
	public void newTransaction()
	{
		client.clear();
	}
	
	/***************************************************************************
		Commit the current transaction.
	*/
	public void commit()
	{
		try { client.commit(); }
		catch (LDBException e) { setStatus(e); }
	}
	
	/***************************************************************************
		Issue a specific SQL command.
	*/
	public void sql(String command)
	{
		try { client.sql(command); }
		catch (LDBException e) { setStatus(e); }
	}
	
	public void clearStatus() { status=null; }
	public void setStatus(LDBException e) { status=e; }
	public String getStatus()
	{
		if (status!=null) return status.toString();
		return "OK";
   }

	/** Return the client for this background */
	public LDBClient getClient() { return client; }
}
