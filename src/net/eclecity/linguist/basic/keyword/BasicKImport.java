//	BasicKImport.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import java.util.Vector;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHImport;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLMessages;


/******************************************************************************
	import {variable declaration} [and {variable declaration} ...]
*/
public class BasicKImport extends LKHandler
{
	private Vector imports;

	public LHHandler handleKeyword() throws LLException
	{
		imports=new Vector();
		while (true)
		{
			getNextToken();
			String token=getToken();
	   	String name=token.substring(0,1).toUpperCase()+token.substring(1);
	   	String[] packages=compiler.getPackages();
	   	int n;
	   	for (n=0; n<packages.length; n++)
	   	{
	   		Class c=null;
				try
				{
					String className="net.eclecity.linguist."+packages[n]+".handler."
						+packages[n].substring(0,1).toUpperCase()
						+packages[n].substring(1).toLowerCase()
						+"H"+name;
					c=Class.forName(className);
					if (c!=null)
					{
						addImport(c);
						break;
					}
				}
				catch (ClassNotFoundException e) {}
			}
			if (n==packages.length) throw new LLException(LLMessages.unhandledVariable(token));
			getNextToken();
			if (!tokenIs("and")) break;
		}
		unGetToken();
      return new LHImport(line,imports);
	}

	private void addImport(Class c) throws LLException
	{
		getNextToken();
		String name=getToken();
		LHHandler handler=null;
		try
		{
		   handler=(LHHandler)c.newInstance();
		}
		catch (InstantiationException e) { noHandler(c); }
		catch (IllegalAccessException e) { noHandler(c); }
		if (handler instanceof LHVariableHandler)
		{
		   LHVariableHandler vh=(LHVariableHandler)handler;
		   vh.init(line,name,getPC(),1);
		}
   	putSymbol(name,handler);
   	imports.addElement(handler);
	}

	private void noHandler(Class c) throws LLException
	{
		throw new LLException(LLMessages.noRuntimeHandler(c.getName()));
	}
}

