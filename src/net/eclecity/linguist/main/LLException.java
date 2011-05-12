//	LLException.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.main;

import java.util.Vector;

public class LLException extends Exception
{
	private String message;

	public LLException(String message)
	{
		this.message=message;
	}

	public Vector getMessage(LLCompiler compiler)
	{
		Vector v=new Vector();
		if (message.length()==0) return v;
		int line=compiler.getCurrentScriptLine();
		if (line>0)
		{
			if (line>3) v.addElement(getLineNo(line-4)+compiler.getRelativeLine(-4));
			if (line>2) v.addElement(getLineNo(line-3)+compiler.getRelativeLine(-3));
			if (line>1) v.addElement(getLineNo(line-2)+compiler.getRelativeLine(-2));
			v.addElement(getLineNo(line-1)+compiler.getRelativeLine(-1));
		}
		if (line<compiler.scriptLines)
			v.addElement(getLineNo(line)+compiler.getRelativeLine(0));
		v.addElement("Line "+String.valueOf(line+1)+" of "+compiler.getScriptName()+": "+message);
		return v;
	}
	
	private String getLineNo(int n)
	{
		String s="    "+String.valueOf(n+1);
		return s.substring(s.length()-4)+": ";
	}
}
