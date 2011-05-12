// BasicRBackground.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.runtime;

import net.eclecity.linguist.runtime.LRBackground;
import net.eclecity.linguist.runtime.LRProgram;

/******************************************************************************
	Handle background actions for the basic package.
	<pre>
	[1.001 GT]  21/05/01  Existing code.
	</pre>
*/
public class BasicRBackground extends LRBackground
{
	private boolean onScreenPrompts=false;

	protected void initBackground(Object data) {}
 	public void onMessage(LRProgram p,String message) {}
	public void doFinishActions() {}

	private String directory="";
	public void setDirectory(String directory) { this.directory=directory; }
	public String getDirectory() { return directory; }
	
	public void setPrompt(boolean onScreen) { onScreenPrompts=onScreen; }
	public boolean getPrompt() { return onScreenPrompts; }
}
