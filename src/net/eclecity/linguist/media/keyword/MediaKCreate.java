//	MediaKCreate.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.media.keyword;

import net.eclecity.linguist.graphics.handler.GraphicsHComponent;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.media.MediaLMessages;
import net.eclecity.linguist.media.handler.MediaHCreate;
import net.eclecity.linguist.media.handler.MediaHPlayer;
import net.eclecity.linguist.media.handler.MediaHRtp;
import net.eclecity.linguist.value.LVConstant;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
<pre>
	create {player} in {container}
		[at {left} {top}]
		[size {width} {height}]
	create {rtp}
		type audio
		mode capture linear/g723/gsm {rate} {bits} {channels}
		destination {url}
	create {rtp}
		type audio
		mode relay
		source {url}
		destination {url}
	create {rtp}
		type audio
		mode play
		source {url}
</pre>
*/
public class MediaKCreate extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		LVValue left=null;
		LVValue top=null;
		LVValue width=null;
		LVValue height=null;
	   getNextToken();
	  	if (!isSymbol()) return null;			// not for me
		LHHandler handler=getHandler();
		if (handler instanceof MediaHPlayer)
		{
			skip("in");
			if (isSymbol())
			{
				if (getHandler() instanceof GraphicsHComponent)
				{
					GraphicsHComponent container=(GraphicsHComponent)getHandler();
			   	while (true)
			   	{
			   		getNextToken();
			   		if (tokenIs("at"))
			   		{
			   			getNextToken();
							left=getValue();
			   			getNextToken();
							top=getValue();
			   		}
			   		else if (tokenIs("size"))
			   		{
			   			getNextToken();
							width=getValue();
			   			getNextToken();
							height=getValue();
			   		}
			   		else break;
			   	}
			   	unGetToken();
			   	return new MediaHCreate(line,(MediaHPlayer)handler,container,left,top,width,height);
			   }
			   throw new LLException(MediaLMessages.containerExpected(getToken()));
			}
			dontUnderstandToken();
	   }
	   else if (handler instanceof MediaHRtp)
	   {
	   	int type=MediaHRtp.AUDIO;
	   	int mode=MediaHRtp.CAPTURE;
	   	int coding=MediaHRtp.LINEAR;
	   	LVValue rate=new LVConstant(44100);
	   	LVValue bits=new LVConstant(16);
	   	LVValue channels=new LVConstant(2);
	   	LVValue source=null;
	   	LVValue destination=null;
	   	while (true)
	   	{
	   		getNextToken();
	   		if (tokenIs("type"))
	   		{
	   			getNextToken();
	   			if (tokenIs("audio")) type=MediaHRtp.AUDIO;
	   			else dontUnderstandToken();
	   		}
	   		else if (tokenIs("mode"))
	   		{
	   			getNextToken();
	   			if (tokenIs("capture"))
	   			{
	   				mode=MediaHRtp.CAPTURE;
	   				getNextToken();
	   				if (tokenIs("linear")) coding=MediaHRtp.LINEAR;
	   				else if (tokenIs("g723")) coding=MediaHRtp.G723;
	   				else if (tokenIs("gsm")) coding=MediaHRtp.GSM;
	   				else dontUnderstandToken();
	   				rate=getNextValue();
	   				bits=getNextValue();
	   				channels=getNextValue();
	   			}
	   			else if (tokenIs("relay"))
	   			{
	   				mode=MediaHRtp.RELAY;
	   			}
	   			else if (tokenIs("play"))
	   			{
	   				mode=MediaHRtp.PLAY;
	   			}
	   			else dontUnderstandToken();
	   		}
	   		else if (tokenIs("source")) source=getNextValue();
	   		else if (tokenIs("destination")) destination=getNextValue();
	   		else
	   		{
	   			unGetToken();
	   			break;
	   		}
	   	}
	   	if (mode!=MediaHRtp.PLAY && destination==null) throw new LLException(MediaLMessages.noDestination());
	   	if (mode!=MediaHRtp.CAPTURE && source==null) throw new LLException(MediaLMessages.noSource());
	   	return new MediaHCreate(line,(MediaHRtp)handler,type,mode,coding,rate,bits,channels,source,destination);
	   }
	   warning(this,MediaLMessages.playerExpected(getToken()));
	   return null;
	}
}

