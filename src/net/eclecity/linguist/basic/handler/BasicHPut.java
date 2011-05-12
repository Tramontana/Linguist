// BasicHPut.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHValueHolder;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Put a value into a variable.
	<pre>
	[1.001 GT]  20/10/00  Pre-existing.
	</pre>
*/
public class BasicHPut extends LHHandler
{
	private LHValueHolder valueHolder=null;
	private BasicHHashtable table=null;
	private BasicHQueue queue=null;
	private LVValue value=null;
	private LVValue key=null;

	/***************************************************************************
		Put a value into a value holder
	*/
	public BasicHPut(int line,LVValue value,LHValueHolder valueHolder)
	{
		super(line);
		this.value=value;
		this.valueHolder=valueHolder;
	}

	/***************************************************************************
		Put a value into a queue
	*/
	public BasicHPut(int line,LVValue value,BasicHQueue queue)
	{
		super(line);
		this.value=value;
		this.queue=queue;
	}

	/***************************************************************************
		Put a value into a hashtable
	*/
	public BasicHPut(int line,LVValue value,BasicHHashtable table,LVValue key)
	{
		super(line);
		this.value=value;
		this.table=table;
		this.key=key;
	}

	/***************************************************************************
		(Runtime) Do it now
	*/
	public int execute() throws LRException
	{
		if (queue!=null) queue.put(value);
		else if (table!=null) table.put(key,value);
		else if (valueHolder!=null) valueHolder.setValue(value);
		return pc+1;
	}
}

