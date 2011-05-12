// LDBClient.java

package net.eclecity.linguist.persist;

import java.util.Enumeration;
import java.util.Vector;

/******************************************************************************
	A class that represents a client for database transactions.
	This is the only route into the system for database access.
*/
public class LDBClient extends LDBObject implements LDBNotifyListener
{
	private LDBPersistent store;
	private LDBTransaction transaction;
	private Vector listeners=new Vector();

	/***************************************************************************
		Default constructor.
	*/
	public LDBClient() {}

	/***************************************************************************
		Add a notify listener to this client.
		@param l the listener that wishes to be notified when changes are made
		to any record currently being accessed by that listener
	*/
	public void addNotifyListener(LDBNotifyListener l)
	{
		if (!listeners.contains(l)) listeners.addElement(l);
	}

	/***************************************************************************
		Remove a notify listener from this client.
		@param l the listener to remove
	*/
	public void removeNotifyListener(LDBNotifyListener l)
	{
		listeners.removeElement(l);
	}

	/***************************************************************************
		Notify is called whenever a change is made to a record
		currently in use by this client.
		@param record the record that changed
		@param client the client that made the change
	*/
	public void notify(LDBRecord record,LDBClient client) throws LDBException
	{
		Enumeration en=listeners.elements();
		while (en.hasMoreElements())
			((LDBNotifyListener)en.nextElement()).notify(record,client);
	}

	/***************************************************************************
		Lock a record to this client.
		@param record the record to lock
	*/
	public void lock(LDBRecord record) throws LDBException { record.lock(this); }

	/***************************************************************************
		Clear this client and get a new transaction.
		The new transaction now belongs to this client.
	*/
	public void clear()
	{
		transaction=new LDBTransaction();
	}

	/***************************************************************************
		Commit the current transaction.
		This also closes the transaction, so don't commit until
		you have no further use for the records you gathered.
	*/
	public void commit() throws LDBException
	{
		if (transaction==null) throw new LDBNoTransactionException();
		transaction.commit();
		clear();
	}

	/***************************************************************************
		Return the transaction.
		Throw an error if the transaction hasn't been defined.
	*/
	public LDBTransaction getTransaction() throws LDBException
	{
		if (transaction==null) throw new LDBNoTransactionException();
		return transaction;
	}

	/***************************************************************************
		Close this client.
		Notify the transaction class so it can remove orphan records.
		This calls a static method in LDBTransaction because the transaction
		object has probably already been deleted following a commit().
	*/
	public void close()
	{
		LDBTransaction.removeClient(this);
		clear();
	}

	/***************************************************************************
		Connect to the default database.
		This is currently set to "IDB".
	*/
	public void connect(String name) throws LDBException { connect(IDB,name); }

	/***************************************************************************
		Connect to the database.
		This method selects a database driver depending on the value of the parameter.
		To add a new database type, write a driver (in linguist.persist.driver.XXX),
		give it a suitable name and add it to the list checked for here.
		@param type the type of database requested.
	*/
	public void connect(String type,String name) throws LDBException
	{
		if (!LDBTransaction.isConnected())
		{
			if (type.equals(IDB))
			{
				try
				{
					Class c=Class.forName("net.eclecity.linguist.persist.driver.LDBIDB");
					store=(LDBPersistent)c.newInstance();
				}
				catch (ClassNotFoundException e) { throw new LDBUnknownStoreException(); }
				catch (InstantiationException e) { throw new LDBUnknownStoreException(); }
				catch (IllegalAccessException e) { throw new LDBUnknownStoreException(); }
			}
			else throw new LDBUnknownStoreException();
			LDBTransaction.connect(store,name);
		}
	}

	/***************************************************************************
		Get the persistent object.
	*/
	public LDBPersistent getPersistent()
	{
		return store;
	}

	/***************************************************************************
		Create a table for a record.
		@param record the record to save
		@param dropTable true if we should drop the previous table
	*/
	public void createNewTable(LDBRecord record,boolean dropTable) throws LDBException
	{
		record.createNewTable(this,dropTable);
	}

	/***************************************************************************
		Get a new record.
		@param table the table name
	*/
	public LDBRecord getNewRecord(String table)
	{
		return new LDBRecord(this,table);
	}

	/***************************************************************************
		Save a record.
		@param record the record to save
	*/
	public void saveRecord(LDBRecord record) throws LDBException
	{
		record.save(this);
	}

	/***************************************************************************
		Cache a record.
		@param record the record to cache
	*/
	public void cacheRecord(LDBRecord record) throws LDBException
	{
		record.cache(this);
	}

	/***************************************************************************
		Restore a record.
		@param record the record to restore
	*/
	public LDBRecord restoreRecord(LDBRecord record) throws LDBException
	{
		return record.restore(this);
	}

	/***************************************************************************
		Get all records of the type given.
		@param record a record of the type wanted
	*/
	public void getRecords(LDBRecord record) throws LDBException
	{
		record.getRecords(this);
	}

	/***************************************************************************
		Get all records of the type given that match the expression provided.
		@param record a record of the type wanted
		@param expression an SQL expression
	*/
	public void getRecords(LDBRecord record,String expression) throws LDBException
	{
		record.getRecords(this,expression);
	}

	/***************************************************************************
		Find if the given record has more in its result set.
		@param record a record of the type wanted
		@returns true if there are more records
	*/
	public boolean hasMoreRecords(LDBRecord record) throws LDBException
	{
		return record.hasMoreRecords(this);
	}

	/***************************************************************************
		Get the next record from a result set.
		@param record a record of the type wanted
	*/
	public void getNextRecord(LDBRecord record) throws LDBException
	{
		record.getNextRecord(this);
	}

	/***************************************************************************
		Check if a record exists.
		@param record the record to test
	*/
	public boolean recordExists(LDBRecord record) throws LDBException
	{
		return record.exists(this);
	}

	/***************************************************************************
		Issue an arbitrary SQL command.
		@param command the SQL command
	*/
	public void sql(String command) throws LDBException { transaction.sql(command); }

	/***************************************************************************
		Disconnect from the database.
	*/
	public void disconnect() throws LDBException { LDBTransaction.disconnect(); }

	/***************************************************************************
		The code that identifies a database type.
	*/
	public static final String IDB="IDB";
}
