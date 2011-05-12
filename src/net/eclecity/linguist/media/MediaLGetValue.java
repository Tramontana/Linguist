// MediaLGetValue.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.media;

import net.eclecity.linguist.main.LLCompiler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLGetValue;
import net.eclecity.linguist.main.LLMessages;
import net.eclecity.linguist.media.handler.MediaHPlayer;
import net.eclecity.linguist.media.value.MediaVDurationOfPlayer;
import net.eclecity.linguist.media.value.MediaVHeightOfPlayer;
import net.eclecity.linguist.media.value.MediaVLeftOfPlayer;
import net.eclecity.linguist.media.value.MediaVPositionInPlayer;
import net.eclecity.linguist.media.value.MediaVTopOfPlayer;
import net.eclecity.linguist.media.value.MediaVWidthOfPlayer;
import net.eclecity.linguist.value.LVValue;

public class MediaLGetValue extends LLGetValue
{
	/***************************************************************************
		* the left of {player}
		* the top of {player}
		* the width of {player}
		* the height of {player}
		* the duration/length of {player}
		* the position in {player}
	*/
	public LVValue getValue(LLCompiler compiler) throws LLException
	{
		this.compiler=compiler;
		numeric=true;
	   if (tokenIs("the"))
	   {
	   	getNextToken();
		   if (tokenIs("left"))
		   {
				// the left of {player}
		   	skip("of");
				if (isSymbol())
				{
					if (getHandler() instanceof MediaHPlayer) return new MediaVLeftOfPlayer((MediaHPlayer)getHandler());
		  			warning(this,LLMessages.inappropriateType(getToken()));
			   }
		   }
			else if (tokenIs("top"))
		   {
				// the top of {player}
		   	skip("of");
				if (isSymbol())
				{
					if (getHandler() instanceof MediaHPlayer) return new MediaVTopOfPlayer((MediaHPlayer)getHandler());
		  			warning(this,LLMessages.inappropriateType(getToken()));
			   }
		   }
		   else if (tokenIs("width"))
		   {
				// the width of {player}
		   	skip("of");
				if (isSymbol())
				{
					if (getHandler() instanceof MediaHPlayer) return new MediaVWidthOfPlayer((MediaHPlayer)getHandler());
		  			warning(this,LLMessages.inappropriateType(getToken()));
			   }
		   }
		   else if (tokenIs("height"))
		   {
				// the height of {player}
		   	skip("of");
				if (isSymbol())
				{
					if (getHandler() instanceof MediaHPlayer) return new MediaVHeightOfPlayer((MediaHPlayer)getHandler());
		  			warning(this,LLMessages.inappropriateType(getToken()));
			   }
		   }
		   else if (tokenIs("duration") || tokenIs("length"))
		   {
				// the duration of {player}
		   	skip("of");
				if (isSymbol())
				{
					if (getHandler() instanceof MediaHPlayer) return new MediaVDurationOfPlayer((MediaHPlayer)getHandler());
		  			warning(this,LLMessages.inappropriateType(getToken()));
			   }
		   }
		   else if (tokenIs("position"))
		   {
				// the position in {player}
		   	skip("in");
				if (isSymbol())
				{
					if (getHandler() instanceof MediaHPlayer) return new MediaVPositionInPlayer((MediaHPlayer)getHandler());
		  			warning(this,LLMessages.inappropriateType(getToken()));
			   }
		   }
		}
		return null;
	}
}
