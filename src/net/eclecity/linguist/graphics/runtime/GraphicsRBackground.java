// GraphicsRBackground.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.runtime;

import javax.swing.RepaintManager;

import net.eclecity.linguist.runtime.LRBackground;
import net.eclecity.linguist.runtime.LRProgram;


/******************************************************************************
	Handle background actions for the swing package.
*/
public class GraphicsRBackground extends LRBackground
{
	static
	{
		RepaintManager.currentManager(null).setDoubleBufferingEnabled(true);
	}

	protected void initBackground(Object data) {}
 	public void onMessage(LRProgram p,String message) {}
	public void doFinishActions() {}
}
