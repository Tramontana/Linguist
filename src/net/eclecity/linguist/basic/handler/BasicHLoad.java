// BasicHLoad.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Load something from a file.
*/
public class BasicHLoad extends LHHandler
{
	private BasicHBuffer buffer=null;
	private BasicHHashtable table=null;
	private BasicHSound sound=null;
	private LVValue path;

	public BasicHLoad(int line,BasicHBuffer buffer,LVValue path)
	{
		super(line);
		this.buffer=buffer;
		this.path=path;
	}

	public BasicHLoad(int line,BasicHHashtable table,LVValue path)
	{
		super(line);
		this.table=table;
		this.path=path;
	}

	public BasicHLoad(int line,BasicHSound sound,LVValue path)
	{
		super(line);
		this.sound=sound;
		this.path=path;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		if (buffer!=null) buffer.load(path);
		else if (table!=null) table.load(path);
		else if (sound!=null) sound.load(path);
		return pc+1;
	}
}

