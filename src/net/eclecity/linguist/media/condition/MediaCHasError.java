//	MediaCHasError.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.media.condition;

import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.media.handler.MediaHRtp;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Check if an RTP returned an error.
*/
public class MediaCHasError extends LCCondition
{
	private MediaHRtp rtp;

	public MediaCHasError(MediaHRtp rtp)
	{
		this.rtp=rtp;
	}

	public boolean test() throws LRException
	{
		return rtp.hasError();
	}
}
