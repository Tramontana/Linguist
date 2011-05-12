// LDBRecord.java

package net.eclecity.linguist.persist;

import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/******************************************************************************
	A user record.  This class does the following:<BR>
	1  Manage the fields of the record.<BR>
	2  Inform listeners of changes.<BR>
*/
public class LDBRecord extends LDBObject
{
	/***************************************************************************
		Construct an empty record.
	*/
	public LDBRecord()
	{
		super();
		created=accessed=modified=new Date();
	}

	/***************************************************************************
		Construct a new record.
		@param owner the owner of this record.
		@param tableName the name of the table that holds this record.
	*/
	public LDBRecord(LDBClient owner,String tableName)
	{
		this();
		if (owner!=null) addClient(this.owner=owner);
		this.tableName=tableName;
	}

	private transient Vector clients=new Vector();			// all clients
	private transient Object resultSet=null;

	private LDBClient owner;							// the client that has the lock
	private Hashtable fields=new Hashtable();		// the fields in this record
	private String tableName=null;					// the name of the table
	private Date created=null;							// the date this record was created
	private Date accessed=null;						// the date this record was last accessed
	private Date modified=null;						// the date this record was last modified
	private int status=SAFE;
	private boolean binaryStatus=false;				// true if a BINARY field has been used

	/***************************************************************************
		Set the table name of this record.
	*/
	public void setTableName(String name) { tableName=name; }

	/***************************************************************************
		Return the table name of this record.
	*/
	public String getTableName() { return tableName; }

	/***************************************************************************
		Set the owner of this record
	*/
	public void setOwner(LDBClient owner) { this.owner=owner; }

	/***************************************************************************
		Set the created date of this record
	*/
	public void setCreated(Date created) { this.created=created; }

	/***************************************************************************
		Set the accessed date of this record
	*/
	public void setAccessed(Date accessed) { this.accessed=accessed; }

	/***************************************************************************
		Set the modified date of this record
	*/
	public void setModified(Date modified) { this.modified=modified; }

	/***************************************************************************
		Set the status of this record
	*/
	public void setStatus(int status) { this.status=status; }
//	public void setStatus(int status)
//	{
//		this.status=status;
//		try
//		{
//			println("Setting the status of "+tableName+":"+getID()+" to "+status);
//			new Exception().printStackTrace();
//		}
//		catch (LDBException e) {}
//	}

	/***************************************************************************
		Set the binary status of this record
	*/
	public void setBinaryStatus(boolean binaryStatus) { this.binaryStatus=binaryStatus; }

	/***************************************************************************
		Set the ID of this record.
		@param id the unique ID for this record
	*/
	public void setID(Object id) throws LDBException { setValue(null,ID,id); }

	/***************************************************************************
		Get the ID of this record.
	*/
	public Object getID() { return getField(ID).getValue(); }

	/***************************************************************************
		Get the value that indicates BINARY fields have been used.
	*/
	public boolean getBinaryStatus() { return binaryStatus; }

	/***************************************************************************
		Get the status of this record.
	*/
	public int getStatus() { return status; }

	/***************************************************************************
		Set this record to have the status NEW.
	*/
	public void setNew() { setStatus(NEW); }

	/***************************************************************************
		Set this record to have the status SAFE.
		This marks all fields as unchanged.
	*/
	public void setSafe()
	{
		setStatus(SAFE);
		Enumeration en=fields.elements();
		while (en.hasMoreElements()) ((LDBField)en.nextElement()).setChanged(false);
		owner=null;
	}

	/***************************************************************************
		Add a field to the current record.
		@param fs a field specification for this record
		@param value the value contained by the record
	*/
	public void addField(LDBFieldSpec fs,Object value) throws LDBException
	{
		String name=fs.getName();
		if (name==null) throw new LDBNoNameException();
		if (fields.containsKey(name)) throw new LDBFieldExistsException();
		fields.put(name,new LDBField(fs,value));
		if (fs.getType()==LDBFieldSpec.BINARY) binaryStatus=true;
		modified=new Date();
	}

