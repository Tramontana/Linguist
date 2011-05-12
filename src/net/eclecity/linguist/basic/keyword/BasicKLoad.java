//	BasicKLoad.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.handler.BasicHBuffer;
import net.eclecity.linguist.basic.handler.BasicHHashtable;
import net.eclecity.linguist.basic.handler.BasicHLoad;
import net.eclecity.linguist.basic.handler.BasicHSound;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	load {buffer} from {file name}
	load {hashtable} from {file name}
	load {sound} from {media name}
*/
public class BasicKLoad extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		getNextToken();
	  	if (isSymbol())
	  	{
			if (getHandler() instanceof BasicHBuffer)
			{
				skip("from");
			   return new BasicHLoad(line,(BasicHBuffer)getHandler(),getValue());
		   }
			if (getHandler() instanceof BasicHHashtable)
			{
				BasicHHashtable table=(BasicHHashtable)getHandler();
				skip("from");
				LVValue value=null;
				try { value=getValue(); }
				catch (LLException e) { return null; }
			   return new BasicHLoad(line,table,value);
		   }
			if (getHandler() instanceof BasicHSound)
			{
				skip("from");
			   return new BasicHLoad(line,(BasicHSound)getHandler(),getValue());
		   }
	   }
	   return null;
	}
}

