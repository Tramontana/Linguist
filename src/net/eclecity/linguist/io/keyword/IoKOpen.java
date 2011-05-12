//	IoKOpen.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.io.keyword;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.io.IoLMessages;
import net.eclecity.linguist.io.handler.IoHDevice;
import net.eclecity.linguist.io.handler.IoHOpen;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
	open {device} {name}
*/
public class IoKOpen extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	  	if (isSymbol())
	  	{
			LHHandler handler=getHandler();
			if (handler instanceof IoHDevice)
			{
				// open {device} {name}
				return new IoHOpen(line,(IoHDevice)handler,getNextValue());
		   }
			warning(this,IoLMessages.deviceExpected(getToken()));
		}
	   return null;
	}
}

