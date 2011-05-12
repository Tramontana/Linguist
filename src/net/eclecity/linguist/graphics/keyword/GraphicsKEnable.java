//	GraphicsKEnable.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.keyword;

import net.eclecity.linguist.graphics.GraphicsLMessages;
import net.eclecity.linguist.graphics.handler.GraphicsHButton;
import net.eclecity.linguist.graphics.handler.GraphicsHEnable;
import net.eclecity.linguist.graphics.handler.GraphicsHLabel;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLMessages;

/******************************************************************************
	* enable {button}/{label}
*/
public class GraphicsKEnable extends GraphicsKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		getNextToken();
		if (isSymbol())
		{
			LHHandler handler=getHandler();
			if (handler instanceof GraphicsHButton)
			{
				return new GraphicsHEnable(line,(GraphicsHButton)handler,true);
			}
			if (handler instanceof GraphicsHLabel)
			{
				return new GraphicsHEnable(line,(GraphicsHLabel)handler,true);
			}
			warning(this,GraphicsLMessages.componentExpected(getToken()));
		}
		warning(this,LLMessages.dontUnderstand(getToken()));
		return null;
	}
}

