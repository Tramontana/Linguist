// BasicHReadFile.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Read from a file.
*/
public class BasicHReadFile extends LHHandler
{
	private BasicHFile file;
	private LHVariableHandler variable;
	private BasicHBuffer buffer;
	private LVValue fileName;
	private boolean wholeLine;

	/***************************************************************************
		Read from an already opened file.
	*/
	public BasicHReadFile(int line,BasicHFile file,LHVariableHandler variable,boolean wholeLine)
	{
		super(line);
		this.file=file;
		this.variable=variable;
		this.wholeLine=wholeLine;
	}

	/***************************************************************************
		Read from a named file.
	*/
	public BasicHReadFile(int line,BasicHBuffer buffer,LVValue fileName)
	{
		super(line);
		this.buffer=buffer;
		this.fileName=fileName;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		if (buffer!=null) buffer.readFrom(fileName);
		else if (file!=null) file.read(variable,wholeLine);
		return pc+1;
	}
}

