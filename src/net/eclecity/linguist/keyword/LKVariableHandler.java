//	LKVariableHandler.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.keyword;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
	The base class all variable handlers are built on.
*/
public abstract class LKVariableHandler extends LKHandler
{
	protected String name;
	protected int elements;

	/***************************************************************************
		The constructor.
	*/
   public LKVariableHandler() {}

	/***************************************************************************
		Handle a keyword.
		@return The handler created.
	*/
	public LHHandler handleKeyword() throws LLException
	{
		getVariableSpecification();
		LHVariableHandler handler=getRuntimeClass();
		handler.init(line,name,getPC(),elements);
		putSymbol(name,handler);
		return handler;
	}

	public abstract LHVariableHandler getRuntimeClass();

	/***************************************************************************
		Get common information about this variable.
	*/
	protected void getVariableSpecification() throws LLException
	{
	   getNextToken();
	   name=getToken();
	   getNextToken();
	   elements=1;
	   if (isConstant()) elements=(int)evaluate();
	   else unGetToken();
	}
}

