// CommsRBackground.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.comms.runtime;

import net.eclecity.linguist.runtime.LRBackground;
import net.eclecity.linguist.runtime.LRProgram;

/******************************************************************************
	Handle background actions for the comms package.
*/
public class CommsRBackground extends LRBackground
{
	protected void initBackground(Object data) {}
 	public void onMessage(LRProgram p,String message) {}
	public void doFinishActions() {}
}
