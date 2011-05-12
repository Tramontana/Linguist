//	BasicKCopy.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.handler.BasicHCopy;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	<pre>
	copy file {from name} to {to name}

	[1.001 GT]  23/11/00  New class.
	</pre>
*/
public class BasicKCopy extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		getNextToken();
		if (tokenIs("file"))
		{
	   	LVValue fromName=getNextValue();
	   	skip("to");
	   	return new BasicHCopy(line,fromName,getValue());
	   }
	   return null;
	}
}

