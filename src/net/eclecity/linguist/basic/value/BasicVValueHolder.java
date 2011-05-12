//	BasicVValueHolder.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.value;

import net.eclecity.linguist.basic.handler.BasicHHashtable;
import net.eclecity.linguist.basic.handler.BasicHQueue;
import net.eclecity.linguist.basic.handler.BasicHVector;
import net.eclecity.linguist.handler.LHValueHolder;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	The value of a value holder.
*/
public class BasicVValueHolder extends LVValue
{
	private LHValueHolder variable;
	private BasicHQueue queue;
	private BasicHHashtable hashtable;
	private BasicHVector vector;

	public BasicVValueHolder(LHValueHolder variable)
	{
		this.variable=variable;
	}

	public BasicVValueHolder(BasicHQueue queue)
	{
		this.queue=queue;
	}

	public BasicVValueHolder(BasicHHashtable hashtable)
	{
		this.hashtable=hashtable;
	}

	public BasicVValueHolder(BasicHVector vector)
	{
		this.vector=vector;
	}

	public long getNumericValue() throws LRException
	{
		return longValue();
	}

	public String getStringValue() throws LRException
	{
		if (variable!=null) return variable.getStringValue();
		else if (queue!=null) return queue.getValue().getStringValue();
		else if (hashtable!=null) return hashtable.getNextItem();
		else if (vector!=null) return vector.getNextItem();
		return "";
	}
}
