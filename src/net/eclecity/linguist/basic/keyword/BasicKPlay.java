//	BasicKPlay.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.handler.BasicHPlaySound;
import net.eclecity.linguist.basic.handler.BasicHSound;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLMessages;

/******************************************************************************
	* play {sound} [looped]
*/
public class BasicKPlay extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	  	getNextToken();
	  	if (isSymbol())
	  	{
			if (getHandler() instanceof BasicHSound)
			{
				boolean looped=false;
				getNextToken();
				if (tokenIs("looped")) looped=true;
				else unGetToken();
			   return new BasicHPlaySound(line,(BasicHSound)getHandler(),looped);
		   }
		   warning(this,LLMessages.inappropriateType(getToken()));
	   }
	   return null;
	}
}

