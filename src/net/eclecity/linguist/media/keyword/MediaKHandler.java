//	MediaKHandler.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.media.keyword;

import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.media.value.MediaVLocation;
import net.eclecity.linguist.media.value.MediaVScreenCenter;
import net.eclecity.linguist.media.value.MediaVSize;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	* Superclass of all media keyword handlers.
*/
public abstract class MediaKHandler extends LKHandler
{
	public MediaVLocation getLocation() throws LLException
	{
		if (tokenIs("screen"))
		{
			getNextToken();
			if (tokenIs("center") || tokenIs("centre")) return new MediaVScreenCenter();
			dontUnderstandToken();
		}
		LVValue left=getValue();
		getNextToken();
		LVValue top=getValue();
		return new MediaVLocation(left,top);
	}

	public MediaVLocation getNextLocation() throws LLException
	{
		getNextToken();
		return getLocation();
	}
	
	public MediaVSize getSize() throws LLException
	{
		LVValue width=getValue();
		getNextToken();
		LVValue height=getValue();
		return new MediaVSize(width,height);
	}

	public MediaVSize getNextSize() throws LLException
	{
		getNextToken();
		return getSize();
	}
}

