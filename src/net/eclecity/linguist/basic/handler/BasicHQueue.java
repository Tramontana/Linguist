// BasicHQueue.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import java.util.Vector;

import net.eclecity.linguist.handler.LHData;
import net.eclecity.linguist.handler.LHValueHolder;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVStringConstant;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	A queue variable.
	<pre>
	[1.000 GT] 02/03/01  New class.
	</pre>
*/
public class BasicHQueue extends LHVariableHandler
{
	public Object newElement(Object extra) { return new BasicHQueueData(); }
	
	/***************************************************************************
		Tell the caller if the queue is empty.
	*/
	public boolean isEmpty() throws LRException
	{
		return ((BasicHQueueData)getData()).queue.isEmpty();
	}

	/***************************************************************************
		Put a value into the queue.
	*/
	public void put(LVValue value) throws LRException
	{
		((BasicHQueueData)getData()).queue.addElement(value.getStringValue());
	}

	/***************************************************************************
		Put a value holder into the queue.
	*/
	public void put(LHValueHolder valueHolder) throws LRException
	{
		((BasicHQueueData)getData()).queue.addElement(valueHolder.getStringValue());
	}

	/***************************************************************************
		Return the next value from the queue.
	*/
	public LVValue getValue() throws LRException
	{
		Vector queue=((BasicHQueueData)getData()).queue;
		if (queue.isEmpty()) return new LVStringConstant("");
		String value=(String)queue.elementAt(0);
		queue.removeElementAt(0);		
		return new LVStringConstant(value);
	}
	
	/***************************************************************************
		An inner class that holds data about a queue.
	*/
	class BasicHQueueData extends LHData
	{
		Vector<String> queue;

		BasicHQueueData()
		{
			queue=new Vector<String>();
		}
	}
}