	/***************************************************************************
		Add a field to the current record.
		@param fs a field specification for this record
	*/
	public void addField(LDBFieldSpec fs) throws LDBException
	{
		addField(fs,null);
	}

	/***************************************************************************
		Put a field into the current record.
		@param field a field having the contents we want.
	*/
	public void putField(LDBField field)
	{
		fields.put(field.getName(),field.duplicate());
	}

	/***************************************************************************
		Duplicate this record.
	*/
	public LDBRecord duplicate()
	{
		LDBRecord record=new LDBRecord();
		Enumeration enumeration=fields.keys();
		while (enumeration.hasMoreElements())
		{
			LDBField field=getField((String)enumeration.nextElement());
			record.putField(field.duplicate());
		}
		record.setTableName(getTableName());
		record.setOwner(owner);
		record.setCreated(created);
		record.setAccessed(accessed);
		record.setModified(modified);
		record.setStatus(status);
		record.setBinaryStatus(binaryStatus);
		return record;
	}

	/***************************************************************************
		Lock this record.
		@param client The client that is requesting the lock
	*/
	public void lock(LDBClient client) throws LDBException
	{
		if (owner==null) owner=client;
		else if (owner!=client) throw new LDBLockException();
	}

	/***************************************************************************
		Set the value of a named field.
		IF there is no current owner, the client requesting the change becomes the owner.
		If there is an owner not the same as this client an exception is thrown.
		If there are any notification listeners, inform them of the change.
		Finally, update the record in the local cache.
		@param client the client requesting the change
		@param name the name of the field to change
		@param the new value object
	*/
	public void setValue(LDBClient client,String name,Object value)
		throws LDBException
	{
		checkOwner(client);
		checkNotDeleted();
		LDBField field=(LDBField)fields.get(name);
		if (field==null) throw new LDBNoFieldValueException(name);
		field.setValue(value);
		if (client!=null)
		{
			Enumeration en=clients.elements();
			while (en.hasMoreElements())
			{
				((LDBClient)en.nextElement()).notify(this,client);
			}
			if (status!=NEW) setStatus(CHANGED);
			LDBRecord record=duplicate();
			client.getTransaction().addToMyRecords(record);
			modified=new Date();
		}
	}

	/***************************************************************************
		Get the value of this field.
		@param name the name of the field whose value is wanted
	*/
	public Object getValue(String name) throws LDBException
	{
		checkNotDeleted();
		accessed=new Date();
		LDBField field=(LDBField)fields.get(name);
		return field.getValue();
	}

	/***************************************************************************
		Find out if a field is changed.
		@param name the name of the field to check
	*/
	public boolean isChanged(String name)
	{
		return ((LDBField)fields.get(name)).isChanged();
	}

	/***************************************************************************
		Return an array comprising all the field names for this record.
	*/
	public String[] getNames()
	{
		String[] names=new String[fields.size()];
		Enumeration en=fields.keys();
		int n=0;
		while (en.hasMoreElements()) names[n++]=(String)en.nextElement();
		return names;
	}

	/***************************************************************************
		Get the field specification for a field of this record.
		@param name the name of the field
	*/
	public LDBFieldSpec getFieldSpec(String name)
	{
		return getField(name).getFieldSpec();
	}

	/***************************************************************************
		Return a field object.
		@param the name of the field to return
	*/
	public LDBField getField(String name)
	{
		return (LDBField)fields.get(name);
	}

	/***************************************************************************
		Get all the current values of the fields in this record.
		Return them as Strings in a Hashtable.
	*/
	public Hashtable getFields() throws LDBException
	{
		Hashtable table=new Hashtable();
		Enumeration enumeration=fields.keys();
		while (enumeration.hasMoreElements())
		{
			String name=(String)enumeration.nextElement();
			Object value=getValue(name);
			if (value!=null) table.put(name,value);
		}
		return table;
	}

