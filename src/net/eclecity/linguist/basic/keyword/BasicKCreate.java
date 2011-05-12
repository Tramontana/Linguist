//	BasicKCreate.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import java.util.Hashtable;

import net.eclecity.linguist.basic.BasicLMessages;
import net.eclecity.linguist.basic.handler.BasicHCreate;
import net.eclecity.linguist.basic.handler.BasicHDispatcher;
import net.eclecity.linguist.basic.handler.BasicHFile;
import net.eclecity.linguist.basic.handler.BasicHProcess;
import net.eclecity.linguist.basic.handler.BasicHTimer;
import net.eclecity.linguist.handler.LHGoto;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHNoop;
import net.eclecity.linguist.handler.LHStop;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.value.LVConstant;
import net.eclecity.linguist.value.LVValue;



/******************************************************************************
	<pre>
	create directory {name}
	create {file} {name}
	create {file} using dialog {title} [at {left} {top}]
	create {process} {name}
	create {timer} [duration {value} tick[s]/second[s]/minute[s]] [id {n}]
	create {dispatcher}
	   case {string} {block}
	   [...]
	   [default {block}]
	</pre>
*/
public class BasicKCreate extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	   if (tokenIs("directory")) return new BasicHCreate(line,getNextValue());
	  	if (isSymbol())
	  	{
			LHHandler handler=getHandler();
			if (handler instanceof BasicHFile)
			{
				getNextToken();
			   if (tokenIs("using"))
			   {
			   	// create {file} using dialog {title} [at {left} {top}]
			   	getNextToken();
			   	if (tokenIs("dialog"))
			   	{
			   		getNextToken();
			   		LVValue title=getValue();
			   		LVValue left=new LVConstant(100);
			   		LVValue top=new LVConstant(100);
			   		getNextToken();
			   		if (tokenIs("at"))
			   		{
			   			getNextToken();
			   			left=getValue();
			   			getNextToken();
			   			top=getValue();
			   		}
			   		else unGetToken();
			   		return new BasicHCreate(line,(BasicHFile)handler,title,left,top);
			   	}
			   	dontUnderstandToken();
			   }
			   // create {file} {name}
				return new BasicHCreate(line,(BasicHFile)handler,getValue());
		   }
			if (handler instanceof BasicHProcess)
			{
				// create {process} {name}
				return new BasicHCreate(line,(BasicHProcess)handler,getNextValue());
		   }
			if (handler instanceof BasicHTimer)
			{
				// create {timer} [duration {value} tick[s]/second[s]/minute[s]] [id {n}]
				LVValue duration=new LVConstant(1);
	   		int scale=1000;
	   		LVValue id=null;
	   		while (true)
	   		{
					getNextToken();
					if (tokenIs("duration"))
					{
						duration=getNextValue();
						getNextToken();
		   			if (getToken().equals("tick") || getToken().equals("ticks")) scale=10;
		   			else if (getToken().equals("second") || getToken().equals("seconds")) scale=1000;
		   			else if (getToken().equals("minute") || getToken().equals("minutes")) scale=60*1000;
		   			else unGetToken();
					}
					else if (tokenIs("id")) id=getNextValue();
					else
					{
						unGetToken();
						break;
					}
				}
				return new BasicHCreate(line,(BasicHTimer)handler,duration,scale,id);
		   }
		   if (handler instanceof BasicHDispatcher)
		   {
				// create {dispatcher}
	   		// 	case {string} {block}
	   		// 	[...]
	   		// 	[default {block]
	   		Hashtable table=new Hashtable();
	   		int skipTable=getPC();
				addCommand(new LHNoop(0));		// skip the table
	   		while (true)
	   		{
	   			getNextToken();
	   			if (tokenIs("case"))
	   			{
	   				getNextToken();
	   				table.put(getString(),new Integer(getPC()));
	   				doKeyword();
						addCommand(new LHStop(line));
	   			}
	   			else if (tokenIs("default"))
	   			{
	   				table.put("default",new Integer(getPC()));
	   				doKeyword();
						addCommand(new LHStop(line));
	   			}
	   			else
	   			{
	   				unGetToken();
	   				break;
	   			}
	   		}
				setCommandAt(new LHGoto(line,getPC()),skipTable);
				return new BasicHCreate(line,(BasicHDispatcher)handler,table);
		   }
			warning(this,BasicLMessages.fileExpected(getToken()));
		}
	   return null;
	}
}

