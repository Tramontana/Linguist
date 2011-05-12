//	MediaLMessages.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.media;

public class MediaLMessages
{
	public static String playerExpected(String token)
	{
		return "'"+token+"' is not a media player variable.";
	}

	public static String rtpExpected(String token)
	{
		return "'"+token+"' is not an rtp variable.";
	}

	public static String containerExpected(String token)
	{
		return "'"+token+"' is not a container.";
	}

	public static String noDestination()
	{
		return "No destination given.";
	}

	public static String noSource()
	{
		return "No source given.";
	}
}
