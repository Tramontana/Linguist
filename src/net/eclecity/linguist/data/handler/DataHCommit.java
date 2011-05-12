// DataHCommit.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.data.handler;

import net.eclecity.linguist.data.runtime.DataRBackground;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Commit changes to the database.
*/
public class DataHCommit extends LHHandler
{
	public DataHCommit(int line)
	{
		this.line=line;
	}

	public int execute() throws LRException
	{
		((DataRBackground)getBackground("data")).commit();
		return pc+1;
	}
}

