// LDBTransaction.java

package net.eclecity.linguist.persist;

import java.util.Enumeration;
import java.util.Hashtable;

/******************************************************************************
	The LDBTransaction class manages individual transactions
	and also maintains a cache of objects to avoid excessive
	database traffic.
	A background task runs to identify unused records and
	delete them from the cache.  The expiry time is set to
	one hour.
*/
public class LDBTransaction extends LDBObject
{
	private static LDBPersistent persistent=null;
	private static Hashtable allRecords=new Hashtable();
	private Hashtable myRecords=new Hashtable();

	public LDBTransaction()
	{
		super();
		new LDBRecordWatcher(allRecords);
	}

	/***************************************************************************
		Get the allRecords Hashtable.
	*/
	public static Hashtable getAllRecords() { return allRecords; }

	/***************************************************************************
		Get the allRecords Hashtable.
	*/
	public static void deleteAllRecords() { allRecords=new Hashtable(); }

	/***************************************************************************
		Check if the database is connected.
	*/
	public static boolean isConnected()
	{
		return (persistent!=null);
	}

	/***************************************************************************
		Connect to the database object.
		@param p the driver to use for persistent storage.
		@param name a string required by the particular database driver for initialization.
	*/
	public static void connect(LDBPersistent p,String name) throws LDBException
	{
		p.connect(name);
		persistent=p;
	}

	/***************************************************************************
		Disconnect from the database.
	*/
	public static void disconnect() throws LDBException
	{
		persistent.disconnect();
	}

	/***************************************************************************
		Save a new record.  This is one point at which the record
		enters the local cache.  If it's not already in the cache,
		a search is made in the database.  If a matching record is found
		an exception will result.
		@param record the record to save
	*/
	public void save(LDBRecord record) throws LDBException
	{
		// Check if the record is in the main cache.
		// If so, throw an exception.
		if (allRecords.containsKey(record.getTableName()+":"+record.getID()))
			throw new LDBRecordExistsException();
//		Enumeration en=allRecords.keys();
//		while (en.hasMoreElements())
//		{
//			String id2=(String)en.nextElement();
//			if (id2.equals(fullID)) throw new LDBRecordExistsException();
//		}
		// Create a duplicate and try to restore it.
		// If it succeeds we have an error.
		// Either way the duplicate is never used.
		LDBRecord rc2=record.duplicate();
		try
		{
			persistent.restore(rc2);
		}
		catch (LDBException e)
		{
			// If we get here the record was not found,
			// so now we can save it.
			record.setNew();
			cache(record.duplicate());
			return;
		}
		// If we get here the record already exists
		throw new LDBRecordExistsException();
	}

	/***************************************************************************
		Cache a record.  This is the second point at which the record
		enters the local cache.
		The record (assumed to be already in the database) is re-entered into the
		local cache for this transaction.  This is used after a commit(), which
		will have cleared the cache, and so avoids the need to do another lookup
		for a record we have previously been using.
		The record is also re-enterd into the main cache in case it timed out.
		@param record the record to save
	*/
	public void cache(LDBRecord record)
	{
		addToAllRecords(record.getTableName()+":"+record.getID(),record);
		addToMyRecords(record);
	}

	/***************************************************************************
		Restore a record.  This is the third place at which
		a record enters the cache.
		@param record a template record having all the necessary fields
		but with only the ID field needing to have a value
	*/
	public LDBRecord restore(LDBRecord record) throws LDBException
	{
		String fullID=record.getTableName()+":"+record.getID();
		if (allRecords.containsKey(fullID))
		{
			record=((LDBDatedRecord)allRecords.get(fullID)).record;
		}
		else
		{
			persistent.restore(record);
			addToAllRecords(fullID,record);
		}
		addToMyRecords(fullID,record);
		return record;
	}

	/***************************************************************************
		Delete a record.
		@param record the record to delete
	*/
	public void delete(LDBRecord record) throws LDBException
	{
		String id=record.getTableName()+":"+record.getID();
		myRecords.remove(id);
		allRecords.remove(id);
		persistent.delete(record);
	}

	/***************************************************************************
		Delete records of this type according to the expression provided.
		Once the command has been issued, check every record in 'allRecords'
		to see if it's still in the database.  If not, remove it from the cache.
		@param record a record of the type required
		@param expression the expression to use for the search
	*/
	public void deleteRecords(LDBRecord record,String expression) throws LDBException
	{
		persistent.deleteRecords(record,expression);
//		println("Checking the cache...");
		Hashtable ht=new Hashtable();
		Enumeration enumeration=allRecords.keys();
		while (enumeration.hasMoreElements())
		{
			String key=(String)enumeration.nextElement();
			LDBDatedRecord dr=(LDBDatedRecord)allRecords.get(key);
			record=dr.record;
			if (persistent.exists(record)) ht.put(key,dr);
//			else println(key+" removed from cache.");
		}
		allRecords=ht;
	}

	/***************************************************************************
		Test if a record exists in the database.
		@param record the record to test for
	*/
	public boolean exists(LDBRecord record) throws LDBException
	{
		if (allRecords.containsKey(record.getTableName()+":"+record.getID())) return true;
		return persistent.exists(record);
	}

	/***************************************************************************
		Get records of this type that match the field list provided.
		@param record a record of the type required
		@param fields the field list
	*/
	public void getRecords(LDBRecord record,Hashtable fields) throws LDBException
	{
		persistent.getRecords(record,fields);
	}

	/***************************************************************************
		Get records of this type according to the expression provided.
		@param record a record of the type required
		@param expression the SQL expression to use for the search
	*/
	public void getRecords(LDBRecord record,String expression) throws LDBException
	{
		persistent.getRecords(record,expression);
	}

