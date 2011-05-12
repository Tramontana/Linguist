//	BasicKString.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.handler.BasicHString;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
   string {symbol} {string}
   string {symbol} from {filename}
*/
public class BasicKString extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	   inConstant();
	   String name=getToken();
	   getNextToken();
	   if (tokenIs("from"))
	   {
		   getNextToken();
		   BasicHString handler=new BasicHString(line,name,getString(),true,getPass());
		   putSymbol(name,handler);
		   return handler;
	   }
		BasicHString handler=new BasicHString(line,name,getString(),false,getPass());
		putSymbol(name,handler);
		return handler;
	}
}

