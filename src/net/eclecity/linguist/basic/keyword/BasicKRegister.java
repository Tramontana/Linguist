//	BasicKRegister.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.handler.BasicHRegister;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	register {name} {number}
*/
public class BasicKRegister extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
      LVValue value=getValue();
      getNextToken();
      return new BasicHRegister(line,value,getValue());
	}
}
