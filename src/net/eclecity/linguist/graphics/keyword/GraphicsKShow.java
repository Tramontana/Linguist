//	GraphicsKShow.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.keyword;

import net.eclecity.linguist.graphics.GraphicsLMessages;
import net.eclecity.linguist.graphics.handler.GraphicsHComponent;
import net.eclecity.linguist.graphics.handler.GraphicsHSetVisible;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
	show {component} [at {left} {top}]
*/
public class GraphicsKShow extends GraphicsKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		getNextToken();
		if (isSymbol())
		{
			LHHandler handler=getHandler();
			if (handler instanceof GraphicsHComponent)
			{
				getNextToken();
				if (tokenIs("at"))
				{
					return new GraphicsHSetVisible(line,(GraphicsHComponent)handler,getNextValue(),getNextValue(),true);
				}
				 unGetToken();
				return new GraphicsHSetVisible(line,(GraphicsHComponent)handler,true);
			}
		}
		warning(this,GraphicsLMessages.componentExpected(getToken()));
		return null;
	}
}

