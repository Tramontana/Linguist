// BasicHLoadPackage.java

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
	Load a zip file into a package.
*/
public class BasicHLoadPackage extends LHHandler
{
	private BasicHPackage pkg;							// the package
	private LVValue file;							// the zip file to load

	public BasicHLoadPackage(int line,BasicHPackage pkg,LVValue file)
	{
		super(line);
		this.pkg=pkg;
		this.file=file;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute()
	{
		pkg.load(file);
		return pc+1;
	}
}

