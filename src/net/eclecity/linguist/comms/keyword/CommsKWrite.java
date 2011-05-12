//	CommsKWrite.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.comms.keyword;

import net.eclecity.linguist.comms.CommsLMessages;
import net.eclecity.linguist.comms.handler.CommsHPort;
import net.eclecity.linguist.comms.handler.CommsHWrite;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	write {value} to {port}
*/
public class CommsKWrite extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	   LVValue value=null;
	   try { value=getValue(); } catch (LLException e) { return null; }
	   skip("to");
   	if (isSymbol())
   	{
   		if (getHandler() instanceof CommsHPort)
				return new CommsHWrite(line,(CommsHPort)getHandler(),value);
		}
		warning(this,CommsLMessages.portExpected(getToken()));
	   return null;
	}
}

