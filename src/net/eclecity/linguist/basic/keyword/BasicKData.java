//	BasicKData.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import java.util.Vector;

import net.eclecity.linguist.basic.handler.BasicHData;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.runtime.LRException;


/******************************************************************************
	data {name} [is] {constant} [{constant} ...]
*/
public class BasicKData extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		getNextToken();
		String name=getToken();
		Vector items=new Vector();
		skip("is");
		unGetToken();
		while (true)
		{
		   getNextToken();
		   if (isConstant()) items.addElement(new Long(evaluate(getToken())));
		   else break;
		}
		unGetToken();
		BasicHData data=new BasicHData();
		data.init(line,name,getPC(),items.size());
   	putSymbol(name,data);
   	try
   	{
			for (int n=0; n<items.size(); n++)
				data.setValue(n,((Long)items.elementAt(n)).longValue());
		}
		catch (LRException e) {}
		return data;
	}
}

