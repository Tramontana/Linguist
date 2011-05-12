//	ServletKOn.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.servlet.keyword;

import net.eclecity.linguist.handler.LHFlag;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHNoop;
import net.eclecity.linguist.handler.LHStop;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.servlet.handler.ServletHOn;

/******************************************************************************
	on http {block}
*/
public class ServletKOn extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	   if (tokenIs("http"))
	   {
			int here=getPC();
			addCommand(new LHNoop(0));
			doKeyword();
			addCommand(new LHStop(line));
			setCommandAt(new ServletHOn(line,getPC()),here);
			return new LHFlag();
		}
      return null;
	}
}

