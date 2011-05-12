// BasicHRename.java

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
	Rename a file.
	<pre>
	[1.001 GT]  23/11/00  New class.
	</pre>
*/
public class BasicHRename extends LHHandler
{
	private LVValue oldName;
	private LVValue newName;

	public BasicHRename(int line,LVValue oldName,LVValue newName)
	{
		super(line);
		this.oldName=oldName;
		this.newName=newName;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		BasicHFile.rename(oldName,newName);
		return pc+1;
	}
}

