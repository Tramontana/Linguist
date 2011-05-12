// LHComponent.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.handler;

import java.awt.Component;

import net.eclecity.linguist.runtime.LRException;


/******************************************************************************
	An interface that defines an AWT component.
*/
public interface LHComponent
{
	public Component getComponent() throws LRException;		// return the underlying AWT component
	public void notifyPlacement(LHContainer container) throws LRException;
}

