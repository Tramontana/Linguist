// MediaHSeek.java

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
	Reposition a (stopped) media clip.
*/
public class MediaHSeek extends LHHandler
{
	private MediaHPlayer player;
	private LVValue position;
	private boolean absolute;

	public MediaHSeek(int line,MediaHPlayer player,LVValue position,boolean absolute)
	{
		this.line=line;
		this.player=player;
		this.position=position;
		this.absolute=absolute;
	}

	public int execute() throws LRException
	{
		player.seek(position,absolute);
		return pc+1;
	}
}

