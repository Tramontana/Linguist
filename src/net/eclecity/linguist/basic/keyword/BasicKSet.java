//	BasicKSet.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.handler.BasicHPut;
import net.eclecity.linguist.basic.handler.BasicHSetEcho;
import net.eclecity.linguist.basic.handler.BasicHSetEventName;
import net.eclecity.linguist.basic.handler.BasicHSetFilePath;
import net.eclecity.linguist.basic.handler.BasicHSetModule;
import net.eclecity.linguist.basic.handler.BasicHSetReady;
import net.eclecity.linguist.handler.LHEventSource;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHModule;
import net.eclecity.linguist.handler.LHValueHolder;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLMessages;
import net.eclecity.linguist.value.LVConstant;

/******************************************************************************
	<pre>
	set {variable}
	set {module} to parent/{string value}
	set the name of {event source} to {name}
	set the file path to {path}
	set ready
	set echo/prompt to screen/console

	[1.001 GT]  12/10/00  Pre-existing.
	</pre>
*/
public class BasicKSet extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   skip("the");
	   if (tokenIs("name"))
	   {
			// set the name of {event source} to {name}
	   	skip("of");
	   	if (isSymbol())
	   	{
	   		if (getHandler() instanceof LHEventSource)
	   		{
	   			skip("to");
  					return new BasicHSetEventName(line,(LHEventSource)getHandler(),getValue());
	   		}
	   	}
			warning(this,LLMessages.variableExpected(getToken()));
	   }
	   else if (tokenIs("file"))
	   {
			// set the file path to {path}
			getNextToken();
			if (tokenIs("path"))
			{
	   		skip("to");
	   		return new BasicHSetFilePath(line,getValue());
	   	}
	   }
	   else if (tokenIs("ready"))
	   {
			// set ready
			return new BasicHSetReady(line);
	   }
	   else if (tokenIs("echo") || tokenIs("prompt"))
	   {
			// set prompt to screen/console
			skip("to");
			boolean onScreen;
			if (tokenIs("screen")) onScreen=true;
			else onScreen=false;
			return new BasicHSetEcho(line,onScreen);
	   }
	   else if (isSymbol())
	   {
	      LHHandler h=getHandler();
	      if (h instanceof LHValueHolder)
	      {
				// set {variable}
	      	return new BasicHPut(line,new LVConstant(1),(LHValueHolder)h);
	      }
	      if (h instanceof LHModule)
	      {
				// set {module} to parent/{string value}
	      	getNextToken();
	      	if (tokenIs("to"))
	      	{
	      		getNextToken();
		      	if (tokenIs("parent"))
		      		return new BasicHSetModule(line,(LHModule)h,null);
	      		return new BasicHSetModule(line,(LHModule)h,getValue());
		      }
		      throw new LLException(LLMessages.setModuleWhat());
	      }
	   }
		warning(this,LLMessages.variableExpected(getToken()));
      return null;
	}
}

