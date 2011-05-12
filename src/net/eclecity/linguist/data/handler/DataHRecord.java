// DataHRecord.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.data.handler;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

import net.eclecity.linguist.data.runtime.DataRBackground;
import net.eclecity.linguist.data.runtime.DataRMessages;
import net.eclecity.linguist.handler.LHData;
import net.eclecity.linguist.handler.LHStringHolder;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.persist.LDBException;
import net.eclecity.linguist.persist.LDBFieldSpec;
import net.eclecity.linguist.persist.LDBReader;
import net.eclecity.linguist.persist.LDBRecord;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.util.LUEncrypt;
import net.eclecity.linguist.util.LUUtil;
import net.eclecity.linguist.value.LVObject;
import net.eclecity.linguist.value.LVStringConstant;
import net.eclecity.linguist.value.LVValue;

import org.xml.sax.SAXException;


/******************************************************************************
	A generic database record.
	<pre>
	[1.001 GT]  04/10/00  Pre-existing.
	</pre>
*/
public class DataHRecord extends LHVariableHandler
{
	private DataRBackground background=null;

	public DataHRecord() {}

	public Object newElement(Object extra) { return new DataHRecordData(); }

	/***************************************************************************
		Tell callers this type doesn't return a value.
	*/
	public boolean hasValue() { return false; }
	
	/***************************************************************************
		Get the background object.
	*/
	public void getBackground() throws LRException
	{
		if (background==null) background=(DataRBackground)getBackground("data");
	}

	/***************************************************************************
		Clear this record.
		This removes the reference to the underlying record object
		but preserves the identity of the field spec.
	*/
	public void clear() throws LRException
	{
		DataHRecordData myData=(DataHRecordData)getData();
		myData.record=null;
	}

	/***************************************************************************
		Set the specification of this record.
	*/
	public void setSpecification(DataHSpecification spec) throws LRException
	{
		DataHRecordData myData=(DataHRecordData)getData();
		myData.spec=spec;
	}

	/***************************************************************************
		Set the encryption key of this record.
	*/
	public void setEncryptionKey(LVValue encryptKey) throws LRException
	{
		DataHRecordData myData=(DataHRecordData)getData();
		myData.encryptKey=encryptKey;
	}

	/***************************************************************************
		Create a new table for this type of record.
	*/
	public void createTable() throws LRException
	{
		getBackground();
		DataHRecordData myData=(DataHRecordData)getData();
		// Create a new (empty) record
		create(new Hashtable(),myData);
		if (myData.record==null) throw new LRException(DataRMessages.nullRecord(getName()));
		try
		{
			background.newTransaction();
			myData.record.createNewTable(background.getClient(),true);
		}
		catch (LDBException e) { setStatus(e); }
	}

	/***************************************************************************
		Check if this record exists in the database.
	*/
	public boolean exists() throws LRException
	{
		DataHRecordData myData=(DataHRecordData)getData();
		getBackground();
		try { return myData.record.exists(background.getClient()); }
		catch (LDBException e) { setStatus(e); }
		return false;
	}

	/***************************************************************************
		Insert the current record into the database.
	*/
	public void insert() throws LRException
	{
		DataHRecordData myData=(DataHRecordData)getData();
		getBackground();
		try { myData.record.save(background.getClient()); }
		catch (LDBException e) { setStatus(e); }
	}

	/***************************************************************************
		Create a new record using the fields given,
		then save it to the database.
	*/
	public void create(Hashtable fieldValues) throws LRException
	{
		DataHRecordData myData=(DataHRecordData)getData();
		create(fieldValues,myData);
		getBackground();
		try { myData.record.save(background.getClient()); }
		catch (LDBException e) { setStatus(e); }
	}

	/***************************************************************************
		Create a new record using the fields given.
	*/
	private void create(Hashtable fieldValues,DataHRecordData myData) throws LRException
	{
		// First, to permit searching for a field name
		// the passed table must be converted to change the
		// keys from LVValues to Strings.
		Hashtable ht=new Hashtable();
		Enumeration en=fieldValues.keys();
		while (en.hasMoreElements())
		{
			LVValue v=(LVValue)en.nextElement();
			ht.put(v.getStringValue(),fieldValues.get(v));
		}
		fieldValues=ht;
		getBackground();
		DataHSpecification spec=myData.spec;
		if (spec==null) throw new LRException(DataRMessages.hasNoSpec(getName()));
		myData.record=createRecord(spec,fieldValues);
	}

