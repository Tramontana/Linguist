//	BasicKSwitch.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import java.util.Vector;

import net.eclecity.linguist.basic.handler.BasicHSwitch;
import net.eclecity.linguist.handler.LHFlag;
import net.eclecity.linguist.handler.LHGoto;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHNoop;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLMessages;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	<pre>
   switch {block}
   	[case {value [to {value]} [or {value [to {value]} ...] {block} ...]]
   	...
   	[default {block}]
	</pre>
*/
public class BasicKSwitch extends LKHandler
{
	Vector sw;				// the vector of runtime information
	Vector fixup;			// a vector of goto fixups

	public LHHandler handleKeyword() throws LLException
	{
		sw=new Vector();
		fixup=new Vector();
		int here=getPC();
		int switchLine=line;
		addCommand(new LHNoop(0));	// this is where the switch instruction will go
	   getNextToken();
		LVValue value=getValue();
      sw.addElement(value);			// first item is the value
      getNextToken();
      if (tokenIs("case"))
      {
      	doCase();
      	while (true)
			{
				getNextToken();
				if (tokenIs("case")) doCase();
				else if (tokenIs("default"))
				{
					sw.addElement(new Boolean(false));			// marks the default case
					sw.addElement(new Integer(getPC()));		// where the block starts
					doKeyword();
					break;												// must be the last
				}
				else
				{
					unGetToken();
					break;
				}
			}
      }
      for (int n=0; n<fixup.size(); n++)						// put in the goto instructions
			compiler.setCommandAt(new LHGoto(line,getPC()),
				((Integer)fixup.elementAt(n)).intValue());
		setCommandAt(new BasicHSwitch(switchLine,sw,getPC()),here);
		return new LHFlag();
	}

	private void doCase() throws LLException
	{
		sw.addElement(new Boolean(true));						// mark a case
		while (true)
		{
		   getNextToken();
			LVValue value=getValue();
			if (!value.isNumeric()) numericSwitch();
	      sw.addElement(value);									// a value to compare
	      getNextToken();
	      if (tokenIs("to"))
	      {
	      	sw.addElement(new Boolean(true));				// identify a range
			   getNextToken();
				value=getValue();										// the end of the range
				if (!value.isNumeric()) numericSwitch();
		      sw.addElement(value);								// a value to compare
	      }
	      else
	      {
	      	unGetToken();
	      	sw.addElement(new Boolean(false));				// identify a single item
	      }
	      getNextToken();
	      if (!tokenIs("or"))
	      {
	      	unGetToken();
	      	break;
	      }
	      sw.addElement(new Boolean(true));					// identify an(other) 'or'
      }
    	sw.addElement(new Boolean(false));						// identify the end of this case
		sw.addElement(new Integer(getPC()));					// where the block starts
		doKeyword();
		fixup.addElement(new Integer(getPC()));				// fix this up later
		addCommand(new LHNoop(0));									// rejoin the program
	}

	private void numericSwitch() throws LLException
	{
		throw new LLException(LLMessages.switchNotNumeric());
	}
}

