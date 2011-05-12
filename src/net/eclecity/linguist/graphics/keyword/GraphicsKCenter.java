//	GraphicsKCenter.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.keyword;

import net.eclecity.linguist.graphics.GraphicsLMessages;
import net.eclecity.linguist.graphics.handler.GraphicsHCenter;
import net.eclecity.linguist.graphics.handler.GraphicsHComponent;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLMessages;

/******************************************************************************
	center {item} at {location}
*/
public class GraphicsKCenter extends GraphicsKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		getNextToken();
		if (isSymbol())
		{
			if (getHandler() instanceof GraphicsHComponent)
			{
				getNextToken();
				if (tokenIs("at"))
				{
					return new GraphicsHCenter(line,(GraphicsHComponent)getHandler(),getNextLocation());
				}
				dontUnderstandToken();
			}
			warning(this,GraphicsLMessages.componentExpected(getToken()));
		}
		else warning(this,LLMessages.dontUnderstand(getToken()));
		return null;
	}
}

