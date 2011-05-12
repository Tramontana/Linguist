//	MediaVPositionInPlayer.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.media.value;

import net.eclecity.linguist.media.handler.MediaHPlayer;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Return the current position in a Player.
*/
public class MediaVPositionInPlayer extends LVValue
{
	private MediaHPlayer player;

	public MediaVPositionInPlayer(MediaHPlayer player)
	{
		this.player=player;
	}

	public long getNumericValue() throws LRException
	{
		return player.getMediaTime();
	}

	public String getStringValue() throws LRException
	{
		return String.valueOf(getNumericValue());
	}
}
