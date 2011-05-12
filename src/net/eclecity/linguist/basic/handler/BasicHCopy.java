// BasicHCopy.java

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
	Copy a file.
	<pre>
	[1.001 GT]  23/11/00  New class.
	</pre>
*/
public class BasicHCopy extends LHHandler
{
	private LVValue fromName;
	private LVValue toName;

	public BasicHCopy(int line,LVValue fromName,LVValue toName)
	{
		super(line);
		this.fromName=fromName;
		this.toName=toName;
	}

	public int execute() throws LRException
	{
		BasicHFile.copy(fromName,toName);
		return pc+1;
	}
}