	/***************************************************************************
		Convert this record to XML and return it as a string.
	*/
	public String getXML()
	{
		StringBuffer sb=new StringBuffer();
		sb.append("\t");
		sb.append("<record table=\"");
		sb.append(tableName);
		sb.append("\">\n");
		Enumeration enumeration=fields.keys();
		while (enumeration.hasMoreElements())
		{
			String name=(String)enumeration.nextElement();
			sb.append("\t");
			sb.append("<field name=\"");
			sb.append(name);
			sb.append("\" value=\"");
			sb.append(((LDBField)fields.get(name)).getValue());
			sb.append("\"/>\n");
		}
		sb.append("\t");
		sb.append("</record>\n");
		return sb.toString();
	}

	/***************************************************************************
		Set the value of a named field from a JDBC ResultSet.
		@param name the name of the field
		@param value the value to set
	*/
	public void setFieldValue(String name,java.sql.ResultSet rs) throws LDBException
	{
		LDBField field=getField(name);
		field.setFieldValue(rs);
		if (field.getFieldSpec().getType()==LDBFieldSpec.BINARY) binaryStatus=true;
	}

	/***************************************************************************
		Return the value of a named field as a string.
		@param name the name of the field whose value is wanted
	*/
	public String getStringValue(String name) throws LDBException
	{
		return getStringValue(name,false);
	}

	/***************************************************************************
		Return the value of a named field as a string.
		@param name the name of the field whose value is wanted
		@param quote true if CHAR values should be returned quoted
	*/
	public String getStringValue(String name,boolean quote) throws LDBException
	{
		LDBField field=(LDBField)fields.get(name);
		if (field==null) throw new LDBUnknownFieldException();
		return field.getStringValue(quote);
	}

	/***************************************************************************
		Save the current record.  This is one point at which the record
		enters the local cache.  If it's not already in the cache,
		a search is made in the database.  If a matching record is found,
		this record must not be marked as NEW or an exception will result.
		@param client the client requesting the save
	*/
	public synchronized void save(LDBClient client) throws LDBException
	{
		client.getTransaction().save(this);
	}

	/***************************************************************************
		Cache a record.  This is another point at which the record
		enters the local cache.
		@param client the client requesting the resave
	*/
	public synchronized void cache(LDBClient client) throws LDBException
	{
		client.getTransaction().cache(this);
	}

	/***************************************************************************
		Restore the current record.
		@param client the client requesting the restore
	*/
	public synchronized LDBRecord restore(LDBClient client) throws LDBException
	{
		LDBRecord record=client.getTransaction().restore(this);
		record.addClient(client);
		return record;
	}

	/***************************************************************************
		Update (freshen) this record in the database.
		@param client the client requesting the delete.
		This must be the current owner.
	*/
	public synchronized void update(LDBClient client) throws LDBException
	{
		checkOwner(client);
		LDBTransaction transaction=client.getTransaction();
		transaction.delete(this);
		transaction.save(this);
		setStatus(NEW);
	}

	/***************************************************************************
		Delete this record from the database.
		@param client the client requesting the delete.
		This must be the current owner.
	*/
	public synchronized void delete(LDBClient client) throws LDBException
	{
		checkOwner(client);
//		client.getTransaction().delete(this);
		client.getTransaction().cache(this);
		modified=new Date();
		setStatus(DELETED);
	}

	/***************************************************************************
		Delete records of this type according to the expression provided.
		@param client the client making the request
		@param expression the expression to use for the search
	*/
	public synchronized void deleteRecords(LDBClient client,String expression)
		throws LDBException
	{
		client.getTransaction().deleteRecords(this,expression);
	}

	/***************************************************************************
		Check if this record exists in the database.
		@param client the client making the request
	*/
	public synchronized boolean exists(LDBClient client) throws LDBException
	{
		return client.getTransaction().exists(this);
	}

