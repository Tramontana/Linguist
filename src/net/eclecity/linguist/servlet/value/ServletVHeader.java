//	ServletVHeader.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.servlet.value;

import java.util.Enumeration;

import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.runtime.LRProgram;
import net.eclecity.linguist.servlet.runtime.ServletRServletParams;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Return HTTP request headers.
*/
public class ServletVHeader extends LVValue
{
	private LRProgram program;
	private LVValue name=null;

	public ServletVHeader(LRProgram program)
	{
		this.program=program;
	}

	public ServletVHeader(LRProgram program,LVValue name)
	{
		this.program=program;
		this.name=name;
	}

	public long getNumericValue() throws LRException
	{
	  return longValue();
	}

	public String getStringValue() throws LRException
	{
		ServletRServletParams params=null;
		try
		{
			params=(ServletRServletParams)program.getQueueData(new ServletRServletParams().getClass());
			if (params==null) return "";
			if (params.request==null) return "";
		}
		catch (ClassCastException e) { return ""; }
		if (name==null)
		{
			String s="";
			if (params.request!=null)
			{
				// look for the headers
				Enumeration enumeration=params.request.getHeaderNames();
				while (enumeration.hasMoreElements())
				{
					String name=(String)enumeration.nextElement();
					s+=(name+": "+params.request.getHeader(name)+"\n");
				}
			}
			return s;
		}
		return params.request.getHeader(name.getStringValue());
	}
}
