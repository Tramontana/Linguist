//	GraphicsKWarning.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.keyword;

import net.eclecity.linguist.graphics.handler.GraphicsHWarning;
import net.eclecity.linguist.graphics.handler.GraphicsHWindow;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	<pre>
	warning {message} [parent {window}]
	<p>
	[1.001 GT]  15/02/01  New class.
	</pre>
*/
public class GraphicsKWarning extends GraphicsKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		LVValue message=getNextValue();
		GraphicsHWindow window=null;
		getNextToken();
		if (tokenIs("parent"))
		{
			getNextToken();
			if (isSymbol())
			{
				if (getHandler() instanceof GraphicsHWindow) window=(GraphicsHWindow)getHandler();
				else inappropriateType();
			}
			else dontUnderstandToken();
		}
		else unGetToken();
		return new GraphicsHWarning(line,message,window);
	}
}

