// ServletLGetCondition

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.servlet;

import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.main.LLCompiler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLGetCondition;
import net.eclecity.linguist.main.LLMessages;
import net.eclecity.linguist.servlet.condition.ServletCExists;
import net.eclecity.linguist.servlet.condition.ServletCNewSession;
import net.eclecity.linguist.servlet.handler.ServletHCookie;

/******************************************************************************
	Generate code for a condition:

	the session is new
	cookie {cookie} exists/does not exist
*/
public class ServletLGetCondition extends LLGetCondition
{
	public LCCondition getCondition(LLCompiler c) throws LLException
	{
		compiler=c;
		getNextToken();
		if (tokenIs("the")) getNextToken();
		if (tokenIs("session"))
		{
			skip("is");
			if (tokenIs("new")) return new ServletCNewSession(getProgram());
		}
		else if (tokenIs("cookie"))
		{
			getNextToken();
			if (isSymbol())
			{
				if (getHandler() instanceof ServletHCookie)
				{
					getNextToken();
					if (tokenIs("exists")) return new ServletCExists((ServletHCookie)getHandler(),true);
					else if (tokenIs("does"))
					{
						getNextToken();
						if (tokenIs("not"))
						{
							getNextToken();
							if (tokenIs("exist"))
								return new ServletCExists((ServletHCookie)getHandler(),false);
						}
						dontUnderstandToken();
					}
					else warning(this,LLMessages.dontUnderstand(getToken()));
				}
				else warning(this,ServletLMessages.cookieExpected(getToken()));
			}
		}
		return null;
	}
}
