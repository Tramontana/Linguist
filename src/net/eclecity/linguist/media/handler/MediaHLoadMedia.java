// MediaHLoadMedia.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.media.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Load a media file.
*/
public class MediaHLoadMedia extends LHHandler
{
	private MediaHPlayer player;
	private LVValue media;

	public MediaHLoadMedia(int line,MediaHPlayer player,LVValue media)
	{
		this.line=line;
		this.player=player;
		this.media=media;
	}

	public int execute() throws LRException
	{
		player.load(media,pc+1);
		return 0;
	}
}

