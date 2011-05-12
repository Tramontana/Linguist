// LDBSQL.java

package net.eclecity.linguist.persist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

/******************************************************************************
	A basic driver for an SQL database.
	Subclass this to implement a particular driver.
	In most cases, only connect() will need to be implemented.
*/
public abstract class LDBSQL extends LDBObject implements LDBPersistent
{
	protected static Connection connection=null;

	/***************************************************************************
		Default constructor.
	*/
	public LDBSQL() {}

	/***************************************************************************
		Connect to the database.
		Implement this method in your driver class, to connect to the database.
		@param url the URL to connect to
	*/
	public abstract void connect(String url) throws LDBException;

	/***************************************************************************
		Connect to the database.
		@param driver the full classpath of the JDBC driver
		@param url the URL to connect to
	*/
	public void connect(String driver,String url) throws LDBException
	{
		if (connection==null)		// only do this once
		{
			try
			{
				Class.forName(driver);
				connection=DriverManager.getConnection(url);
				connection.setAutoCommit(false);
				println("Database connected.");
			}
			catch( SQLException e )
			{
				throw new LDBException("Failed to connect to database: "+e.getMessage());
			}
			catch( ClassNotFoundException e )
			{
	   		throw new LDBException("Unable to find driver class."+e.getMessage());
			}
		}
	}

	/***************************************************************************
		Disconnect from the database.
	*/
	public void disconnect()
	{
		try
		{
			connection.close();
			println("Database disconnected.");
		}
		catch( SQLException e )
		{
			println("Error closing connection: " + e.getMessage());
			System.exit(0);
		}
	}

	/***************************************************************************
		Commit all outstanding transactions to the database.
	*/
	public void commit() throws LDBException
	{
		try { connection.commit(); }
		catch (SQLException e) { throw new LDBException(e); }
	}

	/***************************************************************************
		Create a table using the field specifications in the record given.
		@param record a record containing the required fields
		@param delete true if the old table is to be deleted
	*/
	public void createTable(LDBRecord record,boolean delete) throws LDBException
	{
		if (delete) deleteTable(record);
//		println("Create a new database table.");
		String table=record.getTableName();
		executeUpdate(getCreateTableSQL(table,record));
		commit();
	}

	/***************************************************************************
		Delete a table using the table name in the record given.
		@param record a record containing the required name
	*/
	public void deleteTable(LDBRecord record) throws LDBException
	{
//		println("Delete a database table.");
		String table=record.getTableName();
		executeUpdate(getDeleteTableSQL(table));
		commit();
	}

	/***************************************************************************
		Insert a record into a table.  Used by the XML importer.
		@param tableName the name of the table.
		@param table a table of name/value pairs.
	*/
	public void insert(String tableName,Properties table) throws LDBException
	{
		executeUpdate(getInsertSQL(tableName,table));
	}

	/***************************************************************************
		Insert a record into a table.
		@param record the record to insert
	*/
	public void insert(LDBRecord record) throws LDBException
	{
		// If the record has one or more BINARY fields, use a PreparedStatement
		// to save the data.  Otherwise, use a regular SQL command.
		if (record.getBinaryStatus())
		{
			try
			{
				PreparedStatement statement=connection.prepareStatement(getInsertSQL(record));
				getFields(statement,record);
				statement.execute();
			}
			catch (SQLException e) { throw new LDBException(e); }
		}
		else executeUpdate(getInsertSQL(record));
	}

	/***************************************************************************
		Update a record.
		Only update fields that have changed.
		@param record the record to update
	*/
	public void update(LDBRecord record) throws LDBException
	{
		// If the record has one or more BINARY fields, use a PreparedStatement
		// to save the data.  Otherwise, use a regular SQL command.
		if (record.getBinaryStatus())
		{
			try
			{
				PreparedStatement statement=connection.prepareStatement(getUpdateSQL(record));
				getFields(statement,record);
				statement.execute();
			}
			catch (SQLException e) { throw new LDBException(e); }
		}
		else
		{
			String updateSQL=getUpdateSQL(record);
			if (updateSQL!=null) executeUpdate(updateSQL);
		}
	}

