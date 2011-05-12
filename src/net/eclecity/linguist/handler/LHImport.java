// LHImport.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.handler;

import java.util.Vector;

/******************************************************************************
	Import a variable list.
*/
public class LHImport extends LHHandler
{
	Vector imports;

	public LHImport(int line,Vector imports)
	{
		this.line=line;
		this.imports=imports;
	}

	public int execute()
	{
		for (int n=0; n<imports.size(); n++)
			((LHHandler)imports.elementAt(n)).setProgram(getProgram());
		return pc+1;
	}
	
	public Vector getImports() { return imports; }
}

