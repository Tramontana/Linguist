// BasicHCreate.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import java.util.Hashtable;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Create a new file or process.
*/
public class BasicHCreate extends LHHandler
{
	private BasicHFile file=null;
	private BasicHProcess process=null;
	private BasicHTimer timer=null;
	private BasicHDispatcher dispatcher=null;
	private LVValue directory;
	private LVValue name;
	private LVValue value;
	private LVValue left;
	private LVValue top;
	private LVValue id;
	private int scale;
	private boolean useDialog;
	private Hashtable table;

	/***************************************************************************
		Create a directory using the name given.
	*/
	public BasicHCreate(int line,LVValue directory)
	{
		super(line);
		this.directory=directory;
	}

	/***************************************************************************
		Create a file using the name given.
	*/
	public BasicHCreate(int line,BasicHFile file,LVValue name)
	{
		super(line);
		this.file=file;
		this.name=name;
		useDialog=false;
	}

	/***************************************************************************
		Create a file using a dialog.
	*/
	public BasicHCreate(int line,BasicHFile file,LVValue name,LVValue left,LVValue top)
	{
		super(line);
		this.file=file;
		this.name=name;
		this.left=left;
		this.top=top;
		useDialog=true;
	}

	/***************************************************************************
		Create a process.
	*/
	public BasicHCreate(int line,BasicHProcess process,LVValue name)
	{
		super(line);
		this.process=process;
		this.name=name;
	}

	/***************************************************************************
		Create a timer.
	*/
	public BasicHCreate(int line,BasicHTimer timer,LVValue value,int scale,LVValue id)
	{
		super(line);
		this.timer=timer;
		this.value=value;
		this.scale=scale;
		this.id=id;
	}

	/***************************************************************************
		Create a dispatcher.
	*/
	public BasicHCreate(int line,BasicHDispatcher dispatcher,Hashtable table)
	{
		super(line);
		this.dispatcher=dispatcher;
		this.table=table;
	}

	/***************************************************************************
		(Runtime) Do it now.
	*/
	public int execute() throws LRException
	{
		if (directory!=null) BasicHFile.makeDirectory(directory);
		else if (file!=null)
		{
			if (useDialog) file.create(name,left,top);
			else file.create(name);
		}
		else if (process!=null) process.create(name);
		else if (timer!=null) timer.create(value,scale,id);
		else if (dispatcher!=null) dispatcher.create(table);
		return pc+1;
	}
}