	/***************************************************************************
		Create a record with the given specification.
		Optionally fill its fields from the Hashtable given.
	*/
	private LDBRecord createRecord(DataHSpecification spec,Hashtable fieldValues)
		throws LRException
	{
		clearStatus();
		if (fieldValues==null) fieldValues=new Hashtable();
		// Create a new empty record
		LDBRecord record=new LDBRecord(background.getClient(),spec.getTableName());
		// Get the fields of the specification
		// and fill the record from the values passed in.
		// If a field is not identified create one with a default value.
		Hashtable fields=spec.getFields();
		Enumeration en=fields.keys();
		while (en.hasMoreElements())
		{
			// Get the field name from the spec
			String name=(String)en.nextElement();
			// Get the field spec for that name
			LDBFieldSpec fieldSpec=(LDBFieldSpec)fields.get(name);
			// See if we have a value for this field.
			// Remember that the keys of fieldValues are LVValues.
			Object value=(LVValue)fieldValues.get(name);
			if (value!=null)
			{
				String obj=((LVValue)value).getStringValue();
				switch (fieldSpec.getType())
				{
				case LDBFieldSpec.BYTE:
					value=new Byte((byte)LUUtil.getInt(obj));
					break;
				case LDBFieldSpec.INT:
					value=new Integer(LUUtil.getInt(obj));
					break;
				case LDBFieldSpec.LONG:
					value=new Long(LUUtil.getLong(obj));
					break;
				case LDBFieldSpec.CHAR:
					value=obj;
					break;
				case LDBFieldSpec.BINARY:
					value=obj;
					break;
				}
			}
			else
			{
				// No value given so use a sensible default
				// depending on the type of the field.
				switch (fieldSpec.getType())
				{
				case LDBFieldSpec.BYTE:
					value=new Byte((byte)0);
					break;
				case LDBFieldSpec.INT:
					value=new Integer(0);
					break;
				case LDBFieldSpec.LONG:
					value=new Long(0);
					break;
				case LDBFieldSpec.CHAR:
					value="";
					break;
				case LDBFieldSpec.BINARY:
					value="";
					break;
				}
			}
			// Add the new field to the record
			try
			{
				record.addField(fieldSpec,value);
			}
			catch (LDBException e) { setStatus(e); }
		}
		return record;
	}

	/***************************************************************************
		Set the value of a field.
	*/
	public void setFieldValue(LVValue name,LVValue value) throws LRException
	{
		DataHRecordData myData=(DataHRecordData)getData();
		try
		{
			clearStatus();
			LDBRecord record=myData.record;
			if (record!=null)
			{
				String fieldName=name.getStringValue();
				Object nValue=new Object();
				switch (record.getFieldSpec(fieldName).getType())
				{
					case LDBFieldSpec.BYTE:
						nValue=new Byte((byte)value.getIntegerValue());
						break;
					case LDBFieldSpec.INT:
						nValue=new Integer(value.getIntegerValue());
						break;
					case LDBFieldSpec.LONG:
						nValue=new Long(value.getNumericValue());
						break;
					case LDBFieldSpec.CHAR:
						nValue=value.getStringValue();
						break;
					case LDBFieldSpec.BINARY:
						nValue=value.getBinaryValue();
						break;
				}
				record.setValue(background.getClient(),fieldName,nValue);
			}
		}
		catch (LDBException e) { setStatus(e); }
	}

	/***************************************************************************
		Get the value of a field.
	*/
	public String getFieldValue(LVValue name) throws LRException
	{
		DataHRecordData myData=(DataHRecordData)getData();
		try
		{
			clearStatus();
			LDBRecord record=myData.record;
			if (record!=null) return record.getStringValue(name.getStringValue());
		}
		catch (LDBException e) { setStatus(e); }
		return "";
	}

