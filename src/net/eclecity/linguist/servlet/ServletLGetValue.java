//	ServletLGetValue.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.servlet;

import java.util.Hashtable;

import net.eclecity.linguist.main.LLCompiler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLGetValue;
import net.eclecity.linguist.servlet.handler.ServletHElement;
import net.eclecity.linguist.servlet.handler.ServletHTemplate;
import net.eclecity.linguist.servlet.handler.ServletHUploader;
import net.eclecity.linguist.servlet.value.ServletVClient;
import net.eclecity.linguist.servlet.value.ServletVContentType;
import net.eclecity.linguist.servlet.value.ServletVElement;
import net.eclecity.linguist.servlet.value.ServletVHeader;
import net.eclecity.linguist.servlet.value.ServletVParameter;
import net.eclecity.linguist.servlet.value.ServletVServerName;
import net.eclecity.linguist.servlet.value.ServletVSessionID;
import net.eclecity.linguist.servlet.value.ServletVSessionValue;
import net.eclecity.linguist.servlet.value.ServletVTemplate;
import net.eclecity.linguist.value.LVStringConstant;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Get a value.
	<pre>
	parameter {name}
	parameter {name} of {uploader}
	template {file} [where [select {name}] {key} is {value} [and ...]]
	the headers
	header {name}
	encoded {value}
	the session id
	session value {name}
	the server name
	the client name
	the client address
	content type
	{element}
	<p>
	[1.001 GT]  24/11/00  Pre-existing class.
	[1.002 GT]  19/05/03  Add uploader.
	</pre>
*/
public class ServletLGetValue extends LLGetValue
{
	public LVValue getValue(LLCompiler compiler) throws LLException
	{
		this.compiler=compiler;
		numeric=true;
		if (isSymbol())
		{
			if (getHandler() instanceof ServletHTemplate)
			{
		   	return new ServletVTemplate((ServletHTemplate)getHandler());
			}
		}
		if (tokenIs("the")) getNextToken();
	   if (tokenIs("parameter"))
	   {
	   	// parameter {name} [of {uploader}]
	   	numeric=false;
	   	LVValue name=getNextValue();
	   	getNextToken();
	   	if (tokenIs("of"))
	   	{
	   		getNextToken();
	   		if (isSymbol())
	   		{
	   			if (getHandler() instanceof ServletHUploader)
	   				return new ServletVParameter((ServletHUploader)getHandler(),name);
	   		}
	   		return null;
	   	}
	   	unGetToken();
	   	return new ServletVParameter(getProgram(),name);
	   }
	   else if (tokenIs("template"))
	   {
	   	// template {file} [where [select {name}] {key} is {value} [and ...]]
	   	LVValue template=getNextValue();
	   	Hashtable params=new Hashtable();
	   	getNextToken();
	   	if (tokenIs("where"))
	   	{
	   		while (true)
	   		{
	   			LVValue[] key=new LVValue[2];
	   			key[0]=null;
	   			key[1]=null;
	   			getNextToken();
	   			if (tokenIs("select"))		// substitute in a SELECT field
	   			{
		   			key[0]=new LVStringConstant(getToken());
		   			getNextToken();
		   		}
	   			key[1]=getValue();
	   			skip("is");
	   			LVValue data=getValue();
	   			params.put(key,data);
	   			getNextToken();
	   			if (!tokenIs("and")) break;
	   		}
	   	}
	   	unGetToken();
	   	return new ServletVTemplate(getProgram(),template,params);
	   }
	   else if (tokenIs("header"))
	   {
	   	// header {name}
	   	numeric=false;
	   	return new ServletVHeader(getProgram(),getNextValue());
	   }
	   else if (tokenIs("headers"))
	   {
	   	// the headers
	   	numeric=false;
	   	return new ServletVHeader(getProgram());
	   }
	   else if (tokenIs("server"))
	   {
	   	// the server name
	   	numeric=false;
	   	getNextToken();
	   	if (tokenIs("name")) return new ServletVServerName(getProgram());
	   	dontUnderstandToken();
	   }
	   else if (tokenIs("client"))
	   {
	   	// the client name
	   	numeric=false;
	   	getNextToken();
	   	if (tokenIs("name")) return new ServletVClient(getProgram(),true);
	   	if (tokenIs("address")) return new ServletVClient(getProgram(),false);
	   	dontUnderstandToken();
	   }
	   else if (tokenIs("session"))
	   {
	   	numeric=false;
	   	getNextToken();
	   	if (tokenIs("id"))
	   	{
		   	// session id
	   		return new ServletVSessionID(getProgram());
	   	}
	   	else if (tokenIs("value"))
	   	{
	   		// session value {name}
	   		return new ServletVSessionValue(getProgram(),getNextValue());
	   	}
	   }
	   else if (tokenIs("content"))
	   {
	   	numeric=false;
	   	getNextToken();
	   	if (tokenIs("type")) return new ServletVContentType(getProgram());
	   }
	   else if (isSymbol())
	   {
	   	if (getHandler() instanceof ServletHElement)
	   	{
	   		return new ServletVElement((ServletHElement)getHandler());
	   	}
	   }
	   return null;
	}
}
