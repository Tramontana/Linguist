//	ServletVParameter.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.servlet.value;

import java.util.StringTokenizer;

import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.runtime.LRProgram;
import net.eclecity.linguist.servlet.handler.ServletHUploader;
import net.eclecity.linguist.servlet.runtime.ServletRServletParams;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Return the named parameter.
*/
public class ServletVParameter extends LVValue
{
	private LRProgram program;
	private ServletHUploader uploader;
	private LVValue name;

	public ServletVParameter(LRProgram program,LVValue name)
	{
		this.program=program;
		this.name=name;
	}

	public ServletVParameter(ServletHUploader uploader,LVValue name)
	{
		this.uploader=uploader;
		this.name=name;
	}

	public long getNumericValue() throws LRException
	{
	   return longValue();
	}

	public String getStringValue() throws LRException
	{
		if (program!=null)
		{
		   String name=this.name.getStringValue();
		   String param=null;
			ServletRServletParams params=
				(ServletRServletParams)program.getQueueData(new ServletRServletParams().getClass());
			// See if the servlet parameters are in the queue data
			if (params!=null)
			{
				if (params.request!=null)
				{
					// look for the parameter in the request parameter list
					param=params.request.getParameter(name);
					if (param==null)
					{
						// look for it in the path info
						String pathInfo=params.request.getPathInfo();
						if (pathInfo!=null)
						{
							int n=pathInfo.indexOf('?');
							if (n>=0)
							{
								StringTokenizer st=new StringTokenizer(pathInfo.substring(n),"&");
								while (st.hasMoreTokens())
								{
									String token=st.nextToken();
									n=token.indexOf('=');
									if (n>0 && n<token.length()-1)
									{
										if (name.equals(token.substring(0,n)))
										{
											param=token.substring(n+1);
											break;
										}
									}
								}
							}
						}
					}
				}
			}
			if (param==null)
			{
				if (params!=null)
				{
					// Try the property list
					if (params.properties!=null) param=params.properties.getProperty(name);
				}
			}
			if (param==null) param="";
			return param;
		}
		else if (uploader!=null) return uploader.getParameter(name);
		return "";
	}
}
