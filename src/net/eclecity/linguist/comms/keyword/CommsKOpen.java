//	CommsKOpen.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.comms.keyword;

import net.eclecity.linguist.comms.CommsLMessages;
import net.eclecity.linguist.comms.handler.CommsHOpen;
import net.eclecity.linguist.comms.handler.CommsHPort;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	open {port} {name} [mode raw] [for [input] [and] [output]]
*/
public class CommsKOpen extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	  	if (isSymbol())
	  	{
			LHHandler handler=getHandler();
			if (handler instanceof CommsHPort)
			{
				// open {port} {name} [mode raw] [for [input] [and] [output]]
				LVValue name=getNextValue();
				boolean rawMode=false;
				boolean input=false;
				boolean output=false;
				getNextToken();
				if (tokenIs("mode"))
				{
					getNextToken();
					if (tokenIs("raw"))
					{
						rawMode=true;
						getNextToken();
					}
					else dontUnderstandToken();
				}
			   if (tokenIs("for"))
			   {
			   	while (true)
			   	{
			   		getNextToken();
			   		if (tokenIs("input")) input=true;
			   		else if (tokenIs("output")) output=true;
			   		else dontUnderstandToken();
			   		getNextToken();
			   		if (!tokenIs("and"))
			   		{
			   			unGetToken();
			   			break;
			   		}
			   	}
			   }
			   else input=true;
				return new CommsHOpen(line,(CommsHPort)handler,name,rawMode,input,output);
		   }
			warning(this,CommsLMessages.portExpected(getToken()));
		}
	   return null;
	}
}