	/***************************************************************************
		Fill up a PreparedStatement with the fields from this record.
		@param statement the statement to fill
		@param record the record whose values are to be used
	*/
	private void getFields(PreparedStatement statement,LDBRecord record)
		throws SQLException, LDBException
	{
		String[] names=record.getNames();
		for (int n=0; n<names.length; n++)
		{
			Object data=record.getValue(names[n]);
			switch (record.getField(names[n]).getFieldSpec().getType())
			{
				case LDBFieldSpec.BYTE:
					if (data==null) data=new Byte((byte)0);
					statement.setByte(n+1,((Byte)data).byteValue());
					break;
				case LDBFieldSpec.INT:
					if (data==null) data=new Integer(0);
					statement.setInt(n+1,((Integer)data).intValue());
					break;
				case LDBFieldSpec.LONG:
					if (data==null) data=new Long(0);
					statement.setLong(n+1,((Long)data).longValue());
					break;
				case LDBFieldSpec.CHAR:
					if (data==null) data="";
					statement.setString(n+1,(String)data);
					break;
				case LDBFieldSpec.BINARY:
					if (data==null) data="";
					statement.setObject(n+1,data);
					break;
			}
		}
	}

	/***************************************************************************
		Delete a record from the database.
		@param record the record to delete.  This object must contain
		at least the ID of the record.
	*/
	public void delete(LDBRecord record) throws LDBException
	{
		executeUpdate(getDeleteSQL(record));
	}

	/***************************************************************************
		Perform an SQL update on a table.
		@param sql the SQL command string to use
	*/
	private void executeUpdate(String sql) throws LDBException
	{
		try
		{
			Statement s=connection.createStatement();
			s.executeUpdate(sql);
			s.close();
		}
		catch (SQLException e) { throw new LDBException(e); }
	}

	/***************************************************************************
		Restore a record.
		Find a record having the ID in the given record, and fill in
		all the fields of the record using data retrieved from the database.
		@param record a record containing a complete set of fields,
		of which only the ID field need have a value.  This ID is used
		to search the database.
	*/
	public void restore(LDBRecord record) throws LDBException
	{
		String sql=getRestoreSQL(record);
		try
		{
			Statement s=connection.createStatement();
			ResultSet rs=s.executeQuery(sql);
			if (!rs.next()) throw new LDBNoRecordsException();
			getFields(record,rs);
			s.close();
		}
		catch (SQLException e) {throw new LDBException(e); }
	}

	/***************************************************************************
		Test if a record exists.
		@param record the record to test for
	*/
	public boolean exists(LDBRecord record) throws LDBException
	{
		boolean result=false;
		String sql=getExistsSQL(record);
		try
		{
			Statement s=connection.createStatement();
			ResultSet rs=s.executeQuery(sql);
			result=rs.next();
			s.close();
		}
		catch (SQLException e) { throw new LDBException(e); }
		return result;
	}

	/***************************************************************************
		Get records that match the field list provided.
		@param record a record in which to return a result set
		@param fields the field list
	*/
	public void getRecords(LDBRecord record,Hashtable fields) throws LDBException
	{
		String query="WHERE ";
		Enumeration enumeration=fields.keys();
		while (enumeration.hasMoreElements())
		{
			String name=(String)enumeration.nextElement();
			query+=(name+"=");
			LDBField field=record.getField(name);
			LDBFieldSpec fieldSpec=field.getFieldSpec();
			String data=(String)fields.get(name);
			switch (fieldSpec.getType())
			{
			case LDBFieldSpec.BYTE:
			case LDBFieldSpec.INT:
			case LDBFieldSpec.LONG:
				query+=data;
				break;
			case LDBFieldSpec.CHAR:
				query+=("'"+data+"'");
				break;
			case LDBFieldSpec.BINARY:
				throw new LDBInappropriateTypeException(name);
			}
			if (enumeration.hasMoreElements()) query+=" AND ";
		}
		getRecords(record,query);
	}

