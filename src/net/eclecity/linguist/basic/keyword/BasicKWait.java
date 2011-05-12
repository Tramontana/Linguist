//	BasicKWait.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.handler.BasicHWait;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	wait {count} millisecond[s]/tick[s]/second[s]/minute[s]
*/
public class BasicKWait extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	   LVValue count=getValue();
	   getNextToken();
	   int scale;
	   if (getToken().equals("millisecond") || getToken().equals("milliseconds")) scale=1;
	   else if (getToken().equals("tick") || getToken().equals("ticks")) scale=10;
	   else if (getToken().equals("second") || getToken().equals("seconds")) scale=1000;
	   else if (getToken().equals("minute") || getToken().equals("minutes")) scale=60*1000;
	   else
	   {
	      scale=1000;
	      unGetToken();
	   }
		return new BasicHWait(line,count,scale);
	}
}

