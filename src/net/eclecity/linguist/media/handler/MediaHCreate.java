// MediaHCreate.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.media.handler;

import net.eclecity.linguist.graphics.handler.GraphicsHComponent;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Create a new media player or other media object.
*/
public class MediaHCreate extends LHHandler
{
	private MediaHPlayer player=null;
	private GraphicsHComponent container;
	private LVValue left;
	private LVValue top;
	private LVValue width;
	private LVValue height;

	/***************************************************************************
		Create a new media player.
	*/
	public MediaHCreate(int line,MediaHPlayer player,GraphicsHComponent container,
		LVValue left,LVValue top,LVValue width,LVValue height)
	{
		this.line=line;
		this.player=player;
		this.container=container;
		this.left=left;
		this.top=top;
		this.width=width;
		this.height=height;
	}
	
	/***************************************************************************
		Create a new rtp handler.
	*/
	private MediaHRtp rtp=null;
	int type;
	int mode;
	int coding;
	LVValue rate;
	LVValue bits;
	LVValue channels;
	LVValue source;
	LVValue destination;
	
	public MediaHCreate(int line,MediaHRtp rtp,int type,int mode,int coding,LVValue rate,
		LVValue bits,LVValue channels,LVValue source,LVValue destination)
	{
		this.rtp=rtp;
		this.type=type;
		this.mode=mode;
		this.coding=coding;
		this.rate=rate;
		this.bits=bits;
		this.channels=channels;
		this.source=source;
		this.destination=destination;
	}

	public int execute() throws LRException
	{
		if (player!=null) player.create(container.getContentPane(),left,top,width,height);
		else if (rtp!=null) rtp.create(type,mode,coding,rate,bits,channels,source,destination);
		return pc+1;
	}
}

