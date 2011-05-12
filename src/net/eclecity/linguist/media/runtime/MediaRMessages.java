//	MediaRMessages.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.media.runtime;

public class MediaRMessages
{
	public static String cantLoadMedia(String name)
	{
		return "Can't load media file '"+name+"'.";
	}

	public static String notRealized(String name)
	{
		return "Media file '"+name+"' is not realized.";
	}
}
