//	MediaKHide.java

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
import net.eclecity.linguist.media.handler.MediaHHideControlComponent;
import net.eclecity.linguist.media.handler.MediaHHideVisualComponent;
import net.eclecity.linguist.media.handler.MediaHPlayer;

/******************************************************************************
	* hide {player}
	* hide controller of {player}
*/
public class MediaKHide extends LKHandler
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
				   return new MediaHHideControlComponent(line,(MediaHPlayer)getHandler());
			   }
		   }
	  	}
	  	else if (isSymbol())
	  	{
			if (getHandler() instanceof MediaHPlayer)
			{
			   return new MediaHHideVisualComponent(line,(MediaHPlayer)getHandler());
		   }
	   }
	   return null;
	}
}

