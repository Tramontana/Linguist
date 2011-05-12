// LHModule.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.handler;

import java.util.Enumeration;
import java.util.Hashtable;

import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.runtime.LRProgram;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	A module reference.
*/
public class LHModule extends LHVariableHandler
{
	private static Hashtable modules=new Hashtable();

	public LHModule()
	{
	}

	public void setElement(int index,LRProgram p) throws LRException
	{
		setData(index,p);
	}

	public void setModule(LVValue name) throws LRException
	{
		LRProgram p=getModule(name.getStringValue());
		if (p==null) throw new LRException(LRException.moduleNotFound(name.getStringValue()));
		setModule(p);
	}

	public void setModule(LRProgram p) throws LRException
	{
		setData(p);
		try { modules.put(getModule(),this); }
		catch (Exception e) {}
	}

	public LRProgram getModule() throws LRException
	{
		return (LRProgram)getData();
	}

	public void close() throws LRException
	{
		LRProgram p=(LRProgram)getData();
		p.terminate();
		dispose();
	}

	public String getValue() throws LRException
	{
		LRProgram p=(LRProgram)getData();
		if (p==null) return "undefined";
		return p.getScriptName();
	}

	public String getValue(int n) throws LRException
	{
		LRProgram p=(LRProgram)getData(n);
		if (p==null) return "undefined";
		return p.getScriptName();
	}

	/***************************************************************************
		Get a module, given its timestamp.
	*/
	public static LRProgram getModule(long timestamp)
	{
		Enumeration enumeration=modules.keys();
		while (enumeration.hasMoreElements())
		{
			LRProgram module=(LRProgram)enumeration.nextElement();
			if (module.getTimestamp()==timestamp) return module;
		}
		println("No module found with timestamp "+timestamp);
		return null;
	}

}

