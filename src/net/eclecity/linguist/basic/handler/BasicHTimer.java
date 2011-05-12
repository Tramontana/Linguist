// BasicHTimer.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHData;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.runtime.LRProgram;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	A timer variable.
*/
public class BasicHTimer extends LHVariableHandler
{
	public Object newElement(Object extra) { return new BasicHTimerData(); }

	/***************************************************************************
		Tell callers this type doesn't return a value.
	*/
	public boolean hasValue() { return false; }

	/***************************************************************************
		Create a new timer.
	*/
	public void create(LVValue value,int scale,LVValue id) throws LRException
	{
		BasicHTimerData myData=(BasicHTimerData)getData();
		myData.duration=value.getIntegerValue()*scale;
		myData.program=program;
		if (id!=null) myData.id=id.getIntegerValue();
	}

	/***************************************************************************
		Set up the timer callback.
	*/
	public void onFire(int callback) throws LRException
	{
		BasicHTimerData myData=(BasicHTimerData)getData();
		myData.callback=callback;
	}

	/***************************************************************************
		Start the timer.
	*/
	public void start() throws LRException
	{
		BasicHTimerData myData=(BasicHTimerData)getData();
		myData.start();
	}

	/***************************************************************************
		Stop the timer without firing it.
	*/
	public void stop() throws LRException
	{
		BasicHTimerData myData=(BasicHTimerData)getData();
		myData.stop();
	}

	/***************************************************************************
		Get the ID of this timer.
	*/
	public int getID() throws LRException
	{
		BasicHTimerData myData=(BasicHTimerData)getData();
		return myData.id;
	}

	/***************************************************************************
		Cancel a timer by ID.
	*/
	public static void cancel(LRProgram program,LVValue id) throws LRException
	{
		BasicHTimerData timer=(BasicHTimerData)program.getObjectData().get(new Integer(id.getIntegerValue()));
		if (timer!=null) timer.stop();
	}

	/***************************************************************************
		A private class that holds data about a timer.
	*/
	class BasicHTimerData extends LHData implements Runnable
	{
		int duration=1000;
		LRProgram program=null;
		int callback=0;
		int id=0;
		Thread myThread=null;

		BasicHTimerData() {}

		public void start()
		{
			stop();
			(myThread=new Thread(this)).start();
			if (program!=null) program.getObjectData().put(new Integer(id),this);
		}

		public void stop()
		{
			if (myThread!=null)
			{
				myThread.interrupt();
				myThread=null;
			}
		}

		public void run()
		{
			try
			{
				Thread.sleep(duration);
				if (callback!=0)
				{
					if (program!=null) program.addQueue(callback,this);
					myThread=null;
				}
			}
			catch (InterruptedException ignored) {}
		}
	}
}

