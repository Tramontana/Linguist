// BasicHDelete.java

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
	Delete a file.
	<pre>
	[1.001 GT]  23/11/00  New class.
	</pre>
*/
public class BasicHDelete extends LHHandler
{
	private LVValue name;

	public BasicHDelete(int line,LVValue name)
	{
		super(line);
		this.name=name;
	}

	public int execute() throws LRException
	{
		BasicHFile.delete(name);
		return pc+1;
	}
}

