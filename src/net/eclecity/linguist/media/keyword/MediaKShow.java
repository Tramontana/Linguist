//	MediaKShow.java

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
import net.eclecity.linguist.media.handler.MediaHPlayer;
import net.eclecity.linguist.media.handler.MediaHShowControlComponent;
import net.eclecity.linguist.media.handler.MediaHShowVisualComponent;

/******************************************************************************
	* show {player}
	* show controller of {player}
*/
public class MediaKShow extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	  	getNextToken();
	  	if (tokenIs("controller"))
	  	{
	  		skip("of");
		  	if (isSymbol())
		  	{
				if (getHandler() instanceof MediaHPlayer)
				{
				   return new MediaHShowControlComponent(line,(MediaHPlayer)getHandler());
			   }
		   }
	  	}
	  	else if (isSymbol())
	  	{
			if (getHandler() instanceof MediaHPlayer)
			{
			   return new MediaHShowVisualComponent(line,(MediaHPlayer)getHandler());
		   }
	   }
	   return null;
	}
}

