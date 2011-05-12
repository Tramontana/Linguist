// BasicHStop.java

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
	Stop something.
*/
public class BasicHStop extends LHHandler
{
	private BasicHSound sound=null;
	private BasicHTimer timer=null;
	private BasicHProcess process=null;

	/***************************************************************************
		Stop a sound.
	*/
	public BasicHStop(int line,BasicHSound sound)
	{
		super(line);
		this.sound=sound;
	}

	/***************************************************************************
		Stop a timer.
	*/
	public BasicHStop(int line,BasicHTimer timer)
	{
		super(line);
		this.timer=timer;
	}

	/***************************************************************************
		Stop a process.
	*/
	public BasicHStop(int line,BasicHProcess process)
	{
		super(line);
		this.process=process;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		if (sound!=null) sound.stop();
		else if (timer!=null) timer.stop();
		else if (process!=null) process.close();
		return pc+1;
	}
}

