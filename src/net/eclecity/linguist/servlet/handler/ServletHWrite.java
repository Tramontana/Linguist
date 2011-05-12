// ServletHWrite.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.servlet.handler;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Vector;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.servlet.runtime.ServletRBackground;
import net.eclecity.linguist.servlet.runtime.ServletRMessages;
import net.eclecity.linguist.servlet.runtime.ServletRServletParams;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Write the document.
*/
public class ServletHWrite extends LHHandler
{
	private LVValue filename=null;

	public ServletHWrite(int line)
	{
		this.line=line;
	}

	public ServletHWrite(int line,LVValue filename)
	{
		this.line=line;
		this.filename=filename;
	}

	public int execute() throws LRException
	{
		// Get the parameter package and the document.
		ServletRServletParams params=(ServletRServletParams)getQueueData(new ServletRServletParams().getClass());
		ServletRBackground document=(ServletRBackground)getBackground("servlet");
		Vector elements=document.getElements();
		try
		{
			OutputStream out=null;
			if (params.response==null)		// output to a file?
			{
				if (filename==null) out=System.out;
				else out=new FileOutputStream(filename.getStringValue());
			}
			else
			{
				if (document.isTextMode()) params.response.setContentType("text/plain");
				else params.response.setContentType("text/html");
				out=params.response.getOutputStream();
			}
			// Write everything to the output stream.
			PrintWriter writer=new PrintWriter(out);
			if (!document.isTextMode())
			{
				writer.println("<HTML>");
				writer.println("<HEAD>");
				writer.println(document.getHead());
				writer.println("<TITLE>"+document.getTitle()+"</TITLE>");
				writer.println("</HEAD>");
				writer.print("<BODY");
				if (document.getBodySpec()!=null) writer.print(document.getBodySpec());
				writer.println(">");
			}
			Enumeration en=elements.elements();
			while (en.hasMoreElements())
			{
				Object item=en.nextElement();
				if (item instanceof String) writer.println((String)item);
				else if (item instanceof ServletHTemplate)
					writer.print(((ServletHTemplate)item).getStringValue());
				else writer.print(((ServletHElement)item).getStringValue());
			}
			if (!document.isTextMode())
			{
				if (document.isForm()) writer.println("</FORM>");
				writer.println("</BODY></HTML>");
			}
			writer.close();
	   }
		catch (IOException e) { throw new LRException(ServletRMessages.cantWrite()); }
		return pc+1;
	}
}

