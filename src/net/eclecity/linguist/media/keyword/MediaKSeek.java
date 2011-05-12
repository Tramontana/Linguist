//	MediaKSeek.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.media.keyword;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLMessages;
import net.eclecity.linguist.media.handler.MediaHPlayer;
import net.eclecity.linguist.media.handler.MediaHSeek;

/******************************************************************************
	* seek {player} by {amount}
	* seek {player} to {position}
*/
public class MediaKSeek extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	  	getNextToken();
	  	if (isSymbol())
	  	{
			if (getHandler() instanceof MediaHPlayer)
			{
				getNextToken();
				if (tokenIs("by"))
				{
					getNextToken();
					return new MediaHSeek(line,(MediaHPlayer)getHandler(),getValue(),false);
				}
				if (tokenIs("to"))
				{
					getNextToken();
					return new MediaHSeek(line,(MediaHPlayer)getHandler(),getValue(),true);
				}
				dontUnderstandToken();
		   }
		   warning(this,LLMessages.inappropriateType(getToken()));
	   }
	   return null;
	}
}

