// LHContainer.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.handler;

import java.awt.Container;

import net.eclecity.linguist.runtime.LRException;


/******************************************************************************
	An interface that defines an AWT container.
*/
public interface LHContainer extends LHComponent
{
	public void place(LHComponent c,Object position) throws LRException;		// place an AWT component in this container
	public Container getContainer() throws LRException;								// return the underlying AWT container
}

