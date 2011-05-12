// DataHInit.java

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
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Initialize the database.
*/
public class DataHInit extends LHHandler
{
	private LVValue name=null;

	public DataHInit(int line,LVValue name)
	{
		this.line=line;
		this.name=name;
	}

	/***************************************************************************
		(Runtime.)  Do it now.
	*/
	public int execute() throws LRException
	{
		if (name!=null)
		{
			DataRBackground background=(DataRBackground)getBackground("data");
			background.initDB(name);
		}
		return pc+1;
	}
}

