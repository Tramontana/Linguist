//	IoKRead.java

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
import net.eclecity.linguist.io.handler.IoHRead;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	read port {n} of {device}
*/
public class IoKRead extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	   if (tokenIs("port"))
	   {
	   	LVValue port=getNextValue();
	   	skip("of");
	  		if (isSymbol())
	  		{
				LHHandler handler=getHandler();
				if (handler instanceof IoHDevice)
				{
					// set port {n} of {device}
					return new IoHRead(line,(IoHDevice)handler,port);
		   	}
				warning(this,IoLMessages.deviceExpected(getToken()));
			}
		}
	   return null;
	}
}

