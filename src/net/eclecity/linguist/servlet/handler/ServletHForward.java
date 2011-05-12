// ServletHForward.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.servlet.handler;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.servlet.runtime.ServletRServletParams;


/******************************************************************************
	Forward the input stream.
*/
public class ServletHForward extends LHHandler
{
	public ServletHForward(int line)
	{
		this.line=line;
	}

	public int execute()
	{
		ServletRServletParams params=(ServletRServletParams)getQueueData(new ServletRServletParams().getClass());
		HttpServletRequest req=params.request;
		HttpServletResponse res=params.response;
		if (req!=null)
		{
			// Pass on all the headers
			Enumeration enumeration=req.getHeaderNames();
			while (enumeration.hasMoreElements())
			{
				String header=(String)enumeration.nextElement();
				if (header.equalsIgnoreCase("Content-Length")) continue;
				res.setHeader(header,req.getHeader(header));
			}
			
			// Pass on the response body
			String contentType=req.getContentType();
			res.setContentType(contentType);
			try
			{
				ServletInputStream in=req.getInputStream();
				ServletOutputStream out=res.getOutputStream();
				byte[] data=new byte[1024];
				int count;
				while ((count=in.read(data))!=-1)
				{
					out.write(data,0,count);
				}
				out.close();
			}
//			catch (IOException e) { throw new LRError(ServletRErrors.cantWrite()); }
			catch (IOException e) { systemOut(e.toString()); }
		}
		return pc+1;
	}
}

