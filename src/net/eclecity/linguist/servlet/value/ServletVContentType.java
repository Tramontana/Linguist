//	ServletVContentType.java

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
	Return the content type.
*/
public class ServletVContentType extends LVValue
{
	private LRProgram program;

	public ServletVContentType(LRProgram program)
	{
		this.program=program;
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
					String contentType=params.request.getContentType();
					if (contentType==null) contentType="unknown";
					return contentType;
				}
			}
		}
		catch (ClassCastException ignored) {}
		return "unknown";
	}
}
