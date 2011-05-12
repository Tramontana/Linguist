//	DataKCommit.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.data.keyword;

import net.eclecity.linguist.data.handler.DataHCommit;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;

/******************************************************************************
	commit
*/
public class DataKCommit extends LKHandler
{
	public LHHandler handleKeyword()
	{
	   return new DataHCommit(line);
	}
}

