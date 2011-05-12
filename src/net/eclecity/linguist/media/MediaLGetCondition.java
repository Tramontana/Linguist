// MediaLGetCondition

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.media;

import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.main.LLCompiler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLGetCondition;
import net.eclecity.linguist.media.condition.MediaCHasError;
import net.eclecity.linguist.media.handler.MediaHRtp;

/******************************************************************************
	Generate code for a condition:
	<pre>
	{rtp} has error

	[1.001 GT]  13/08/01  New class.
	</pre>
*/
public class MediaLGetCondition extends LLGetCondition
{
	public LCCondition getCondition(LLCompiler c) throws LLException
	{
		compiler=c;
		getNextToken();
		if (isSymbol())
		{
			if (getHandler() instanceof MediaHRtp)
			{
				getNextToken();
				if (tokenIs("has"))
				{
					getNextToken();
					if (tokenIs("error"))
					{
						return new MediaCHasError((MediaHRtp)getHandler());
					}
				}
				dontUnderstandToken();
			}
			warning(this,MediaLMessages.rtpExpected(getToken()));
		}
		return null;
	}
}
