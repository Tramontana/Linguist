//	BasicKOn.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.handler.BasicHOn;
import net.eclecity.linguist.basic.handler.BasicHTimer;
import net.eclecity.linguist.handler.LHFlag;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHModule;
import net.eclecity.linguist.handler.LHNoop;
import net.eclecity.linguist.handler.LHStop;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLMessages;

/******************************************************************************
	on message {block}
	on exit {module} {block}
	on event {block}
	on {timer} {block}
*/
public class BasicKOn extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	   if (tokenIs("message"))
	   {
			// on message {block}
			int here=getPC();
			addCommand(new LHNoop(0));
			doKeyword();
			addCommand(new LHStop(line));
			setCommandAt(new BasicHOn(line,true,getPC()),here);
			return new LHFlag();
		}
	   else if (tokenIs("exit"))
	   {
			// on exit {module} {block}
	   	getNextToken();
	   	if (isSymbol())
	   	{
	   		if (getHandler() instanceof LHModule)
	   		{
	   			LHModule module=(LHModule)getHandler();
					int here=getPC();
					addCommand(new LHNoop(0));
					doKeyword();
					addCommand(new LHStop(line));
					setCommandAt(new BasicHOn(line,module,getPC()),here);
					return new LHFlag();
				}
				warning(this,LLMessages.inappropriateType(getToken()));
			}
			warning(this,LLMessages.dontUnderstand(getToken()));
		}
		else if (tokenIs("event"))
		{
			// on event {block}
			int here=getPC();
			addCommand(new LHNoop(0));
			doKeyword();
			addCommand(new LHStop(line));
			setCommandAt(new BasicHOn(line,getPC()),here);
			return new LHFlag();
		}
		else if (isSymbol())
		{
			if (getHandler() instanceof BasicHTimer)
			{
				// on {timer} {block}
				BasicHTimer timer=(BasicHTimer)getHandler();
				int here=getPC();
				addCommand(new LHNoop(0));
				doKeyword();
				addCommand(new LHStop(line));
				setCommandAt(new BasicHOn(line,timer,getPC()),here);
				return new LHFlag();
			}
		}
		return null;
	}
}

