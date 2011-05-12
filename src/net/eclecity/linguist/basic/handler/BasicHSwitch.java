// BasicHSwitch.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import java.util.Vector;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Handle a switch.
*/
public class BasicHSwitch extends LHHandler
{
	private Vector sw;		// the array of switch data
	private int next;			// the following instruction

	public BasicHSwitch(int line,Vector sw,int next)
	{
		super(line);
		this.sw=sw;
		this.next=next;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		long value=((LVValue)sw.elementAt(0)).getNumericValue();
		int n=1;
		while (n<sw.size())
		{
			if (((Boolean)sw.elementAt(n++)).booleanValue())			// a case
			{
				boolean result=false;
				boolean test;
				while (true)
				{
					long test1=((LVValue)sw.elementAt(n++)).getNumericValue();
					if (((Boolean)sw.elementAt(n++)).booleanValue())	// a range
					{
						long test2=((LVValue)sw.elementAt(n++)).getNumericValue();
						if (test2>test1) test=(value>=test1 && value<=test2);
						else test=(value>=test2 && value<=test1);
					}
					else test=(value==test1);									// a single value
					if (test) result=true;										// any one true is enough
					if (!((Boolean)sw.elementAt(n++)).booleanValue()) break;
				}
				int target=((Integer)sw.elementAt(n++)).intValue();
				if (result) return target;
			}
			else return ((Integer)sw.elementAt(n++)).intValue();		// the default
		}
		return next;
	}
}

