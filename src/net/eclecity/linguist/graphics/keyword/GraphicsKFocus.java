//	GraphicsKFocus.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.keyword;

import net.eclecity.linguist.graphics.GraphicsLMessages;
import net.eclecity.linguist.graphics.handler.GraphicsHComponent;
import net.eclecity.linguist.graphics.handler.GraphicsHFocus;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLMessages;

/******************************************************************************
	focus {component}
*/
public class GraphicsKFocus extends GraphicsKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		getNextToken();
		if (isSymbol())
		{
			LHHandler handler=getHandler();
			if (handler instanceof GraphicsHComponent)
			{
				return new GraphicsHFocus(line,(GraphicsHComponent)handler);
			}
			warning(this,GraphicsLMessages.componentExpected(getToken()));
		}
		warning(this,LLMessages.dontUnderstand(getToken()));
		return null;
	}
}