	/***************************************************************************
		Get records of this type according to the expression provided.
		@param record a record of the type required
		@param expression the SQL expression to use for the search
	*/
	public void getRecords(LDBRecord record,String expression) throws LDBException
	{
		String sql=getSelectSQL(record,expression);
		try
		{
			Statement s=connection.createStatement();
			record.setResultSet(s.executeQuery(sql));
			s.close();
		}
		catch (SQLException e) { throw new LDBException(e); }
	}

	/***************************************************************************
		Check if more results are available.
		@param record the record that owns the result set
	*/
	public boolean hasMoreRecords(LDBRecord record) throws LDBException
	{
		ResultSet resultSet=(ResultSet)record.getResultSet();
		if (resultSet==null) return false;
		try
		{
			boolean result=resultSet.next();
			if (!result) record.setResultSet(null);
			return result;
		}
		catch (SQLException e) { throw new LDBException(e); }
	}

	/***************************************************************************
		Get the next record from the result set.
		@param record the record that owns the result set
	*/
	public void getNextRecord(LDBRecord record) throws LDBException
	{
		ResultSet resultSet=(ResultSet)record.getResultSet();
		if (resultSet==null) throw new LDBNoRecordsException();
		getFields(record,resultSet);
	}

	/***************************************************************************
		Restore the fields of a record from the result set.
		@param record the record to restore
		@param rs the result set, already positioned
	*/
	private void getFields(LDBRecord record,ResultSet rs) throws LDBException
	{
		String[] names=record.getNames();
		// Restore the fields one by one.
		for (int n=0; n<names.length; n++) record.setFieldValue(names[n],rs);
	}

	/***************************************************************************
		Count the number of records that match a query.
		@param record a record of the type required
		@param query the SQL expression to use for the search
	*/
	public int count(LDBRecord record,String query) throws LDBException
	{
		String sql=getCountSQL(record,query);
		try
		{
			Statement s=connection.createStatement();
			s.executeQuery(sql);
			s.close();
			return 0;
		}
		catch (SQLException e) { throw new LDBException(e); }
	}

	/***************************************************************************
		Delete records of this type according to the expression provided.
		@param record a record of the type required
		@param expression the SQL expression to use for the search
	*/
	public void deleteRecords(LDBRecord record,String expression) throws LDBException
	{
		String sql=getDeleteSQL(record,expression);
		try
		{
			Statement s=connection.createStatement();
			s.executeUpdate(sql);
			s.close();
		}
		catch (SQLException e) { throw new LDBException(e); }
	}

	/***************************************************************************
		Issue an arbitrary SQL command.
		@param command the SQL command
	*/
	public void sql(String command) throws LDBException
	{
		try
		{
			Statement s=connection.createStatement();
			s.executeUpdate(command);
			s.close();
			connection.commit();
		}
		catch (SQLException e) { throw new LDBException(e); }
	}

	/***************************************************************************
		Get the SQL command to insert a new record.
	*/
	protected String getInsertSQL(String tableName,Properties fields)
	{
		String s="INSERT INTO "+tableName+"(";
		String[] names=new String[fields.size()];
		Enumeration enumeration=fields.keys();
		int n;
		for (n=0; n<names.length; n++)
		{
			names[n]=(String)enumeration.nextElement();
			s+=names[n];
			if (n<names.length-1) s+=",";
		}
		s+=") VALUES (";
		for (n=0; n<names.length; n++)
		{
			if (n>0) s+=",";
			s+=("\""+fields.getProperty(names[n])+"\"");
		}
		s+=")";
		return s;
	}

	/***************************************************************************
		Get the SQL command to insert a new record.
	*/
	protected String getInsertSQL(LDBRecord record) throws LDBException
	{
		StringBuffer sb=new StringBuffer("INSERT INTO "+record.getTableName()+"(");
		String[] names=record.getNames();
		int n;
		for (n=0; n<names.length; n++)
		{
			sb.append(names[n]);
			if (n<names.length-1) sb.append(",");
		}
		sb.append(") VALUES (");
		for (n=0; n<names.length; n++)
		{
			if (n>0) sb.append(",");
			if (record.getBinaryStatus()) sb.append("?");
			else sb.append(record.getStringValue(names[n],true));
		}
		sb.append(")");
		return sb.toString();
	}

