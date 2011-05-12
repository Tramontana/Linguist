// MediaHPlay.java

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
	Play media.
*/
public class MediaHPlay extends LHHandler
{
	private MediaHPlayer player;
	private LVValue start;
	private LVValue finish;

	public MediaHPlay(int line,MediaHPlayer player,LVValue start,LVValue finish)
	{
		this.line=line;
		this.player=player;
		this.start=start;
		this.finish=finish;
	}

	public int execute() throws LRException
	{
		player.play(start,finish);
		return pc+1;
	}
}

