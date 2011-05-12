//	BasicCFileOpen.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.condition;

import net.eclecity.linguist.basic.handler.BasicHFile;
import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Test if a file is open.
*/
public class BasicCFileOpen extends LCCondition
{
	private BasicHFile file;				// the file to test
	private boolean sense;			// true if returning true

	public BasicCFileOpen(BasicHFile file,boolean sense)
	{
		this.file=file;
		this.sense=sense;
	}

	public boolean test() throws LRException
	{
		return sense?file.isOpen():!file.isOpen();
	}
}
