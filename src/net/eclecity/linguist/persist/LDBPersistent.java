// LDBPersistent.java

package net.eclecity.linguist.persist;

import java.util.Hashtable;
import java.util.Properties;

/******************************************************************************
	This interface specifies the methods
	that must be implemented by a database (or other)
	driver in order to save and restore records
	and perform other related tasks.
*/
public interface LDBPersistent
{
	/***************************************************************************
		Connect to the database.
		@param url the URL to connect to
	*/
	public void connect(String url) throws LDBException;
	/***************************************************************************
		Connect to the database.
		@param driver the full classpath of the JDBC driver
		@param url the URL to connect to
	*/
	public void connect(String driver,String url) throws LDBException;
	/***************************************************************************
		Disconnect from the database.
	*/
	public void disconnect() throws LDBException;
	/***************************************************************************
		Commit all outstanding transactions to the database.
	*/
	public void commit() throws LDBException;
	/***************************************************************************
		Delete a table using the table name in the record given.
		@param record a record containing the required name
	*/
	public void deleteTable(LDBRecord record) throws LDBException;
	/***************************************************************************
		Create a table for the record type given.
		The method should use the field specifications
		to create a table that holds records of this type.
		The name of the table is contained in the record.
		@param record a record having the required fields
		@param delete true if the old table is to be deleted
	*/
	public void createTable(LDBRecord record,boolean delete) throws LDBException;
	/***************************************************************************
		Insert a record into a table.  Used by the XML importer.
		@param tableName the name of the table.
		@param table a table of name/value pairs.
	*/
	public void insert(String tableName,Properties table) throws LDBException;
	/***************************************************************************
		Insert a record into the database.
		The name of the table to use is contained in the record.
		An exception will be thrown if a record already exists
		having the same ID.
		@param record the record to insert
	*/
	public void insert(LDBRecord record) throws LDBException;
	/***************************************************************************
		Update a record in the database.
		The name of the table to use is contained in the record.
		@param record the record to update
	*/
	public void update(LDBRecord record) throws LDBException;
	/***************************************************************************
		Restore a record from the database.
		The name of the table to use is contained in the record.
		An exception will be thrown if the requested record is not present.
		@param record the record to restore.  This object must contain
		at least the ID of the record.
	*/
	public void restore(LDBRecord record) throws LDBException;
	/***************************************************************************
		Delete a record from the database.
		An exception will be thrown if the requested record is not present.
		@param record the record to delete.  This object must contain
		at least the ID of the record.
	*/
	public void delete(LDBRecord record) throws LDBException;
	/***************************************************************************
		Report if a record is present in the database.
		@param record the record to check
	*/
	public boolean exists(LDBRecord record) throws LDBException;
	/***************************************************************************
		Get records of this type that match the field list provided.
		@param client the client making the request
		@param fields the field list
	*/
	public void getRecords(LDBRecord record,Hashtable fields) throws LDBException;
	/***************************************************************************
		Get records of this type according to the expression provided.
		@param record a record of the type required
		@param expression the SQL (or other) expression to use for the search
	*/
	public void getRecords(LDBRecord record,String expression) throws LDBException;
	/***************************************************************************
		Check if more results are available.
		@param record the record that owns the result set
	*/
	public boolean hasMoreRecords(LDBRecord record) throws LDBException;
	/***************************************************************************
		Get the next record from the result set.
		@param record the record that owns the result set, which will be
		filled with the record data if the operation is successful
	*/
	public void getNextRecord(LDBRecord record) throws LDBException;
	/***************************************************************************
		Count the number of records that match a query.
		@param record a record of the type required
		@param query the SQL expression to use for the search
	*/
	public int count(LDBRecord record,String query) throws LDBException;
	/***************************************************************************
		Delete records of this type according to the expression provided.
		@param record a record of the type required
		@param expression the SQL expression to use for the search
	*/
	public void deleteRecords(LDBRecord record,String expression) throws LDBException;
	/***************************************************************************
		Issue an arbitrary SQL command.
		@param command the SQL command
	*/
	public void sql(String command) throws LDBException;
}
