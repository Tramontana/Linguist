// LHCallback.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.handler;

import net.eclecity.linguist.runtime.LRProgram;

/******************************************************************************
	A class that holds a callback and the program it runs in.
	<pre>
	[1.001 GT]  20/11/00  New class.
	</pre>
*/
public class LHCallback implements java.io.Serializable
{
	private LRProgram program;
	private int callback;

	public LHCallback(LRProgram program,int callback)
	{
		this.program=program;
		this.callback=callback;
	}
	
	public LRProgram getProgram() { return program; }
	public int getCallback() { return callback; }
}

