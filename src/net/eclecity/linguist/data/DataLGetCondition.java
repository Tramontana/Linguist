// DataLGetCondition

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.data;

import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.data.condition.DataCExists;
import net.eclecity.linguist.data.condition.DataCMore;
import net.eclecity.linguist.data.handler.DataHRecord;
import net.eclecity.linguist.main.LLCompiler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLGetCondition;

/******************************************************************************
	Generate code for a condition:
	<pre>
	[no] more {record}
	{record} exists
	{record} does not exist
	<p>
	[1.001 GT]  04/10/00  Pre-existing.
	</pre>
*/
public class DataLGetCondition extends LLGetCondition
{
	public LCCondition getCondition(LLCompiler c) throws LLException
	{
		compiler=c;
		getNextToken();
		if (tokenIs("more"))
		{
			getNextToken();
			if (isSymbol())
			{
				if (getHandler() instanceof DataHRecord)
				{
					return new DataCMore((DataHRecord)getHandler());
				}
			}
			warning(this,DataLMessages.recordExpected(getToken()));
		}
		else if (isSymbol())
		{
			if (getHandler() instanceof DataHRecord)
			{
				getNextToken();
				if (tokenIs("exists")) return new DataCExists((DataHRecord)getHandler(),true);
				if (tokenIs("does"))
				{
					getNextToken();
					if (tokenIs("not"))
					{
						getNextToken();
						if (tokenIs("exist"))
							return new DataCExists((DataHRecord)getHandler(),false);
					}
				}
				dontUnderstandToken();
			}
			warning(this,DataLMessages.recordExpected(getToken()));
		}
		return null;
	}
}
