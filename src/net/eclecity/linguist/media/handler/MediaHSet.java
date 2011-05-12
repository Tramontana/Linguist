// MediaHSet.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.media.handler;

import java.awt.Cursor;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;


/******************************************************************************
	Set an attribute of a media player.
*/
public class MediaHSet extends LHHandler
{
	private MediaHPlayer player;
	private Cursor cursor=null;

	public MediaHSet(int line,MediaHPlayer player,Cursor cursor)
	{
		this.line=line;
		this.player=player;
		this.cursor=cursor;
	}

	public int execute() throws LRException
	{
		if (cursor!=null) player.setCursor(cursor);
		return pc+1;
	}
}

