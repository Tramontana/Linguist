//	BasicKWhile.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.handler.BasicHWhile;
import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.handler.LHGoto;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHNoop;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
   while {condition} {block}
*/
public class BasicKWhile extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		int here=getPC();
		addCommand(new LHNoop(0));
		LCCondition condition=getCondition();
		doKeyword();
		compiler.setCommandAt(new BasicHWhile(line,condition,getPC()+1),here);
		return new LHGoto(line+1,here);
	}
}

