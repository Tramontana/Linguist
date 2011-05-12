//	IoLGetValue.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.io;

import net.eclecity.linguist.main.LLCompiler;
import net.eclecity.linguist.main.LLGetValue;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Get a value.
*/
public class IoLGetValue extends LLGetValue
{
	public boolean isNumeric() { return numeric; }
	public boolean isString() { return !numeric; }

	/********************************************************************
		* Create a numeric or string value.
		*
	*/

	public LVValue getValue(LLCompiler compiler)
	{
		this.compiler=compiler;
		if (tokenIs("the"))
		{
		}
	   return null;
	}
}
