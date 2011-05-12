//	GraphicsKFind.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.keyword;

import net.eclecity.linguist.graphics.GraphicsLMessages;
import net.eclecity.linguist.graphics.handler.GraphicsHComponent;
import net.eclecity.linguist.graphics.handler.GraphicsHFindEvent;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
	find event in {component}
*/
public class GraphicsKFind extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	   if (tokenIs("event"))
	   {
	   	skip("in");
	   	if (isSymbol())
	   	{
				LHHandler handler=getHandler();
				if (handler instanceof GraphicsHComponent)
					return new GraphicsHFindEvent(line,(GraphicsHComponent)handler);
			}
		}
		warning(this,GraphicsLMessages.componentExpected(getToken()));
		return null;
	}
}

