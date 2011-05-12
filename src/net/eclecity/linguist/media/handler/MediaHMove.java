// MediaHMove.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.media.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.media.value.MediaVLocation;
import net.eclecity.linguist.media.value.MediaVSize;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Move a player.
*/
public class MediaHMove extends LHHandler
{
	private MediaHPlayer player;
	private MediaVLocation location=null;
	private MediaVSize size=null;
	private LVValue value=null;
	private int direction;

	public MediaHMove(int line,MediaHPlayer player,MediaVLocation location)
	{
		this.line=line;
		this.player=player;
		this.location=location;
	}

	public MediaHMove(int line,MediaHPlayer player,MediaVSize size)
	{
		this.line=line;
		this.player=player;
		this.size=size;
	}

	public MediaHMove(int line,MediaHPlayer player,LVValue value,int direction)
	{
		this.line=line;
		this.player=player;
		this.value=value;
		this.direction=direction;
	}

	public int execute() throws LRException
	{
		if (location!=null) player.moveTo(location);
		else if (size!=null) player.moveBy(size);
		else if (value!=null) player.moveBy(value,direction);
		return pc+1;
	}
}

