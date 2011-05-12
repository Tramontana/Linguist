//	LRMessage.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.runtime;


/******************************************************************************
	A class that holds a message.
*/
public class LRMessage
{
	public String message;
	public LRProgram caller;
		
	public LRMessage(String message,LRProgram caller)
	{
		this.message=message;
		this.caller=caller;
	}
}

