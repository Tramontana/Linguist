//	ServletVClient.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.servlet.value;

import net.eclecity.linguist.runtime.LRProgram;
import net.eclecity.linguist.servlet.runtime.ServletRServletParams;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Return the name or address of the client.
*/
public class ServletVClient extends LVValue
{
	private LRProgram program;
	private boolean flag;

	public ServletVClient(LRProgram program,boolean flag)
	{
		this.program=program;
		this.flag=flag;
	}

	public long getNumericValue()
	{
	   return 0;
	}

	public String getStringValue()
	{
		ServletRServletParams params=null;
		try
		{
			params=(ServletRServletParams)program.getQueueData(new ServletRServletParams().getClass());
			if (params!=null)
			{
				if (params.request!=null)
				{
					if (flag) return params.request.getRemoteHost();
					return params.request.getRemoteAddr();
				}
			}
		}
		catch (ClassCastException ignored) {}
		return "unknown";
	}
}
