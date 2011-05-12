//	BasicKConstant.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.handler.BasicHNumber;
import net.eclecity.linguist.handler.LHConstant;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
   constant {symbol} {expression}
*/
public class BasicKConstant extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	   inConstant();
	   String name=getToken();
	   getNextToken();
	   LHConstant c=new BasicHNumber(line,name,new Long(evaluate(getToken())));
	   putSymbol(name,c);
	   return c;
	}
}

