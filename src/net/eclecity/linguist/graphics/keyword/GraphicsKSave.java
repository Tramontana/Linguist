//	GraphicsKSave.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.keyword;

import net.eclecity.linguist.graphics.GraphicsLMessages;
import net.eclecity.linguist.graphics.handler.GraphicsHLabel;
import net.eclecity.linguist.graphics.handler.GraphicsHSave;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
	<pre>
	save {label} as {filename}
	<p>
	[1.001 GT]  22/04/03  New class.
	</pre>
*/
public class GraphicsKSave extends GraphicsKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		getNextToken();
		if (isSymbol())
		{
			if (getHandler() instanceof GraphicsHLabel)
			{
				skip("as");
				return new GraphicsHSave(line,(GraphicsHLabel)getHandler(),getValue());
			}
		}
		warning(this,GraphicsLMessages.labelExpected(getToken()));
		return null;
	}
}

