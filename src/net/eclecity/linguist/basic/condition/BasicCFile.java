//	BasicCFile.java

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
	Test file conditions.
*/
public class BasicCFile extends LCCondition
{
	private LVValue name;			// the file to test
	private int opcode;
	private boolean invert;

	public static final int
		FILE_EXISTS=1,
		FILE_IS_DIRECTORY=2;

	/***************************************************************************
		Test a file condition.
	*/
	public BasicCFile(LVValue name,int opcode,boolean invert)
	{
		this.name=name;
		this.opcode=opcode;
		this.invert=invert;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public boolean test() throws LRException
	{
		File file=new File(name.getStringValue());
		boolean result=false;
		switch (opcode)
		{
			case FILE_EXISTS:
				result=file.exists();
				break;
			case FILE_IS_DIRECTORY:
				result=file.isDirectory();
				break;
		}
		if (invert) result=!result;
		return result;
	}
}
