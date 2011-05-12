// BasicHSaveTable.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Save a table.
*/
public class BasicHSaveTable extends LHHandler
{
	private BasicHHashtable table;
	private LVValue fileName;

	public BasicHSaveTable(int line,BasicHHashtable table,LVValue fileName)
	{
		super(line);
		this.table=table;
		this.fileName=fileName;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute()
	{
		table.save(fileName);
		return pc+1;
	}
}

