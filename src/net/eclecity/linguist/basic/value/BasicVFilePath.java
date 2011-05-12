//	BasicVFilePath.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.value;

import net.eclecity.linguist.basic.runtime.BasicRBackground;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.runtime.LRProgram;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Get the current file path for this module.
*/
public class BasicVFilePath extends LVValue
{
	LRProgram program;

	public BasicVFilePath(LRProgram program)
	{
		this.program=program;
	}

	public long getNumericValue()
	{
		return 0;
	}

	public String getStringValue() throws LRException
	{
		BasicRBackground bg=(BasicRBackground)program.getBackground("basic");
		return bg.getDirectory();
	}
}
