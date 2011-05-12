//	DataKCreate.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.data.keyword;

import java.util.Hashtable;

import net.eclecity.linguist.data.DataLMessages;
import net.eclecity.linguist.data.handler.DataHCreate;
import net.eclecity.linguist.data.handler.DataHRecord;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	create table for {record}
	create {record} where
		field {fieldname} is {value}
		[...]
*/
public class DataKCreate extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
   	if (tokenIs("table"))
   	{
			// create table for {record}
   		skip("for");
   		if (isSymbol())
   		{
   			if (getHandler() instanceof DataHRecord)
   			{
   				return new DataHCreate(line,(DataHRecord)getHandler());
   			}
   		}
   		warning(this,DataLMessages.recordExpected(getToken()));
   		dontUnderstandToken();
		}
		else if (isSymbol())
		{
			// create {record} where field {fieldname} is {value} [...]
			if (getHandler() instanceof DataHRecord)
			{
				DataHRecord record=(DataHRecord)getHandler();
				Hashtable fields=new Hashtable();
				skip("where");
				while (tokenIs("field"))
				{
					LVValue name=getNextValue();
					skip("is");
					LVValue value=getValue();
					fields.put(name,value);
					getNextToken();
				}
				unGetToken();
				return new DataHCreate(line,record,fields);
			}
			warning(this,DataLMessages.specificationExpected(getToken()));
		}
	   return null;
	}
}

