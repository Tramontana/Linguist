//	DataLGetValue.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.data;

import net.eclecity.linguist.data.handler.DataHRecord;
import net.eclecity.linguist.data.value.DataVCount;
import net.eclecity.linguist.data.value.DataVEncrypt;
import net.eclecity.linguist.data.value.DataVField;
import net.eclecity.linguist.data.value.DataVFields;
import net.eclecity.linguist.data.value.DataVStatus;
import net.eclecity.linguist.data.value.DataVXML;
import net.eclecity.linguist.main.LLCompiler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLGetValue;
import net.eclecity.linguist.value.LVStringConstant;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Get a value.
	<pre>
		field {fieldname} of {record}
		the status
		the count of {record} [using {query}]
		the fields of {record}
		encrypted {record}
		[all] xml {record}
	<p>
	[1.001 GT]  04/10/00  Pre-existing.
	</pre>
*/
public class DataLGetValue extends LLGetValue
{
	/********************************************************************
		Get a value for the data package.
	*/
	public LVValue getValue(LLCompiler compiler) throws LLException
	{
		this.compiler=compiler;
		numeric=true;
		if (tokenIs("the")) getNextToken();
	   if (tokenIs("status"))
	   {
	   	// the status
			numeric=false;
			getNextToken();
			if (tokenIs("of"))
			{
				unGetToken();
				return null;
			}
			unGetToken();
			return new DataVStatus(getProgram());
	   }
	   else if (tokenIs("count"))
	   {
	   	// the count of {record} [using {query}]
			skip("of");
			if (isSymbol())
			{
				if (getHandler() instanceof DataHRecord)
				{
					LVValue query=new LVStringConstant("*");
					getNextToken();
					if (tokenIs("using"))
					{
						query=getNextValue();
					}
					else unGetToken();
					return new DataVCount((DataHRecord)getHandler(),query);
				}
			}
			warning(this,DataLMessages.recordExpected(getToken()));
	   }
	   else if (tokenIs("field"))
	   {
	   	// field {fieldname} of {record}
	   	LVValue fieldName=getNextValue();
	   	skip("of");
	   	if (isSymbol())
	   	{
	   		if (getHandler() instanceof DataHRecord)
	   		{
			   	numeric=false;
			   	return new DataVField((DataHRecord)getHandler(),fieldName);
				}
			}
			warning(this,DataLMessages.recordExpected(getToken()));
		}
	   else if (tokenIs("fields"))
	   {
	   	// the fields of {record}
	   	// This is a special value that returns a hashtable.
	   	skip("of");
	   	if (isSymbol())
	   	{
	   		if (getHandler() instanceof DataHRecord)
	   		{
			   	numeric=false;
			   	return new DataVFields((DataHRecord)getHandler());
				}
			}
			warning(this,DataLMessages.recordExpected(getToken()));
		}
		else if (tokenIs("encrypted"))
		{
			getNextToken();
			if (isSymbol())
			{
				if (getHandler() instanceof DataHRecord)
					return new DataVEncrypt((DataHRecord)getHandler());
			}
			warning(this,DataLMessages.recordExpected(getToken()));
		}
		else if (tokenIs("all"))
		{
			getNextToken();
			if (tokenIs("xml"))
			{
				getNextToken();
				if (isSymbol())
				{
					if (getHandler() instanceof DataHRecord)
						return new DataVXML((DataHRecord)getHandler(),true);
				}
				warning(this,DataLMessages.recordExpected(getToken()));
			}
		}
		else if (tokenIs("xml"))
		{
			getNextToken();
			if (isSymbol())
			{
				if (getHandler() instanceof DataHRecord)
					return new DataVXML((DataHRecord)getHandler(),false);
			}
			warning(this,DataLMessages.recordExpected(getToken()));
		}
	   return null;
	}
}
