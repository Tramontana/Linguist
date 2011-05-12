//	GraphicsKHide.java

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
	hide [all] {component}
*/
public class GraphicsKHide extends GraphicsKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		getNextToken();
		boolean all=false;
		if (tokenIs("all"))
		{
			all=true;
			getNextToken();
		}
		if (isSymbol())
		{
			LHHandler handler=getHandler();
			if (handler instanceof GraphicsHComponent)
			{
				if (all) return new GraphicsHSetVisible(line,(GraphicsHComponent)handler);
				return new GraphicsHSetVisible(line,(GraphicsHComponent)handler,false);
			}
		}
		warning(this,GraphicsLMessages.componentExpected(getToken()));
		return null;
	}
}

