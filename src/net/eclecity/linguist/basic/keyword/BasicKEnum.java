//	BasicKEnum.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.handler.BasicHNumber;
import net.eclecity.linguist.handler.LHConstant;
import net.eclecity.linguist.handler.LHFlag;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
   enum {symbol} [{symbol} ...] end
*/
public class BasicKEnum extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		int value=0;
		while (true)
		{
		   inConstant();
		   getNextToken();
		   if (tokenIs("end")) break;
		   LHConstant c=new BasicHNumber(line,getToken(),new Long(value++));
		   addCommand(c);
		   putSymbol(getToken(),c);
		}
	   return new LHFlag();
	}
}

