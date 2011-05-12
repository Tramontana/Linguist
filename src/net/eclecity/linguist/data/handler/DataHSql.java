// DataHSql.java

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
	Issue an SQL command.
*/
public class DataHSql extends LHHandler
{
	private LVValue command=null;

	public DataHSql(int line,LVValue command)
	{
		this.line=line;
		this.command=command;
	}

	/***************************************************************************
		The runtime execution method.
	*/
	public int execute() throws LRException
	{
		((DataRBackground)getBackground("data")).sql(command.getStringValue());
		return pc+1;
	}
}