	/***************************************************************************
		Set the status of this record.
	*/
	protected void setStatus(LDBException e) throws LRException
	{
		getBackground();
		background.setStatus(e);
	}

	/***************************************************************************
		Clear the status of this record.
	*/
	protected void clearStatus() throws LRException
	{
		getBackground();
		background.clearStatus();
	}

	/***************************************************************************
		Get the status of this record.
	*/
	public String getStatus() throws LRException
	{
		getBackground();
		return background.getStatus();
	}

	/***************************************************************************
		Get all the current field values from this record as a Hashtable.
		If there is no underlying record, return empty values based on the
		contents of the field specification.
	*/
	public Hashtable getFields() throws LRException
	{
		DataHRecordData myData=(DataHRecordData)getData();
		if (myData.record!=null)
		{
			Hashtable table;
			try
			{
				table=myData.record.getFields();
				Hashtable fields=new Hashtable();
				Enumeration enumeration=table.keys();
				while (enumeration.hasMoreElements())
				{
					String name=(String)enumeration.nextElement();
					fields.put(name,new LVObject(table.get(name)));
				}
				return fields;
			}
			catch (LDBException e) { setStatus(e); }
		}
		else if (myData.spec!=null)
		{
			Hashtable table=new Hashtable();
			Enumeration enumeration=myData.spec.getFields().keys();
			while (enumeration.hasMoreElements())
				table.put(enumeration.nextElement(),new LVObject());
			return table;
		}
		return new Hashtable();
	}

	/***************************************************************************
		Get something.
	*/
	public void get(int opcode,LVValue value) throws LRException
	{
		switch (opcode)
		{
			case GET_ID:
				getRecord(value);
				break;
			case GET_USING:
				getRecords(value);
				break;
		}
	}

	/***************************************************************************
		Get the record having the given ID value.
	*/
	public void getRecord(LVValue value) throws LRException
	{
		DataHRecordData myData=(DataHRecordData)getData();
		getBackground();
		myData.record=createRecord(myData.spec,null);
		try
		{
			if (value.isNumeric())
				myData.record.setID(new Long(value.getNumericValue()));
			else
				myData.record.setID(value.getStringValue());
			myData.record=myData.record.restore(background.getClient());
		}
		catch (LDBException e) { setStatus(e); }
	}

	/***************************************************************************
		Get one or more records using the field table provided.
		@param fields the field list to use in the search
	*/
	public void getRecords(Hashtable fields) throws LRException
	{
		DataHRecordData myData=(DataHRecordData)getData();
		getBackground();
		myData.record=createRecord(myData.spec,null);
		try
		{
			myData.record.getRecords(background.getClient(),fields);
		}
		catch (LDBException e) { setStatus(e); }
	}

	/***************************************************************************
		Get one or more records using the SQL query provided.
		@param query an SQL query to use in the search
	*/
	public void getRecords(LVValue query) throws LRException
	{
		DataHRecordData myData=(DataHRecordData)getData();
		getBackground();
		myData.record=createRecord(myData.spec,null);
		try
		{
			String s=null;
			if (query!=null) s=query.getStringValue();
			myData.record.getRecords(background.getClient(),s);
		}
		catch (LDBException e) { setStatus(e); }
	}

	/***************************************************************************
		Convert this record or all records in this table to XML.
	*/
	public String getXML(boolean allRecords) throws LRException
	{
		StringBuffer sb=new StringBuffer();
		DataHRecordData myData=(DataHRecordData)getData();
		if (allRecords)
		{
			String tableName=myData.spec.getTableName();
			sb.append("<"+tableName+">\n");
			getRecords((LVValue)null);
			while (hasMoreRecords())
			{
				getNextRecord();
				sb.append(myData.record.getXML());
			}
			sb.append("</"+tableName+">\n");
			return sb.toString();
		}
		return myData.record.getXML();
	}

	/***************************************************************************
		Read all records found in the specified XML file.
		@param fileName the name of the XML file.
	*/
	public void read(LVValue fileName) throws LRException
	{
		try
		{
			new LDBReader(background.getClient(),fileName.getStringValue());
		}
		catch (SAXException e) { setStatus(new LDBException(e)); }
		catch (IOException e) { setStatus(new LDBException(e)); }
		catch (LDBException e) { setStatus(e); }
	}

