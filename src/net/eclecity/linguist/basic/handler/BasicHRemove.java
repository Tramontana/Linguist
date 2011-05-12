// BasicHRemove.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHStringHolder;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Remove an item or all items from a table.
*/
public class BasicHRemove extends LHHandler
{
	private BasicHHashtable table;
	private BasicHVector vector;
	private LVValue key;						// the item to remove (null=all)
	private LHStringHolder string;
	private boolean isLine;

	/***************************************************************************
		Remove an item from a table.
	*/
	public BasicHRemove(int line,BasicHHashtable table,LVValue key)
	{
		super(line);
		this.table=table;
		this.key=key;
	}

	/***************************************************************************
		Remove an item from a vector.
	*/
	public BasicHRemove(int line,BasicHVector vector,LVValue key)
	{
		super(line);
		this.vector=vector;
		this.key=key;
	}

	/***************************************************************************
		Remove a table.
	*/
	public BasicHRemove(int line,BasicHHashtable table)
	{
		super(line);
		this.table=table;
	}

	/***************************************************************************
		Remove the first word or line of a stringholder.
	*/
	public BasicHRemove(int line,LHStringHolder string,boolean isLine)
	{
		super(line);
		this.string=string;
		this.isLine=isLine;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		if (table!=null) table.remove(key);
		else if (vector!=null) vector.remove(key);
		else if (string!=null)
		{
			String s=string.getStringValue();
			int n=s.indexOf(isLine?'\n':' ');
			if (n>=0) s=s.substring(n+1);
			string.setValue(s);
		}
		return pc+1;
	}
}

