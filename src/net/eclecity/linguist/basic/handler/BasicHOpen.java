// BasicHOpen.java

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
	Open a file or socket.
*/
public class BasicHOpen extends LHHandler
{
	private BasicHFile file=null;
	private LVValue name;
	private LVValue left;
	private LVValue top;
	boolean useDialog;
	boolean input;
	boolean output;

	public BasicHOpen(int line,BasicHFile file,LVValue name)
	{
		super(line);
		this.file=file;
		this.name=name;
		useDialog=false;
	}

	public BasicHOpen(int line,BasicHFile file,LVValue name,LVValue left,LVValue top)
	{
		super(line);
		this.file=file;
		this.name=name;
		this.left=left;
		this.top=top;
		useDialog=true;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		if (file!=null)
		{
			if (useDialog) file.open(name,left,top);
			else file.open(name);
		}
		return pc+1;
	}
}

