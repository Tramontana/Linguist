// BasicKGet.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.BasicLMessages;
import net.eclecity.linguist.basic.handler.BasicHBuffer;
import net.eclecity.linguist.basic.handler.BasicHGet;
import net.eclecity.linguist.basic.handler.BasicHHashtable;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHModule;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.value.LVConstant;
import net.eclecity.linguist.value.LVValue;

/*******************************************************************************
 * get {buffer} using dialog {title} [at {left} {top}] get {variable} from
 * {hashtable} as {key} get {module} timestamp {value} get {hashtable} from
 * {address}
 */
public class BasicKGet extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		getNextToken();
		if (isSymbol())
		{
			LHVariableHandler handler = (LHVariableHandler) getHandler();
			getNextToken();
			if (tokenIs("using"))
			{
				if (handler instanceof BasicHBuffer)
				{
					// get {buffer} using dialog {title} [at {left} {top}]
					getNextToken();
					if (tokenIs("dialog"))
					{
						getNextToken();
						LVValue title = getValue();
						LVValue left = new LVConstant(100);
						LVValue top = new LVConstant(100);
						getNextToken();
						if (tokenIs("at"))
						{
							getNextToken();
							left = getValue();
							getNextToken();
							top = getValue();
						}
						else unGetToken();
						return new BasicHGet(line, (BasicHBuffer) handler, title,
								left, top);
					}
					dontUnderstandToken();
				}
			}
			else if (tokenIs("from"))
			{
				// get {hashtable} from {address}
				// get {variable} from {hashtable} as {key}
				if (handler instanceof BasicHHashtable) { return new BasicHGet(
						line, (BasicHHashtable) handler, getNextValue()); }
				getNextToken();
				if (isSymbol())
				{
					if (getHandler() instanceof BasicHHashtable)
					{
						skip("as");
						return new BasicHGet(line, (BasicHHashtable) getHandler(),
								handler, getValue());
					}
					warning(this, BasicLMessages.tableExpected(getToken()));
				}
			}
			else if (tokenIs("timestamp"))
			{
				if (handler instanceof LHModule) return new BasicHGet(line,
						(LHModule) handler, getNextValue());
			}
		}
		return null;
	}
}

