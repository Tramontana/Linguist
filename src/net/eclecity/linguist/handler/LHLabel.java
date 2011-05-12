// LHLabel.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.handler;

/******************************************************************************
	A program label.
*/
public class LHLabel extends LHHandler
{
	public LHLabel(int line,int pc)
	{
		this.line=line;
		this.pc=pc;
	}

	public int execute() { return pc+1; }
}

