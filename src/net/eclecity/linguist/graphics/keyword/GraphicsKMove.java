// GraphicsKMove.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.keyword;

import net.eclecity.linguist.graphics.GraphicsLMessages;
import net.eclecity.linguist.graphics.handler.GraphicsHComponent;
import net.eclecity.linguist.graphics.handler.GraphicsHMove;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLMessages;

/*******************************************************************************
 * move {item} up/down/left/right [by] {value}/{size} move {item} to {location}
 */
public class GraphicsKMove extends GraphicsKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		getNextToken();
		if (isSymbol())
		{
			if (getHandler() instanceof GraphicsHComponent)
			{
				GraphicsHComponent component = (GraphicsHComponent) getHandler();
				int direction = 0;
				getNextToken();
				if (tokenIs("left")) direction = GraphicsHComponent.LEFT;
				else if (tokenIs("right")) direction = GraphicsHComponent.RIGHT;
				else if (tokenIs("up")) direction = GraphicsHComponent.UP;
				else if (tokenIs("down")) direction = GraphicsHComponent.DOWN;
				else unGetToken();
				getNextToken();
				if (tokenIs("to")) { return new GraphicsHMove(line, component,
						getNextLocation()); }
				if (tokenIs("by")) getNextToken();
				if (direction != 0) return new GraphicsHMove(line, component,
						getValue(), direction);
				return new GraphicsHMove(line, component, getSize());
			}
			warning(this, GraphicsLMessages.componentExpected(getToken()));
		}
		else warning(this, LLMessages.dontUnderstand(getToken()));
		return null;
	}
}

