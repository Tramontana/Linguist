//	BasicKWrite.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.BasicLMessages;
import net.eclecity.linguist.basic.handler.BasicHFile;
import net.eclecity.linguist.basic.handler.BasicHWrite;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	write [line] {value} to {file}
	write [line] {value} to file {name}
*/
public class BasicKWrite extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	   boolean newline=false;
	   LVValue value=null;
	   if (tokenIs("line"))
	   {
	   	newline=true;
	   	getNextToken();
	   }
	   try { value=getValue(); } catch (LLException e) { return null; }
	   skip("to");
   	if (isSymbol())
   	{
   		if (getHandler() instanceof BasicHFile)
				return new BasicHWrite(line,(BasicHFile)getHandler(),value,newline);
			warning(this,BasicLMessages.fileExpected(getToken()));
		}
		else if (tokenIs("file")) return new BasicHWrite(line,value,getNextValue(),newline);
	   return null;
	}
}

