//	MediaKPlay.java

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
import net.eclecity.linguist.media.handler.MediaHPlay;
import net.eclecity.linguist.media.handler.MediaHPlayer;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	* play {player} [from {start}] [to {finish}]
*/
public class MediaKPlay extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	  	getNextToken();
	  	if (isSymbol())
	  	{
			if (getHandler() instanceof MediaHPlayer)
			{
				MediaHPlayer player=(MediaHPlayer)getHandler();
				LVValue start=null;
				LVValue finish=null;
				getNextToken();
				if (tokenIs("from"))
				{
					getNextToken();
					start=getValue();
				}
				else unGetToken();
				getNextToken();
				if (tokenIs("to"))
				{
					getNextToken();
					finish=getValue();
				}
				else unGetToken();
			   return new MediaHPlay(line,player,start,finish);
		   }
		   warning(this,LLMessages.inappropriateType(getToken()));
	   }
	   return null;
	}
}

