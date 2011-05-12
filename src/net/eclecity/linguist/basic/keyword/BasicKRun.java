//	BasicKRun.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import java.util.Vector;

import net.eclecity.linguist.basic.handler.BasicHRun;
import net.eclecity.linguist.handler.LHConstant;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHModule;
import net.eclecity.linguist.handler.LHRun;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLMessages;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	<pre>
	run {name} [with {variable} [and {variable} ...]] [as {module}]
	run class {name}
	</pre>
*/
public class BasicKRun extends LKHandler
{
	private Vector exports;

	public LHHandler handleKeyword() throws LLException
	{
		getNextToken();
		if (tokenIs("class")) return new BasicHRun(line,getNextValue());
		LVValue name=getValue();
		LHModule module=null;
		exports=new Vector();
		while (true)
		{
			getNextToken();
			if (tokenIs("as"))
			{
				getNextToken();
				if (isSymbol())
				{
					if (getHandler() instanceof LHModule) module=(LHModule)getHandler();
					else
					{
						warning(this,LLMessages.moduleExpected(getToken()));
						return null;
					}
				}
				else
				{
					warning(this,LLMessages.moduleExpected(getToken()));
					return null;
				}
			}
			else if (tokenIs("with"))
			{
				while (true)
				{
					getNextToken();
					if (isSymbol())
					{
						LHHandler handler=getHandler();
						if (handler instanceof LHVariableHandler
							|| handler instanceof LHConstant)
							exports.addElement(handler);
						else inappropriateType();
						getNextToken();
						if (!tokenIs("and")) break;
					}
					else
					{
						warning(this,LLMessages.variableExpected(getToken()));
						return null;
					}
				}
				unGetToken();
		   }
		   else break;
		}
		unGetToken();
		compiler.addModuleToCompile(name);
      return new LHRun(line,name,exports,module);
	}
}

