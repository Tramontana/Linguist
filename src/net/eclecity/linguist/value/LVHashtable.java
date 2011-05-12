//	LVHashtable.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.value;

import java.util.Hashtable;

/******************************************************************************
	A special kind of value that manages a hashtable.
*/
public class LVHashtable extends LVValue
{
	protected Hashtable table;

	public LVHashtable()
	{
		this.table=new Hashtable();
	}
	
	public LVHashtable(Hashtable table)
	{
		this.table=table;
	}
	
	/***************************************************************************
		Get the table.  This may well be overridden.
	*/
	public Hashtable getTable() { return table; }
	
	public long getNumericValue() { return 0; }
	public String getStringValue() { return ""; }
}
