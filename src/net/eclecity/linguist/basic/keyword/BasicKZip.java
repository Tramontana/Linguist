//	BasicKZip.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.handler.BasicHZip;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	zip {directory} to {filename}
*/
public class BasicKZip extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		LVValue files=getNextValue();
		skip("to");
		return new BasicHZip(line,files,getValue());
	}
}

