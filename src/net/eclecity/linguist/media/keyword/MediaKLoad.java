//	MediaKLoad.java

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
import net.eclecity.linguist.media.handler.MediaHLoadMedia;
import net.eclecity.linguist.media.handler.MediaHPlayer;

/******************************************************************************
	* load {player} from {media name}
*/
public class MediaKLoad extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		getNextToken();
	  	if (isSymbol())
	  	{
			if (getHandler() instanceof MediaHPlayer)
			{
				skip("from");
			   return new MediaHLoadMedia(line,(MediaHPlayer)getHandler(),getValue());
		   }
	   }
	   return null;
	}
}

