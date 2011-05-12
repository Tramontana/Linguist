// LLScript.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.main;

public class LLScript implements java.io.Serializable
{
	public int line;
	public String text;
	
	public LLScript(int line,String text)
	{
		this.line=line;
		this.text=text;
	}
}
