//	BasicCEndOfFile.java

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
	Test for end of file.
*/
public class BasicCEndOfFile extends LCCondition
{
	private BasicHFile file;			// the file to test
	private boolean sense;		// true if testing for end

	public BasicCEndOfFile(BasicHFile file,boolean sense)
	{
		this.file=file;
		this.sense=sense;
	}

	public boolean test() throws LRException
	{
		boolean eof=file.isAtEOF();
		return sense?eof:!eof;
	}
}
