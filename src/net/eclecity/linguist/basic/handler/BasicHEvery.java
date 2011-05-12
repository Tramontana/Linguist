// BasicHEvery.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Run a continuous timer.
*/
public class BasicHEvery extends LHHandler
{
	private LVValue count;			// the count
	private int scale;				// the size of each unit
	private int where;				// where to go when the timer fires
	private int next;					// the next instruction

	/***************************************************************************
		Set up the timer.
		@param line the line of the script.
		@param count the number of units for the delay.
		@param scale the size of the unit, in milliseconds.
		@param where the code to execute when the timer fires.
		@param next the next instruction to execute now.
	*/
	public BasicHEvery(int line,LVValue count,int scale,int where,int next)
	{
		super(line);
		this.count=count;
		this.scale=scale;
		this.where=where;
		this.next=next;
	}

	public int execute() throws LRException
	{
		program.addTimer(count.getIntegerValue()*scale,where,true);
		return next;
	}
}

