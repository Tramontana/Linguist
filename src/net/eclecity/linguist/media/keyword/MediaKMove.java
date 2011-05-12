//	MediaKMove.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.media.keyword;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLMessages;
import net.eclecity.linguist.media.MediaLMessages;
import net.eclecity.linguist.media.handler.MediaHMove;
import net.eclecity.linguist.media.handler.MediaHPlayer;

/******************************************************************************
	* move {item} up/down/left/right by {value}
	* move {item} to {location}
*/
public class MediaKMove extends MediaKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		getNextToken();
		if (isSymbol())
		{
			if (getHandler() instanceof MediaHPlayer)
			{
				MediaHPlayer component=(MediaHPlayer)getHandler();
				int direction=0;
				getNextToken();
				if (tokenIs("left")) direction=MediaHPlayer.LEFT;
				else if (tokenIs("right")) direction=MediaHPlayer.RIGHT;
				else if (tokenIs("up")) direction=MediaHPlayer.UP;
				else if (tokenIs("down")) direction=MediaHPlayer.DOWN;
				else unGetToken();
				getNextToken();
				if (tokenIs("by"))
				{
					if (direction!=0) return new MediaHMove(line,component,getNextValue(),direction);
					return new MediaHMove(line,component,getNextSize());
				}
				else if (tokenIs("to"))
				{
					return new MediaHMove(line,component,getNextLocation());
				}
				else dontUnderstandToken();
			}
			throw new LLException(MediaLMessages.playerExpected(getToken()));
		}
		warning(this,LLMessages.dontUnderstand(getToken()));
		return null;
	}
}

