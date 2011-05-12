//	BasicKDecode.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.BasicLMessages;
import net.eclecity.linguist.basic.handler.BasicHDecode;
import net.eclecity.linguist.basic.handler.BasicHHashtable;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHStringHolder;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
	Decode a string to a hashtable or a file.
	<pre>
	decode {hashtable} from {stringholder}
	decode base64 {string} to {file}
	<p>
	[1.001 GT]  06/10/00  New class.
	[1.002 GT]  14/06/03  Add base64.
	</pre>
*/
public class BasicKDecode extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	   if (isSymbol())
		{
			if (getHandler() instanceof BasicHHashtable)
			{
				BasicHHashtable table=(BasicHHashtable)getHandler();
				getNextToken();
				if (tokenIs("from"))
				{
					getNextToken();
					if (isSymbol())
					{
						if (getHandler() instanceof LHStringHolder)
						{
							return new BasicHDecode(line,table,(LHStringHolder)getHandler());
						}
					}
				}
				dontUnderstandToken();
			}
			warning(this,BasicLMessages.stringHolderExpected(getToken()));
		}
		else if (tokenIs("base64"))
		{
			getNextToken();
			if (isSymbol())
			{
				if (getHandler() instanceof LHStringHolder)
				{
					LHStringHolder string=(LHStringHolder)getHandler();
					skip("to");
					if (tokenIs("file")) getNextToken();
					return new BasicHDecode(line,string,getValue());
				}
			}
		}
	   return null;
	}
}

