//	BasicKDecrypt.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.BasicLMessages;
import net.eclecity.linguist.basic.handler.BasicHDecrypt;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHStringHolder;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
	Decrypt a string.
	<pre>
	decrypt {stringholder} using {key}
	<p>
	[1.001 GT]  06/10/00  New class.
	</pre>
*/
public class BasicKDecrypt extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	   if (isSymbol())
		{
			if (getHandler() instanceof LHStringHolder)
			{
				LHStringHolder handler=(LHStringHolder)getHandler();
				getNextToken();
				if (tokenIs("using")) return new BasicHDecrypt(line,handler,getNextValue());
				dontUnderstandToken();
			}
			warning(this,BasicLMessages.stringHolderExpected(getToken()));
		}
	   return null;
	}
}

