//	BasicCHasData.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.condition;

import net.eclecity.linguist.basic.handler.BasicHHashtable;
import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Test if a hashtable has data.
*/
public class BasicCHasData extends LCCondition
{
	private BasicHHashtable hashTable=null;

	public BasicCHasData(BasicHHashtable hashTable)
	{
		this.hashTable=hashTable;
	}

	public boolean test() throws LRException
	{
		if (hashTable!=null) return hashTable.more();
		return false;
	}
}
