//	BasicLGetPair.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic;

import net.eclecity.linguist.main.LLCompiler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLGetPair;
import net.eclecity.linguist.value.LVPair;
import net.eclecity.linguist.value.LVValue;

public class BasicLGetPair extends LLGetPair
{
	public BasicLGetPair() {}

	/********************************************************************
		Create a value containing two items.
	*/
	public LVPair getPair(LLCompiler compiler)
	{
		this.compiler=compiler;
		try
		{
			LVValue first=getValue();
			if (first==null) return null;
			getNextToken();
			LVValue last=getValue();
			if (last==null) return null;
			return new LVPair(first,last);
		}
		catch (LLException e) { return null; }
	}
}
