// BasicHSetEcho.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.basic.runtime.BasicRBackground;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Set the echo to appear on the screen or the console (default).
	<pre>
	[1.001 GT]  12/10/00  New class.
	[1.002 GT]  21/05/04  Renames from SetPrompt to SetEcho.
	</pre>
*/
public class BasicHSetEcho extends LHHandler
{
	private boolean onScreen=false;

	public BasicHSetEcho(int line,boolean onScreen)
	{
		super(line);
		this.onScreen=onScreen;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		((BasicRBackground)getBackground("basic")).setPrompt(onScreen);
		return pc+1;
	}
}