	/***************************************************************************
		Report if there are more records available.
	*/
	public boolean hasMoreRecords() throws LRException
	{
		DataHRecordData myData=(DataHRecordData)getData();
		getBackground();
		try
		{
			return myData.record.hasMoreRecords(background.getClient());
		}
		catch (LDBException e) { setStatus(e); }
		return false;
	}

	/***************************************************************************
		Get the next record in the current result set.
	*/
	public void getNextRecord() throws LRException
	{
		DataHRecordData myData=(DataHRecordData)getData();
		getBackground();
		try
		{
			myData.record.getNextRecord(background.getClient());
		}
		catch (LDBException e) { setStatus(e); }
	}

	/***************************************************************************
		Count the number of records matching a query.
	*/
	public int count(LVValue query) throws LRException
	{
		DataHRecordData myData=(DataHRecordData)getData();
		getBackground();
		try
		{
			return myData.record.count(background.getClient(),query.getStringValue());
		}
		catch (LDBException e) { setStatus(e); }
		return 0;
	}

	/***************************************************************************
		Update the current record.
	*/
	public void updateRecord() throws LRException
	{
		DataHRecordData myData=(DataHRecordData)getData();
		getBackground();
		try
		{
			myData.record.update(background.getClient());
		}
		catch (LDBException e) { setStatus(e); }
	}

	/***************************************************************************
		Delete the current record.
	*/
	public void deleteRecord() throws LRException
	{
		DataHRecordData myData=(DataHRecordData)getData();
		getBackground();
		try
		{
			myData.record.delete(background.getClient());
		}
		catch (LDBException e) { setStatus(e); }
	}

	/***************************************************************************
		Delete the record having the given ID.
	*/
	public void deleteRecord(LVValue value) throws LRException
	{
		DataHRecordData myData=(DataHRecordData)getData();
		getBackground();
		myData.record=createRecord(myData.spec,null);
		try
		{
			myData.record.setID(value.getStringValue());
			myData.record.delete(background.getClient());
		}
		catch (LDBException e) { setStatus(e); }
	}

	/***************************************************************************
		Delete one or more records using the field table provided.
		@param expression an SQL expression to use in the search
	*/
	public void deleteRecords(LVValue expression) throws LRException
	{
		DataHRecordData myData=(DataHRecordData)getData();
		getBackground();
		myData.record=createRecord(myData.spec,null);
		try
		{
			myData.record.deleteRecords(background.getClient(),expression.getStringValue());
		}
		catch (LDBException e) { setStatus(e); }
	}

	/***************************************************************************
		Return a string comprising an encrypted form of the fields in this record.
	*/
	public String encrypt() throws LRException
	{
		return LUEncrypt.encrypt(getFields(),
			((DataHRecordData)getData()).encryptKey.getStringValue());
	}

	/***************************************************************************
		Return a string comprising an encrypted form of the fields in this record.
	*/
	public void encrypt(LHStringHolder holder) throws LRException
	{
		holder.setValue(LUEncrypt.encrypt(getFields(),
			((DataHRecordData)getData()).encryptKey.getStringValue()));
	}

	/***************************************************************************
		Decrypt a string and extract the fields from the resulting hashtable.
	*/
	public void decrypt(LVValue value) throws LRException
	{
		DataHRecordData myData=(DataHRecordData)getData();
		String key=myData.encryptKey.getStringValue();
		Hashtable table=(Hashtable)LUEncrypt.decrypt(value.getStringValue(),key);
		// Rebuild the record from the fields in the table
		myData.record=createRecord(myData.spec,table);
	}

	public static final int
		GET_ID=1,
		GET_USING=2;

	/***************************************************************************
		The data for a record.
	*/
	class DataHRecordData extends LHData
	{
		DataHSpecification spec=null;		// The specification of this record
		LVValue encryptKey=new LVStringConstant("A");	// The encryption key of this record
		LDBRecord record=null;			// The record object from the persistent package

		DataHRecordData() {}
	}
}
