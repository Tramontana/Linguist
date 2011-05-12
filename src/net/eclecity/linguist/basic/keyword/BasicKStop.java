//	BasicKStop.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.handler.BasicHProcess;
import net.eclecity.linguist.basic.handler.BasicHSound;
import net.eclecity.linguist.basic.handler.BasicHStop;
import net.eclecity.linguist.basic.handler.BasicHTimer;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHLabel;
import net.eclecity.linguist.handler.LHStop;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
	stop {sound}
	stop {timer}
	stop {process}
	stop
*/
public class BasicKStop extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		getNextToken();
		if (isSymbol())
		{
			if (getHandler() instanceof BasicHSound) return new BasicHStop(line,(BasicHSound)getHandler());
			else if (getHandler() instanceof BasicHTimer) return new BasicHStop(line,(BasicHTimer)getHandler());
			else if (getHandler() instanceof BasicHProcess) return new BasicHStop(line,(BasicHProcess)getHandler());
			else if (!(getHandler() instanceof LHLabel)) return null;
		}
		unGetToken();
		return new LHStop(line);
	}
}

