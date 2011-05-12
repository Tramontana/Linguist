//	BasicKClose.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.BasicLMessages;
import net.eclecity.linguist.basic.handler.BasicHClose;
import net.eclecity.linguist.basic.handler.BasicHFile;
import net.eclecity.linguist.basic.handler.BasicHProcess;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHModule;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
	close {file}/{module}/{process}
*/
public class BasicKClose extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	  	if (isSymbol())
	  	{
			LHHandler handler=getHandler();
			if (handler instanceof BasicHFile) return new BasicHClose(line,(BasicHFile)handler);
			if (handler instanceof LHModule) return new BasicHClose(line,(LHModule)handler);
			if (handler instanceof BasicHProcess) return new BasicHClose(line,(BasicHProcess)handler);
			warning(this,BasicLMessages.fileExpected(getToken()));
		}
	   return null;
	}
}

