// MediaHOnMediaEvent.java

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
	Deal with a media event.
*/
public class MediaHOnMediaEvent extends LHHandler
{
	private MediaHPlayer player;
	private int event;
	private int next;

	public MediaHOnMediaEvent(int line,MediaHPlayer player,int event,int next)
	{
		this.line=line;
		this.player=player;
		this.event=event;
		this.next=next;
	}

	public int execute() throws LRException
	{
		player.onMediaEvent(event,pc+1);
		return next;
	}
}

