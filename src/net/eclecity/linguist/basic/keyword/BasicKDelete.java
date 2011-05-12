//	BasicKDelete.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.handler.BasicHDelete;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
	<pre>
	delete file {name}

	[1.001 GT]  23/11/00  New class.
	</pre>
*/
public class BasicKDelete extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		getNextToken();
		if (tokenIs("file"))
		{
		   return new BasicHDelete(line,getNextValue());
		}
		return null;
	}
}

