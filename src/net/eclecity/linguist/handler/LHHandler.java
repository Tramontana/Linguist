// LHHandler.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.handler;

import net.eclecity.linguist.main.LLObject;
import net.eclecity.linguist.runtime.LRBackground;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.runtime.LRProgram;

/******************************************************************************
	The base class for all runtime handlers.
*/
public abstract class LHHandler extends LLObject implements java.io.Serializable
{
	protected int line;							// the line of the script where this command starts
	protected transient LRProgram program;	// the program that's running me
	protected transient int pc;				// the location of this command in the program Vector

	public LHHandler()
	{
	}

	public LHHandler(int line)
	{
		this.line=line;
	}

	public LRProgram getModule(String name) { return program.getModule(name); }
	public LRProgram getProgram() { return program; }
	public LRProgram getParent() { return program.getParent(); }
	public void setProgram(LRProgram p) { program=p; }
	public LRBackground getBackground(String packageName) throws LRException { return program.getBackground(packageName); }
	public void setLine(int line) { this.line=line; }
	public int getLine() { return line; }
	public String getCurrentLine() { return program.getLine(line); }
	public int getPC() { return pc; }
	public String getName() { return ""; }			// override if needed
	public void addQueue(LHCallback cb) { cb.getProgram().addQueue(cb.getCallback(),null); }
	public void addQueue(LHCallback cb,Object data) { cb.getProgram().addQueue(cb.getCallback(),data); }
	public void addQueue(int pc) { if (pc>0) program.addQueue(pc,null); }
	public void addQueue(int pc,Object data) { if (pc>0) program.addQueue(pc,data); }
	public Object getQueueData() { return program.getQueueData(); }
	public Object getQueueData(Class c) { return program.getQueueData(c); }
	public void systemOut(String s) { program.systemOut(s); }

	public void setEnvironment(LRProgram program,int pc)
	{
		this.program=program;
		this.pc=pc;
	}

	public abstract int execute() throws LRException;
}