	/***************************************************************************
		Get the SQL command to update a record.
	*/
	protected String getUpdateSQL(LDBRecord record) throws LDBException
	{
		StringBuffer sb=new StringBuffer("UPDATE "+record.getTableName()+" SET ");
		boolean flag=false;
		boolean changedFlag=false;
		String[] names=record.getNames();
		for (int n=0; n<names.length; n++)
		{
			String name=names[n];
			if (record.getBinaryStatus())
			{
				if (flag) sb.append(",");
				sb.append(names[n]+"=?");
				flag=true;
				changedFlag=true;
			}
			else if (record.isChanged(name))
			{
				if (flag) sb.append(",");
				// If not binary, only generate SQL for fields that changed.
				sb.append(names[n]+"="+record.getStringValue(name,true));
				flag=true;
				changedFlag=true;
			}
		}
		if (!changedFlag) return null;
		sb.append(" WHERE "+LDBRecord.ID+"='"+record.getID()+"'");
		return sb.toString();
	}

	/***************************************************************************
		Get the SQL command to restore a record using its ID.
	*/
	protected String getRestoreSQL(LDBRecord record)
	{
		return "SELECT * FROM "+record.getTableName()
			+" WHERE "+LDBRecord.ID+"="+record.getID();
	}

	/***************************************************************************
		Get the SQL command to search for records matching the expression provided.
	*/
	protected String getSelectSQL(LDBRecord record,String expression)
	{
		String s="SELECT * FROM "+record.getTableName();
		if (expression!=null) s+=(" "+expression);
		return s;
	}

	/***************************************************************************
		Get the SQL command to count the number of records matching the expression provided.
	*/
	protected String getCountSQL(LDBRecord record,String expression)
	{
		return "SELECT COUNT("+expression+") FROM "+record.getTableName();
	}

	/***************************************************************************
		Get the SQL command to delete a record given its ID.
	*/
	protected String getDeleteSQL(LDBRecord record)
	{
		return "DELETE FROM "+record.getTableName()
			+" WHERE "+LDBRecord.ID+"='"+record.getID()+"'";
	}

	/***************************************************************************
		Get the SQL command to delete records matching the expression provided.
		WARNING: THIS COMMAND IS DANGEROUS!
	*/
	protected String getDeleteSQL(LDBRecord record,String expression)
	{
		String s="DELETE FROM "+record.getTableName();
		if (expression!=null) s+=(" "+expression);
		return s;
	}

	/***************************************************************************
		Get the SQL command to check if a record exists.
	*/
	protected String getExistsSQL(LDBRecord record)
	{
		return "SELECT id FROM "+record.getTableName()
			+" WHERE "+LDBRecord.ID+"='"+record.getID()+"'";
	}

	/***************************************************************************
		Get the SQL command to delete a table.
	*/
	protected String getDeleteTableSQL(String table)
	{
		return "DROP TABLE "+table;
	}

	/***************************************************************************
		Get the SQL command to create a table.
	*/
	protected String getCreateTableSQL(String table,LDBRecord record) throws LDBException
	{
		String s="CREATE TABLE "+table+"(";
		String[] names=record.getNames();
		int n;
		for (n=0; n<names.length; n++)
		{
			if (n>0) s+=",";
			String name=names[n];
			s+=(name+" ");
			LDBFieldSpec fieldSpec=record.getFieldSpec(name);
			switch (fieldSpec.getType())
			{
			case LDBFieldSpec.BYTE:
				s+="BYTE";
				break;
			case LDBFieldSpec.INT:
				s+="INT";
				break;
			case LDBFieldSpec.LONG:
				s+="LONG";
				break;
			case LDBFieldSpec.CHAR:
				s+=("CHAR("+fieldSpec.getSize()+")");
				break;
			case LDBFieldSpec.BINARY:
				s+="BINARY";
				break;
			default:
				throw new LDBUnknownFieldTypeException();
			}
			if (name.equals(LDBRecord.ID)) s+=" PRIMARY KEY";
		}
		s+=")";
		return s;
	}
}
