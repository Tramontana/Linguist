//	CommsLGetValue.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.comms;

import net.eclecity.linguist.comms.handler.CommsHPort;
import net.eclecity.linguist.comms.value.CommsVPort;
import net.eclecity.linguist.main.LLCompiler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLGetValue;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Get a value.
*/
public class CommsLGetValue extends LLGetValue
{
	public boolean isNumeric() { return numeric; }
	public boolean isString() { return !numeric; }

	/********************************************************************
		* Create a numeric or string value.
		*
	   *		byte from <port>
	   *		line from <port>
	*/

	public LVValue getValue(LLCompiler compiler) throws LLException
	{
		this.compiler=compiler;
		if (tokenIs("byte"))
		{
			skip("from");
			if (isSymbol())
			{
				if (getHandler() instanceof CommsHPort)
				{
					numeric=true;
					return new CommsVPort((CommsHPort)getHandler());
				}
			}
			warning(this,CommsLMessages.portExpected(getToken()));
	   }
		if (tokenIs("line"))
		{
			skip("from");
			if (isSymbol())
			{
				if (getHandler() instanceof CommsHPort)
				{
					numeric=false;
					return new CommsVPort((CommsHPort)getHandler());
				}
			}
			warning(this,CommsLMessages.portExpected(getToken()));
	   }
	   return null;
	}
}
