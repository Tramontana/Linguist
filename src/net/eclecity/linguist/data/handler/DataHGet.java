// DataHGet.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.data.handler;

import java.util.Enumeration;
import java.util.Hashtable;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Get a record from the database.
*/
public class DataHGet extends LHHandler
{
	private DataHRecord record=null;
	private LVValue value=null;
	private Hashtable fields=null;
	private int opcode;
	private boolean allRecords;

	/***************************************************************************
		Get all records that match the expression given.
	*/
	public DataHGet(int line,DataHRecord record,int opcode,LVValue value)
	{
		this.line=line;
		this.record=record;
		this.opcode=opcode;
		this.value=value;
	}

	/***************************************************************************
		Get all records that match the field list given.
	*/
	public DataHGet(int line,DataHRecord record,Hashtable fields)
	{
		this.line=line;
		this.record=record;
		this.fields=fields;
	}

	/***************************************************************************
		Get all records of this type or the next record in the result set.
	*/
	public DataHGet(int line,DataHRecord record,boolean allRecords)
	{
		this.line=line;
		this.record=record;
		this.allRecords=allRecords;
	}

	/***************************************************************************
		(Runtime.)  Do it now.
	*/
	public int execute() throws LRException
	{
		if (record!=null)
		{
			if (fields!=null)											// get {record} where ...
			{
				Hashtable ht=new Hashtable();
				Enumeration enumeration=fields.keys();
				while (enumeration.hasMoreElements())
				{
					LVValue value=(LVValue)enumeration.nextElement();
					String key=value.getStringValue();
					String data=((LVValue)fields.get(value)).getStringValue();
					ht.put(key,data);
				}
				record.getRecords(ht);
			}
			else if (value!=null) record.get(opcode,value);
			else if (allRecords) record.getRecords((LVValue)null);	// get all {record}
			else record.getNextRecord();							// get next {record}
		}
		return pc+1;
	}
}

