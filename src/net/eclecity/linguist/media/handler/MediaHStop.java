// MediaHStop.java

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
	Stop a media player.
*/
public class MediaHStop extends LHHandler
{
	private MediaHPlayer player=null;
	private MediaHRtp rtp=null;

	public MediaHStop(int line,MediaHPlayer player)
	{
		this.line=line;
		this.player=player;
	}

	public MediaHStop(int line,MediaHRtp rtp)
	{
		this.line=line;
		this.rtp=rtp;
	}

	public int execute() throws LRException
	{
		if (player!=null) player.stop();
		else if (rtp!=null) rtp.stop();
		return pc+1;
	}
}

