//	DataKSet.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.data.keyword;

import java.util.Hashtable;

import net.eclecity.linguist.data.DataLMessages;
import net.eclecity.linguist.data.handler.DataHRecord;
import net.eclecity.linguist.data.handler.DataHSet;
import net.eclecity.linguist.data.handler.DataHSpecification;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.persist.LDBFieldSpec;
import net.eclecity.linguist.persist.LDBRecord;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	set field {fieldname} of {record} to {value}
	set the fields of {specification} to
		{fieldname} {type} [{n}]
		[and ...]
	set the table name of {specification} to {name}
	set the specification of {record} to {specification}
	set the key of {record} to {encryption key}
*/
public class DataKSet extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   skip("the");
	   if (tokenIs("fields"))
	   {
			// set the fields of {specification} to
			//  {fieldname} {type} [{n}]
			//  [and ...]
	   	Hashtable fields=new Hashtable();
	   	skip("of");
	   	if (isSymbol())
	   	{
	   		if (getHandler() instanceof DataHSpecification)
	   		{
	   			DataHSpecification spec=(DataHSpecification)getHandler();
	   			skip("to");
	   			while (true)
	   			{
	   				String name=getToken();
	   				if (fields.containsKey(name)) throw new LLException(DataLMessages.alreadyGotField(name));
	   				int type=0;
	   				int size=1;
	   				getNextToken();
	   				if (tokenIs("byte"))
	   				{
	   					type=LDBFieldSpec.BYTE;
	   				}
	   				else if (tokenIs("int"))
	   				{
	   					type=LDBFieldSpec.INT;
	   				}
	   				else if (tokenIs("long"))
	   				{
	   					type=LDBFieldSpec.LONG;
	   				}
	   				else if (tokenIs("char"))
	   				{
	   					type=LDBFieldSpec.CHAR;
	   					getNextToken();
	   					if (isConstant()) size=(int)evaluate();
	   					else unGetToken();
	   				}
	   				else if (tokenIs("binary"))
	   				{
	   					type=LDBFieldSpec.BINARY;
	   				}
	   				else throw new LLException(DataLMessages.unknownFieldType(getToken()));
	   				fields.put(name,new LDBFieldSpec(name,type,size));
	   				getNextToken();
	   				if (!tokenIs("and"))
	   				{
	   					unGetToken();
	   					break;
	   				}
	   				getNextToken();
	   			}
	   			if (!fields.containsKey(LDBRecord.ID)) throw new LLException(DataLMessages.noIDField());
	   			return new DataHSet(line,spec,fields);
	   		}
	   	}
	   	warning(this,DataLMessages.specificationExpected(getToken()));
	   	return null;
	   }
	   else if (tokenIs("table"))
	   {
			// set the table name of {specification} to {name}
			getNextToken();
			if (tokenIs("name"))
			{
				skip("of");
				if (isSymbol())
				{
					if (getHandler() instanceof DataHSpecification)
					{
						skip("to");
						return new DataHSet(line,(DataHSpecification)getHandler(),getString());
					}
				}
	   		warning(this,DataLMessages.specificationExpected(getToken()));
			}
	   	return null;
	   }
	   else if (tokenIs("specification"))
	   {
			// set the specification of {record} to {specification}
			getNextToken();
			skip("of");
			{
				if (isSymbol())
				{
					if (getHandler() instanceof DataHRecord)
					{
						DataHRecord record=(DataHRecord)getHandler();
						skip("to");
						if (isSymbol())
						{
							if (getHandler() instanceof DataHSpecification)
							{
								return new DataHSet(line,record,(DataHSpecification)getHandler());
							}
						}
	   				warning(this,DataLMessages.specificationExpected(getToken()));
				   	return null;
	   			}
	   		}
  				warning(this,DataLMessages.recordExpected(getToken()));
		   	return null;
			}
	   }
	   else if (tokenIs("key"))
	   {
			// set the key of {record} to {encryption key}
			getNextToken();
			skip("of");
			{
				if (isSymbol())
				{
					if (getHandler() instanceof DataHRecord)
					{
						DataHRecord record=(DataHRecord)getHandler();
						skip("to");
						return new DataHSet(line,record,getValue());
	   			}
	   		}
  				warning(this,DataLMessages.recordExpected(getToken()));
		   	return null;
			}
	   }
	   else if (tokenIs("field"))
	   {
			// set field {fieldname} of {record} to {value}
	   	LVValue fieldName=getNextValue();
	   	skip("of");
	   	if (isSymbol())
	   	{
	   		if (getHandler() instanceof DataHRecord)
	   		{
	   			skip("to");
	   			return new DataHSet(line,(DataHRecord)getHandler(),fieldName,getValue());
	   		}
	   		warning(this,DataLMessages.recordExpected(getToken()));
	   	}
	   }
	   return null;
	}
}

