//	LKHandler.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.keyword;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.main.LLCMethods;
import net.eclecity.linguist.main.LLCompiler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
	The base class all keyword handlers are built on.
	Most of this deals with variables; other keywords require
	little support.
*/
public abstract class LKHandler extends LLCMethods
{
	protected int line;
	protected String name;
	protected boolean isArray=false;
	protected boolean isAlias=false;

	/***************************************************************************
		The constructor.
	*/
   public LKHandler() {}

	/***************************************************************************
		Compile the current token.
		@param c the compiler object that contains the token.
		@return The handler created.
	*/
	public LHHandler compile(LLCompiler c) throws LLException
	{
	   	compiler=c;
	   	line=getCurrentLino();
	   	return handleKeyword();
	}

	/***************************************************************************
		Handle a keyword.
		Implement this in the handler class.
		@return The Object created.
	*/
	protected abstract LHHandler handleKeyword() throws LLException;
}