	/***************************************************************************
		Get records of this type that match the field list provided.
		@param client the client making the request
		@param fields the field list
	*/
	public synchronized void getRecords(LDBClient client,Hashtable fields)
		throws LDBException
	{
		client.getTransaction().getRecords(this,fields);
	}

	/***************************************************************************
		Get records of this type according to the expression provided.
		@param client the client making the request
		@param expression the SQL expression to use for the search
	*/
	public synchronized void getRecords(LDBClient client,String expression)
		throws LDBException
	{
		client.getTransaction().getRecords(this,expression);
	}

	/***************************************************************************
		Get all records of this type.
		@param client the client making the request
	*/
	public synchronized void getRecords(LDBClient client) throws LDBException
	{
		getRecords(client,"");
	}

	/***************************************************************************
		Check if more results are available.
		@param client the client making the request
	*/
	public synchronized boolean hasMoreRecords(LDBClient client)
		throws LDBException
	{
		return client.getTransaction().hasMoreRecords(this);
	}

	/***************************************************************************
		Get the next record from the result set.
		@param client the client making the request
	*/
	public synchronized void getNextRecord(LDBClient client)
		throws LDBException
	{
		client.getTransaction().getNextRecord(this);
	}

	/***************************************************************************
		Count the number of records that match a query.
		@param client the client making the request
		@param query the SQL query string
	*/
	public synchronized int count(LDBClient client,String query)
		throws LDBException
	{
		return client.getTransaction().count(this,query);
	}

	/***************************************************************************
		Save a result set object.
		The set is saved as an Object in order to preserve independence
		from any particular type of database handler.
		@param rs the result set
	*/
	public void setResultSet(Object rs)
	{
		resultSet=rs;
	}

	/***************************************************************************
		Return the result set object.
	*/
	public Object getResultSet()
	{
		return resultSet;
	}

	/***************************************************************************
		Add a client to this record's client list.
		@param client the client to add
	*/
	public void addClient(LDBClient client)
	{
		if (!clients.contains(client)) clients.addElement(client);
	}

	/***************************************************************************
		Remove a client from this record's client list.
		If this is the last client, return true so that
		this record can be removed from the transaction list.
		@param client the client to remove
	*/
	public boolean removeClient(LDBClient client)
	{
		if (owner==client) owner=null;
		if (clients.contains(client)) clients.removeElement(client);
		if (clients.isEmpty())
		{
			owner=null;
			return true;
		}
		return false;
	}

	/***************************************************************************
		Remove all client from this record's client list.
	*/
	public void removeAllClients()
	{
		owner=null;
		clients.removeAllElements();
	}

	/***************************************************************************
		Create a new database table for this record type.
		@param client any client of this record
		@param delete true if the old table is to be deleted
	*/
	public void createNewTable(LDBClient client,boolean delete) throws LDBException
	{
		client.getTransaction().createTable(this,delete);
	}

	/** The name of the ID field. */
	public static final String ID="id";

	/** The code that indicates a record is unchanged since the last save. */
	public static final int SAFE=0;
	/** The code that indicates a record is new and has not yet been saved. */
	public static final int NEW=1;
	/** The code that indicates a record has been changed since the last save. */
	public static final int CHANGED=2;
	/** The code that indicates a record has been deleted. */
	public static final int DELETED=3;

	////////////////////////////////////////////////////////////////////////////
	// Private methods.

	/***************************************************************************
		Check that the owner of this record is the one making the request.
		@param client the client making the request
	*/
	private void checkOwner(LDBClient client) throws LDBException
	{
		if (client!=null)
		{
			if (owner==null) owner=client;
			else if (owner!=client) throw new LDBLockException();
		}
	}

	/***************************************************************************
		Check that this record has not been deleted.
	*/
	private void checkNotDeleted() throws LDBException
	{
		if (status==DELETED) throw new LDBRecordDeletedException();
	}
}
