// BasicHProcess.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import java.io.IOException;

import net.eclecity.linguist.basic.runtime.BasicRMessages;
import net.eclecity.linguist.handler.LHData;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	A process variable.
*/
public class BasicHProcess extends LHVariableHandler
{
	public Object newElement(Object extra) { return new BasicHProcessData(); }

	/***************************************************************************
		Tell callers this type doesn't return a value.
	*/
	public boolean hasValue() { return false; }

	/***************************************************************************
		Create a new process.
	*/
	public void create(LVValue name) throws LRException
	{
		BasicHProcessData myData=(BasicHProcessData)getData();
		try
		{
			myData.process=Runtime.getRuntime().exec(name.getStringValue());
		}
		catch (IOException e) { throw new LRException(BasicRMessages.cantCreateProcess(getName())); }
	}

	/***************************************************************************
		Close (stop) a process.
	*/
	public void close() throws LRException
	{
		BasicHProcessData myData=(BasicHProcessData)getData();
		myData.process.destroy();
	}
	/***************************************************************************
		A private class that holds data about a process.
	*/
	class BasicHProcessData extends LHData
	{
		Process process=null;

		BasicHProcessData() {}
	}
}

