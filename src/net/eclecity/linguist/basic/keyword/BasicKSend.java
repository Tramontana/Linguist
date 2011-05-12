//	BasicKSend.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.BasicLMessages;
import net.eclecity.linguist.basic.handler.BasicHDispatcher;
import net.eclecity.linguist.basic.handler.BasicHSend;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHModule;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.value.LVStringConstant;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	send [{message}] to parent
	send [{message}] to {module}
	send [{message}] to package "{package}"
	send [{message}] to module "{module}"
	send {string} to {dispatcher}
	send mail {message} from {name} to {name} subject {subject} [using {smtphost}]
*/
public class BasicKSend extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		LVValue message=null;
	   getNextToken();
	   if (tokenIs("mail"))
	   {
	   	message=getNextValue();
	   	skip("from");
	   	LVValue from=getValue();
	   	skip("to");
	   	LVValue to=getValue();
	   	skip("subject");
	   	LVValue subject=getValue();
	   	getNextToken();
	   	LVValue using=null;
	   	if (tokenIs("using")) using=getNextValue();
	   	else unGetToken();
	   	return new BasicHSend(line,message,from,to,subject,using);
	   }
      message=new LVStringConstant("");
	   if (!tokenIs("to"))
	   {
	   	try { message=getValue(); }
	   	catch (LLException e) { return null; }
      	getNextToken();
      }
      if (tokenIs("to"))
      {
      	getNextToken();
      	if (tokenIs("parent"))
      	{
      		// send [{message}] to parent
      		return new BasicHSend(line,message);
      	}
      	if (tokenIs("package"))
      	{
      		getNextToken();
      		// send [{message}] to package {name}
	      	String[] packages=getPackages();
	      	for (int n=0; n<packages.length; n++)
	      	{
	      		if (packages[n].equals(getToken()))
	      			return new BasicHSend(line,message,getToken());
	      	}
	      	throw new LLException(BasicLMessages.unknownPackage(getToken()));
      	}
      	if (tokenIs("module"))
      	{
      		// send [{message}] to module {name}
      		return new BasicHSend(line,message,getNextValue());
      	}
	      if (isSymbol())
	      {
	      	if (getHandler() instanceof LHModule)
	      	{
	      		// send [{message}] to {module}
	      		return new BasicHSend(line,message,(LHModule)getHandler());
	      	}
	      	if (getHandler() instanceof BasicHDispatcher)
	      	{
	      		// send {string} to {dispatcher}
	      		return new BasicHSend(line,message,(BasicHDispatcher)getHandler());
	      	}
	      	// Assume that what follows is an expression.
	      	// If an error pass it on to the next package.
	      	try
	      	{
	      		return new BasicHSend(line,message,getValue());
	      	}
	      	catch (LLException e) { return null; }
	      }
	   }
	   return null;
	}
}

