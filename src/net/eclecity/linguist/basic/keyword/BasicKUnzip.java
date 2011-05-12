//	BasicKUnzip.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.handler.BasicHUnzip;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	unzip {filename} to {directory}
*/
public class BasicKUnzip extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		LVValue directory=getNextValue();
		skip("to");
		return new BasicHUnzip(line,directory,getValue());
	}
}

