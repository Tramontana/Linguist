//	DataKGet.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.data.keyword;

import java.util.Hashtable;

import net.eclecity.linguist.data.DataLMessages;
import net.eclecity.linguist.data.handler.DataHGet;
import net.eclecity.linguist.data.handler.DataHRecord;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	get {record} id {value}
	get {record} using {sql expression}
	get {record} where field {name} is {value}
		[and field {name} is {value} ...]
	get all {record}
	get next {record}
*/
public class DataKGet extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	   if (tokenIs("all"))
	   {
			// get all {record}
	   	getNextToken();
	   	if (isSymbol())
	   	{
	   		if (getHandler() instanceof DataHRecord) return new DataHGet(line,(DataHRecord)getHandler(),true);
	   	}
	   }
	   else if (tokenIs("next"))
	   {
			// get next {record}
	   	getNextToken();
	   	if (isSymbol())
	   	{
	   		if (getHandler() instanceof DataHRecord) return new DataHGet(line,(DataHRecord)getHandler(),false);
	   	}
	   }
   	else if (isSymbol())
		{
			if (getHandler() instanceof DataHRecord)
			{
				DataHRecord record=(DataHRecord)getHandler();
				getNextToken();
				if (tokenIs("id"))
				{
					// get {record} id {value}
					return new DataHGet(line,record,DataHRecord.GET_ID,getNextValue());
				}
				else if (tokenIs("using"))
				{
					// get {record} using {sql expression}
					return new DataHGet(line,record,DataHRecord.GET_USING,getNextValue());
				}
				else if (tokenIs("where"))
				{
					// get {record} where field {name} is {value} [and field {name} is {value} ...]
					Hashtable fields=new Hashtable();
					while (true)
					{
						getNextToken();
						if (tokenIs("field"))
						{
							LVValue name=getNextValue();
							skip("is");
							fields.put(name,getValue());
							getNextToken();
							if (!tokenIs("and")) break;
						}
						else dontUnderstandToken();
					}
					unGetToken();
					return new DataHGet(line,record,fields);
				}
			}
			warning(this,DataLMessages.recordExpected(getToken()));
		}
	   return null;
	}
}

