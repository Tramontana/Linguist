// BasicHSetFilePath.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.basic.runtime.BasicRBackground;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Set the current file path.
*/
public class BasicHSetFilePath extends LHHandler
{
	private LVValue path;

	public BasicHSetFilePath(int line,LVValue path)
	{
		super(line);
		this.path=path;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		BasicRBackground bg=(BasicRBackground)getBackground("basic");
		bg.setDirectory(path.getStringValue());
		return pc+1;
	}
}

