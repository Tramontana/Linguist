// MediaHOnStopPlayer.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.media.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Deal with end of media play.
*/
public class MediaHOnStopPlayer extends LHHandler
{
	private MediaHPlayer player;
	private int next;

	public MediaHOnStopPlayer(int line,MediaHPlayer player,int next)
	{
		this.line=line;
		this.player=player;
		this.next=next;
	}

	public int execute() throws LRException
	{
		player.onStopPlayer(pc+1);
		return next;
	}
}

