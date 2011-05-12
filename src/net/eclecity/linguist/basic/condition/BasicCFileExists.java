//	BasicCFileExists.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.condition;

import java.io.File;

import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Test for end of file.
*/
public class BasicCFileExists extends LCCondition
{
	private LVValue name;			// the file to test
	private boolean sense;			// true if returning true

	public BasicCFileExists(LVValue name,boolean sense)
	{
		this.name=name;
		this.sense=sense;
	}

	public boolean test() throws LRException
	{
		File f=new File(name.getStringValue());
		return sense?f.exists():!f.exists();
	}
}
