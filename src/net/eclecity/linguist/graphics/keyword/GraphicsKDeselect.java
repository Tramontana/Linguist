//	GraphicsKDeselect.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.keyword;

import net.eclecity.linguist.graphics.GraphicsLMessages;
import net.eclecity.linguist.graphics.handler.GraphicsHButton;
import net.eclecity.linguist.graphics.handler.GraphicsHSelect;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
	deselect {button}
*/
public class GraphicsKDeselect extends GraphicsKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		getNextToken();
		if (isSymbol())
		{
			LHHandler handler=getHandler();
			if (handler instanceof GraphicsHButton)
			{
				return new GraphicsHSelect(line,(GraphicsHButton)handler,false);
			}
		}
		warning(this,GraphicsLMessages.componentExpected(getToken()));
		return null;
	}
}

