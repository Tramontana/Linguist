//	BasicCEmpty.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.condition;

import net.eclecity.linguist.basic.handler.BasicHHashtable;
import net.eclecity.linguist.basic.handler.BasicHQueue;
import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Test if a hashtable is empty.
*/
public class BasicCEmpty extends LCCondition
{
	private BasicHHashtable table=null;
	private BasicHQueue queue=null;
	private boolean sense;

	public BasicCEmpty(BasicHHashtable table,boolean sense)
	{
		this.table=table;
		this.sense=sense;
	}

	public BasicCEmpty(BasicHQueue queue,boolean sense)
	{
		this.queue=queue;
		this.sense=sense;
	}

	public boolean test() throws LRException
	{
		boolean test=false;
		if (table!=null) test=table.isEmpty();
		else if (queue!=null) test=queue.isEmpty();
		return sense?test:!test;
	}
}
