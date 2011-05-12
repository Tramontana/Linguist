// BasicHSystem.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import java.io.IOException;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Issue a system command.
*/
public class BasicHSystem extends LHHandler implements Runnable
{
	private LVValue command;
	private String cmdString;

	public BasicHSystem(int line,LVValue command)
	{
		super(line);
		this.command=command;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		cmdString=command.getStringValue();
		new Thread(this).start();
		return pc+1;
	}
	
	public void run()
	{
		try { Runtime.getRuntime().exec(cmdString); }
		catch (IOException e) { System.out.println(e); }
	}
}

