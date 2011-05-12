// DataHSpecification.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.data.handler;

import java.util.Hashtable;

import net.eclecity.linguist.handler.LHData;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.persist.LDBFieldSpec;
import net.eclecity.linguist.runtime.LRException;


/******************************************************************************
	A record specification.
	The field data is held in a Hashtable
	where each key is a String (the name of the field)
	and each data is an LDBFieldSpec.
*/
public class DataHSpecification extends LHVariableHandler
{
	public DataHSpecification() {}

	public Object newElement(Object extra) { return new DataHSpecData(); }
	
	/***************************************************************************
		Set the fields of this specification.
	*/
	public void setFields(Hashtable fields) throws LRException
	{
		DataHSpecData myData=(DataHSpecData)getData();
		myData.fields=fields;
	}

	/***************************************************************************
		Get the fields of this specification.
	*/
	public Hashtable getFields() throws LRException
	{
		DataHSpecData myData=(DataHSpecData)getData();
		return myData.fields;
	}

	/***************************************************************************
		Set the table name of this specification.
	*/
	public void setTableName(String tableName) throws LRException
	{
		DataHSpecData myData=(DataHSpecData)getData();
		myData.tableName=tableName;
	}

	/***************************************************************************
		Get the table name of this specification.
	*/
	public String getTableName() throws LRException
	{
		DataHSpecData myData=(DataHSpecData)getData();
		return myData.tableName;
	}

	/***************************************************************************
		Get the type of a field.
		@param name the name of the field
		@return the type of the field
	*/
	public int getFieldType(String name) throws LRException
	{
		DataHSpecData myData=(DataHSpecData)getData();
		LDBFieldSpec fieldSpec=(LDBFieldSpec)myData.fields.get(name);
		return fieldSpec.getType();
	}

	/***************************************************************************
		Get the specification of a field.
		@param name the name of the field
		@return the field specification
	*/
	public LDBFieldSpec getFieldSpec(String name) throws LRException
	{
		DataHSpecData myData=(DataHSpecData)getData();
		return (LDBFieldSpec)myData.fields.get(name);
	}
	
	/***************************************************************************
		The data for a record.
	*/
	class DataHSpecData extends LHData
	{
		Hashtable fields=new Hashtable();
		String tableName=null;
		
		DataHSpecData() {}
	}
}
