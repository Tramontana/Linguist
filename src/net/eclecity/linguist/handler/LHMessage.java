// LHMessage.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.handler;


/******************************************************************************
	A class that handles a message.
*/
public class LHMessage implements java.io.Serializable
{
	public LHMessageSource ms;
	public Object code;
	public Object data;
	public LHCallback target;

	public LHMessage() {	}
	
	public static final Object MESSAGE=new Object();
}

