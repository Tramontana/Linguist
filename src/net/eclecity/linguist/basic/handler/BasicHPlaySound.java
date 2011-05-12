// BasicHPlaySound.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Play a sound variable.
*/
public class BasicHPlaySound extends LHHandler
{
	private BasicHSound sound;
	private boolean looped;

	public BasicHPlaySound(int line,BasicHSound sound,boolean looped)
	{
		super(line);
		this.sound=sound;
		this.looped=looped;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		sound.play(looped);
		return pc+1;
	}
}

