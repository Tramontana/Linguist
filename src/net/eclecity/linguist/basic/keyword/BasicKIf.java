//	BasicKIf.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import java.util.Vector;

import net.eclecity.linguist.basic.handler.BasicHIf;
import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.handler.LHFlag;
import net.eclecity.linguist.handler.LHGoto;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHNoop;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;


/******************************************************************************
	<pre>
   if {condition} {block}
   	[and/or {condition} {block} ...]
   	[else {block}]
	</pre>
*/
public class BasicKIf extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		String conjunction="";
		Vector targetArray=new Vector();
		Vector conditionArray=new Vector();
		targetArray.addElement(new Integer(getPC()));
		addCommand(new LHNoop(0));					// first if
		LCCondition condition=getCondition();
		conditionArray.addElement(condition);
		getNextToken();
		if (tokenIs("and") || tokenIs("or")) conjunction=getToken();
		while (tokenIs(conjunction))
		{
			targetArray.addElement(new Integer(getPC()));
			addCommand(new LHNoop(0));			// another and
			conditionArray.addElement(getCondition());
			getNextToken();
		}
		unGetToken();
		int thenTarget=getPC();
		doKeyword();
		int elseTarget=getPC();
		getNextToken();
		if (tokenIs("else"))
		{
			int skipElse=getPC();
			addCommand(new LHNoop(0));			// goto after 'then' block skips else
			elseTarget++;
			doKeyword();
			setCommandAt(new LHGoto(line,getPC()),skipElse);
		}
		else unGetToken();
		for (int n=0; n<targetArray.size(); n++)
		{
			int target=((Integer)targetArray.elementAt(n)).intValue();
			condition=(LCCondition)conditionArray.elementAt(n);
			if (n==targetArray.size()-1)
				setCommandAt(new BasicHIf(line,condition,thenTarget,elseTarget),target);
			else
			{
				if (conjunction.equals("and"))
					setCommandAt(new BasicHIf(line,condition,target+1,elseTarget),target);
				else
					setCommandAt(new BasicHIf(line,condition,thenTarget,target+1),target);
			}
		}
		return new LHFlag();
	}
}

