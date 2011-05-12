// DataHRead.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.data.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Read and import all records from an XML file.
*/
public class DataHRead extends LHHandler
{
	private DataHRecord record=null;
	private LVValue fileName=null;

	public DataHRead(int line,DataHRecord record,LVValue fileName)
	{
		this.line=line;
		this.record=record;
		this.fileName=fileName;
	}

	/***************************************************************************
		(Runtime.)  Do it now.
	*/
	public int execute() throws LRException
	{
		if (record!=null)
		{
			if (fileName!=null) record.read(fileName);
		}
		return pc+1;
	}
}

