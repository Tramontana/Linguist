// BasicHClose.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHModule;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Close a file, module or process.
*/
public class BasicHClose extends LHHandler
{
	private BasicHFile file;
	private LHModule module;
	private BasicHProcess process;

	public BasicHClose(int line,BasicHFile file)
	{
		super(line);
		this.file=file;
	}

	public BasicHClose(int line,LHModule module)
	{
		super(line);
		this.module=module;
	}

	public BasicHClose(int line,BasicHProcess process)
	{
		super(line);
		this.process=process;
	}

	public int execute() throws LRException
	{
		if (file!=null) file.close();
		else if (module!=null) module.close();
		else if (process!=null) process.close();
		return pc+1;
	}
}

