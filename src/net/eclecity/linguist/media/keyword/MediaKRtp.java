//	MediaKRtp.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.media.keyword;

import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.keyword.LKVariableHandler;
import net.eclecity.linguist.media.handler.MediaHRtp;

/******************************************************************************
	rtp {name} {elements}
*/
public class MediaKRtp extends LKVariableHandler
{
	/***************************************************************************
		Return an instance of the runtime type.
	*/
	public LHVariableHandler getRuntimeClass() { return new MediaHRtp(); }
}
