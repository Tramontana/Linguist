// MediaHHideControlComponent.java

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
	Hide the control component of a media player.
*/
public class MediaHHideControlComponent extends LHHandler
{
	private MediaHPlayer player;

	public MediaHHideControlComponent(int line,MediaHPlayer player)
	{
		this.line=line;
		this.player=player;
	}

	public int execute() throws LRException
	{
		player.hideControlComponent();
		return pc+1;
	}
}

