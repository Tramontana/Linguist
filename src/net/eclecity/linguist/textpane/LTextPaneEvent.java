// LTextPaneEvent.java

package net.eclecity.linguist.textpane;

import java.awt.AWTEvent;

/******************************************************************************
	A special event type for the LTextPane.
*/
public class LTextPaneEvent extends AWTEvent
{
	private LTextPane pane;
	private Object data;

	public LTextPaneEvent(LTextPane pane,int eventID,Object data)
	{
		super(pane,eventID);
		this.pane=pane;
		this.data=data;
	}
	
	public LTextPane getTextPane() { return pane; }
	public String getTag() { return (String)data; }
	
	public static final int DEFINE_TAG=0;
	public static final int MOUSE_ENTER=1;
	public static final int MOUSE_EXIT=2;
	public static final int MOUSE_CLICK=3;
}
