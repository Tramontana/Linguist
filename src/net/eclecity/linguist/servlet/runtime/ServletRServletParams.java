//	ServletRServletParams.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.servlet.runtime;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletRServletParams
{
	public HttpServletRequest request;
	public HttpServletResponse response;
	public Properties properties;
	public boolean isPost;

	public ServletRServletParams() {}

	public ServletRServletParams(HttpServletRequest req, HttpServletResponse res,boolean isPost)
	{
		request=req;
		response=res;
		this.isPost=isPost;
	}

	public ServletRServletParams(Properties props,boolean isPost)
	{
		properties=props;
		this.isPost=isPost;
	}
}
