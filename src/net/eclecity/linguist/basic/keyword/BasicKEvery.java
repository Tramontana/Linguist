//	BasicKEvery.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.handler.BasicHEvery;
import net.eclecity.linguist.handler.LHFlag;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHNoop;
import net.eclecity.linguist.handler.LHStop;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	<pre>
	every {count} tick[s]/second[s]/minute[s] {block}

	[1.001 GT]  08/08/00  Pre-existing.
	</pre>
*/
public class BasicKEvery extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	   LVValue count=getValue();
	   getNextToken();
	   int scale;
	   if (getToken().equals("tick") || getToken().equals("ticks")) scale=10;
	   else if (getToken().equals("second") || getToken().equals("seconds")) scale=1000;
	   else if (getToken().equals("minute") || getToken().equals("minutes")) scale=60*1000;
	   else
	   {
	      scale=1000;
	      unGetToken();
	   }
		int here=getPC();
		addCommand(new LHNoop(0));
	   int where=getPC();
		doKeyword();
		addCommand(new LHStop(line));
		int next=getPC();
		LHHandler timer=new BasicHEvery(line,count,scale,where,next);
		compiler.setCommandAt(timer,here);
		return new LHFlag();
	}
}

