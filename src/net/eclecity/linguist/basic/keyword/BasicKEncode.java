//	BasicKEncode.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.BasicLMessages;
import net.eclecity.linguist.basic.handler.BasicHEncode;
import net.eclecity.linguist.basic.handler.BasicHHashtable;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHStringHolder;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Encode a hashtable or a file.
	<pre>
	encode {hashtable} to {stringholder}
	encode base64 {file1} to {stringholder}
	<p>
	[1.001 GT]  11/01/03  New class.
	[1.002 GT]  14/06/03  Add base64.
	</pre>
*/
public class BasicKEncode extends LKHandler
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
				if (tokenIs("to"))
				{
					getNextToken();
					if (isSymbol())
					{
						if (getHandler() instanceof LHStringHolder)
						{
							return new BasicHEncode(line,table,(LHStringHolder)getHandler());
						}
					}
				}
				dontUnderstandToken();
			}
			warning(this,BasicLMessages.stringHolderExpected(getToken()));
		}
		else if (tokenIs("base64"))
		{
			LVValue file=getNextValue();
			skip("to");
			if (isSymbol())
			{
				if (getHandler() instanceof LHStringHolder)
				return new BasicHEncode(line,file,(LHStringHolder)getHandler());
			}
		}
	   return null;
	}
}

