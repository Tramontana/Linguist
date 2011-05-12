//	BasicCMore.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.condition;

import net.eclecity.linguist.basic.handler.BasicHHashtable;
import net.eclecity.linguist.basic.handler.BasicHVector;
import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Test if a hashtable or vector has more data.
*/
public class BasicCMore extends LCCondition
{
	private BasicHHashtable hashTable;
	private BasicHVector vector;

	public BasicCMore(BasicHHashtable hashTable)
	{
		this.hashTable=hashTable;
	}

	public BasicCMore(BasicHVector vector)
	{
		this.vector=vector;
	}

	public boolean test() throws LRException
	{
		if (hashTable!=null) return hashTable.more();
		else if (vector!=null) return vector.more();
		else return false;
	}
}
