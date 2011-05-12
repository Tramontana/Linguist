// LHEvent.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.handler;

import java.awt.AWTEvent;

public class LHEvent
{
	private LHEventSource source;
	private AWTEvent event;
	private int index;
	
	public LHEvent(LHEventSource source,AWTEvent event,int index)
	{
		this.source=source;
		this.event=event;
		this.index=index;
	}
	
	public LHEventSource getSource() { return source; }
	public AWTEvent getEvent() { return event; }
	public int getIndex() { return index; }
}
