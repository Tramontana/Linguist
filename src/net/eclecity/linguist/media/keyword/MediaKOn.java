//	MediaKOn.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.media.keyword;

import net.eclecity.linguist.handler.LHFlag;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHNoop;
import net.eclecity.linguist.handler.LHStop;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLMessages;
import net.eclecity.linguist.media.handler.MediaHOnMediaEvent;
import net.eclecity.linguist.media.handler.MediaHOnMouseClick;
import net.eclecity.linguist.media.handler.MediaHOnStopPlayer;
import net.eclecity.linguist.media.handler.MediaHPlayer;

/******************************************************************************
	* on media ready in {player} {block}
	* on media end in {player} {block}
	* on stop {player} {block}
	* on mouse click in {player} {block}
*/
public class MediaKOn extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	   if (tokenIs("media"))
	   {
	   	getNextToken();
			if (tokenIs("ready"))
			{
			   getNextToken();
				if (tokenIs("in"))
				{
				   getNextToken();
				   if (isSymbol())
				   {
						if (getHandler() instanceof MediaHPlayer)
						{
							MediaHPlayer player=(MediaHPlayer)getHandler();
							int here=getPC();
							addCommand(new LHNoop(0));
							doKeyword();
							addCommand(new LHStop(line));
							setCommandAt(new MediaHOnMediaEvent(line,player,MediaHPlayer.MEDIA_READY,getPC()),here);
							return new LHFlag();
						}
						warning(this,LLMessages.inappropriateType(getToken()));
					}
				}
			}
			if (tokenIs("end"))
			{
			   getNextToken();
				if (tokenIs("in"))
				{
				   getNextToken();
				   if (isSymbol())
				   {
						if (getHandler() instanceof MediaHPlayer)
						{
							MediaHPlayer player=(MediaHPlayer)getHandler();
							int here=getPC();
							addCommand(new LHNoop(0));
							doKeyword();
							addCommand(new LHStop(line));
							setCommandAt(new MediaHOnMediaEvent(line,player,MediaHPlayer.MEDIA_END,getPC()),here);
							return new LHFlag();
						}
						warning(this,LLMessages.inappropriateType(getToken()));
					}
				}
			}
		}
	   else if (tokenIs("stop"))
	   {
			// on stop {player} {block}
	   	getNextToken();
	   	if (isSymbol())
	   	{
				if (getHandler() instanceof MediaHPlayer)
				{
					MediaHPlayer player=(MediaHPlayer)getHandler();
					int here=getPC();
					addCommand(new LHNoop(0));
					doKeyword();
					addCommand(new LHStop(line));
					setCommandAt(new MediaHOnStopPlayer(line,player,getPC()),here);
					return new LHFlag();
				}
			   warning(this,LLMessages.inappropriateType(getToken()));
			}
	   }
	   else if (tokenIs("mouse"))
	   {
	   	getNextToken();
	   	if (tokenIs("click"))
	   	{
				// on mouse click in {player} {block}
	   		String operation=getToken();
	   		skip("in");
		   	if (isSymbol())
		   	{
					if (getHandler() instanceof MediaHPlayer)
					{
						MediaHPlayer player=(MediaHPlayer)getHandler();
						int here=getPC();
						addCommand(new LHNoop(0));
						doKeyword();
						addCommand(new LHStop(line));
						if (operation.equals("click"))
							setCommandAt(new MediaHOnMouseClick(line,player,getPC()),here);
						return new LHFlag();
					}
				   warning(this,LLMessages.inappropriateType(getToken()));
				}
			}
		}
		return null;
	}
}

