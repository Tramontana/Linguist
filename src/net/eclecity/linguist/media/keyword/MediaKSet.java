//	MediaKSet.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.media.keyword;

import java.awt.Cursor;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.media.MediaLMessages;
import net.eclecity.linguist.media.handler.MediaHPlayer;
import net.eclecity.linguist.media.handler.MediaHSet;


/******************************************************************************
	set the cursor of {player} to default/hand/wait
*/
public class MediaKSet extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		skip("the");
	   if (tokenIs("cursor"))
	   {
			// set the cursor of {player} to default/hand/wait
			MediaHPlayer player=null;
			getNextToken();
			if (tokenIs("of"))
			{
				getNextToken();
	   		if (isSymbol())
	   		{
	   			if (getHandler() instanceof MediaHPlayer) player=(MediaHPlayer)getHandler();
	   			else
	   			{
	   				warning(this,MediaLMessages.playerExpected(getToken()));
	   				return null;
	   			}
	   		}
	   		else
	   		{
	   			warning(this,MediaLMessages.playerExpected(getToken()));
	   			return null;
	   		}
	   	}
	   	skip("to");
	   	int cursorType=Cursor.DEFAULT_CURSOR;
	   	if (tokenIs("default")) cursorType=Cursor.DEFAULT_CURSOR;
	   	else if (tokenIs("hand")) cursorType=Cursor.HAND_CURSOR;
	   	else if (tokenIs("wait")) cursorType=Cursor.WAIT_CURSOR;
  			return new MediaHSet(line,player,new Cursor(cursorType));
  		}
	   return null;
	}
}