	/***************************************************************************
		Check if there are any more records in the current result set.
		@param record the record owning the result set
	*/
	public boolean hasMoreRecords(LDBRecord record) throws LDBException
	{
		return persistent.hasMoreRecords(record);
	}

	/***************************************************************************
		Get the next record from the current result set.
		This is another place a record gets into the cache.
		@param record the record owning the result set
	*/
	public void getNextRecord(LDBRecord record) throws LDBException
	{
		persistent.getNextRecord(record);
		Object id=record.getID();
		String name=record.getTableName()+":"+id;
		record=record.duplicate();
		addToAllRecords(name,record);
		addToMyRecords(name,record);
	}

	/***************************************************************************
		Count the number of records that match a query.
		@param record the record owning the result set
		@param query the SQL query string
	*/
	public int count(LDBRecord record,String query) throws LDBException
	{
		return persistent.count(record,query);
	}

	public void sql(String command) throws LDBException { persistent.sql(command); }

	/***************************************************************************
		Commit the transaction to the database.
		Although this does not itself release any records,
		the calling client will dispose of this transaction
		object and create a new one.
	*/
	public void commit() throws LDBException
	{
		Enumeration enumeration=myRecords.keys();
		while (enumeration.hasMoreElements())
		{
			String fullID=(String)enumeration.nextElement();
			LDBRecord record=((LDBDatedRecord)myRecords.get(fullID)).record;
			// Do the necessary operation on the database.
			// If an exception occurs, remove this record from the cache
			// to force a reload next time.  Otherwise the record may
			// block the system.
			try
			{
//				println("Commit "+record.getID()+": "+record.getStatus());
				switch (record.getStatus())
				{
					case LDBRecord.NEW:
						persistent.insert(record);
						record.setSafe();
						break;
					case LDBRecord.CHANGED:
						persistent.update(record);
						record.setSafe();
						break;
					case LDBRecord.DELETED:
						persistent.delete(record);
						allRecords.remove(fullID);
						myRecords.remove(fullID);
						break;
				}
			}
			catch (LDBException e)
			{
				println("Error committing record "+fullID);
//				e.printStackTrace();
				allRecords.remove(fullID);
				myRecords.remove(fullID);
				throw e;
			}
		}
		persistent.commit();
	}

	/***************************************************************************
		Create a new database table for a record type.  All records of
		this type must be removed from the cache.
		@param record a template record having all the fields required for its
			type.  The field values are arbitrary as we only use the
			field specifications.
		@param delete true if the existing table is to be deleted.
	*/
	public void createTable(LDBRecord record,boolean delete) throws LDBException
	{
		if (persistent==null) throw new LDBNoPersistentException();
		String tableName=record.getTableName();
		Enumeration en=allRecords.keys();
		while (en.hasMoreElements())
		{
			String key=(String)en.nextElement();
			if (key.substring(0,key.indexOf(":")).equals(tableName)) allRecords.remove(key);
		}
		persistent.createTable(record,delete);
	}

	/***************************************************************************
		Remove a client.
		Notify all interested records that a client has closed.
		If a record returns true this was the last of its clients
		so it can be removed from the record table.
		This method is static because it manages the static
		list of all records.
		@param client the client to remove
	*/
	public static synchronized void removeClient(LDBClient client)
	{
		Hashtable ht=new Hashtable();
		Enumeration en=allRecords.keys();
		while (en.hasMoreElements())
		{
			String key=(String)en.nextElement();
			LDBDatedRecord dr=(LDBDatedRecord)allRecords.get(key);
			LDBRecord record=dr.record;
			if (!record.removeClient(client)) ht.put(key,dr);
		}
		allRecords=ht;
	}

	/***************************************************************************
		Add a record to the main cache.
		The cache limit is set to 10,000 records.
	*/
	private void addToAllRecords(String name,LDBRecord record)
	{
		synchronized (allRecords)
		{
			if (allRecords.size()<10000) allRecords.put(name,new LDBDatedRecord(record));
		}
	}

	/***************************************************************************
		Add a record to the local cache.
		This is only current for a short while so it won't often hold many records.
	*/
	public void addToMyRecords(LDBRecord record)
	{
		addToMyRecords(record.getTableName()+":"+record.getID(),record);
	}

	private void addToMyRecords(String name,LDBRecord record)
	{
		synchronized (myRecords)
		{
			myRecords.put(name,new LDBDatedRecord(record));
		}
	}
}

/***************************************************************************
	This class holds a record with a date attached.  When a record is
	created it is set to expire one hour later.  The background
	garbage collector below will then remove it from the cache.
*/
	class LDBDatedRecord
	{
		LDBRecord record;
		long expiry;

		LDBDatedRecord(LDBRecord record)
		{
			this.record=record;
			expiry=System.currentTimeMillis()+1000*60*60;
//			expiry=System.currentTimeMillis()+1000*5;
		}

		boolean hasExpired()
		{
			return (System.currentTimeMillis()>expiry);
		}
	}

/***************************************************************************
	This class monitors the cache and removes all expired records.
*/
class LDBRecordWatcher implements Runnable
{
	private static Hashtable allRecords=null;

	LDBRecordWatcher(Hashtable t)
	{
		if (allRecords==null)
		{
			allRecords=t;
			System.out.println("Record watcher started.");
			new Thread(this).start();
		}
	}

	public void run()
	{
		while (true)
		{
			try { Thread.sleep(60*1000); } catch (InterruptedException e) { return; }
			synchronized (allRecords)
			{
				Enumeration enumeration=allRecords.keys();
				while (enumeration.hasMoreElements())
				{
					String key=(String)enumeration.nextElement();
					if (((LDBDatedRecord)allRecords.get(key)).hasExpired())
					{
						allRecords.remove(key);
					}
				}
			}
		}
	}
}

