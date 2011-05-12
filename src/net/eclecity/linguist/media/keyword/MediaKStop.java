//	MediaKStop.java

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
import net.eclecity.linguist.media.handler.MediaHRtp;
import net.eclecity.linguist.media.handler.MediaHStop;

/******************************************************************************
	stop {player}/{rtp}
*/
public class MediaKStop extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	  	getNextToken();
	  	if (isSymbol())
	  	{
			if (getHandler() instanceof MediaHPlayer)
			{
			   return new MediaHStop(line,(MediaHPlayer)getHandler());
		   }
			if (getHandler() instanceof MediaHRtp)
			{
			   return new MediaHStop(line,(MediaHRtp)getHandler());
		   }
		   warning(this,LLMessages.inappropriateType(getToken()));
	   }
	   return null;
	}
}

