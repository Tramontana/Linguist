// lserve.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyright (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyright notice.
//=============================================================================

package net.eclecity.linguist;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.eclecity.linguist.handler.LHSystemOut;
import net.eclecity.linguist.main.LLCompiler;
import net.eclecity.linguist.main.LLMain;
import net.eclecity.linguist.main.LLVersion;
import net.eclecity.linguist.runtime.LRProgram;
import net.eclecity.linguist.servlet.runtime.ServletRServletParams;


/******************************************************************************
	A servlet that runs a Linguist script to define its behavior.
*/
public class lserve extends HttpServlet implements SingleThreadModel, LHSystemOut
{
	private LRProgram script=null;

	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
		System.out.println("\"Linguist\" servlet script engine, version "+LLVersion.version
			+".\nSee "+LLVersion.home+" for more information.");
		if (script==null)
		{
			runScript(getInitParameter("script"));
		}
	}

	/***************************************************************************
		Everything is done in the servlet doGet() and doPost() methods.
	*/
	public void doGet(HttpServletRequest req, HttpServletResponse res)
	{
		if (script!=null)
			script.handleExternalEvent(new ServletRServletParams(req,res,false));
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
	{
		if (script!=null)
			script.handleExternalEvent(new ServletRServletParams(req,res,true));
	}

	/***************************************************************************
		Return basic information about this servlet.
	*/
	public String getServletInfo()
	{
		return "Application server using a Linguist script";
	}

	private String output="";

	private String getDirectory()
	{
		String s=System.getProperty("user.dir");
		if (!s.endsWith(System.getProperty("file.separator"))) s+="/";
		return s;
	}

	/***************************************************************************
		Run the script for this servlet.
	*/
	public String runScript(String name)
	{
		if (name==null)
		{
			systemOut("No script parameter given - can't run lserve.");
			return output;
		}
		// stop the old program then run the new
		if (script!=null) script.stop();
		output="";
		String[] args;
		args=new String[1];
		args[0]=getDirectory()+"servlets/"+name+LLCompiler.compiledExtension;
		try
		{
			script=new LLMain(args,false,this,LLCompiler.compiledExtension).getProgram();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return output;
	}

	public void systemOut(String message)
	{
		output+=(message+"\n");
	}
}
