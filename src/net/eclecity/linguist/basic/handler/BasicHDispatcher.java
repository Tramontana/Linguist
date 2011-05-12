// BasicHDispatcher.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import java.util.Hashtable;

import net.eclecity.linguist.handler.LHData;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.runtime.LRQueue;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	A dispatcher variable.
*/
public class BasicHDispatcher extends LHVariableHandler
{
	public Object newElement(Object extra) { return new BasicHDispatcherData(); }
	
	/***************************************************************************
		Create a dispatcher.
	*/
	public void create(Hashtable table) throws LRException
	{
		((BasicHDispatcherData)getData()).table=table;
	}

	/***************************************************************************
		Send a command message to the dispatcher.
		This causes a new thread to start for the command.
	*/
	public void send(LVValue command) throws LRException
	{
		Hashtable table=((BasicHDispatcherData)getData()).table;
		if (table==null) throw new LRException(LRException.notCreated(getName()));
		String cmd=command.getStringValue();
		Integer cb=(Integer)table.get(cmd);
		if (cb==null) cb=(Integer)table.get("default");
		if (cb!=null) program.execute(new LRQueue(cb.intValue(),getQueueData()));
	}

	/***************************************************************************
		A private class that holds data about a dispatcher.
	*/
	class BasicHDispatcherData extends LHData
	{
		Hashtable table=new Hashtable();
			
		BasicHDispatcherData() {}
	}
}

