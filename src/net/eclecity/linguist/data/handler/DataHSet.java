// DataHSet.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.data.handler;

import java.util.Hashtable;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Set something.
*/
public class DataHSet extends LHHandler
{
	private DataHSpecification spec=null;
	private DataHRecord record=null;
	private Hashtable fields=null;
	private LVValue key=null;
	private LVValue data=null;
	private LVValue encryptKey=null;
	String tableName=null;

	/***************************************************************************
		Set the fields of a record specification,
		or the list of fields to use for the ID.
	*/
	public DataHSet(int line,DataHSpecification spec,Hashtable fields)
	{
		this.line=line;
		this.spec=spec;
		this.fields=fields;
	}

	/***************************************************************************
		Set the name of the table for this record type.
	*/
	public DataHSet(int line,DataHSpecification spec,String tableName)
	{
		this.line=line;
		this.spec=spec;
		this.tableName=tableName;
	}

	/***************************************************************************
		Set the specification of a record.
	*/
	public DataHSet(int line,DataHRecord record,DataHSpecification spec)
	{
		this.line=line;
		this.record=record;
		this.spec=spec;
	}

	/***************************************************************************
		Set the encryption key of a record.
	*/
	public DataHSet(int line,DataHRecord record,LVValue encryptKey)
	{
		this.line=line;
		this.record=record;
		this.encryptKey=encryptKey;
	}

	/***************************************************************************
		Set the value of a field in a record.
	*/
	public DataHSet(int line,DataHRecord record,LVValue key,LVValue data)
	{
		this.line=line;
		this.record=record;
		this.key=key;
		this.data=data;
	}

	/***************************************************************************
		The runtime execution method.
	*/
	public int execute() throws LRException
	{
		if (spec!=null)
		{
			if (fields!=null) spec.setFields(fields);
			else if (tableName!=null) spec.setTableName(tableName);
			else if (record!=null) record.setSpecification(spec);
		}
		else if (record!=null)
		{
			if (key!=null) record.setFieldValue(key,data);
			else if (encryptKey!=null) record.setEncryptionKey(encryptKey);
		}
		return pc+1;
	}
}

