// ServletHTemplate.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.servlet.handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import net.eclecity.linguist.handler.LHData;
import net.eclecity.linguist.handler.LHHashtable;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.servlet.runtime.ServletRBackground;
import net.eclecity.linguist.value.LVHashtable;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	An HTML template.
	<pre>
	[1.002 GT]  30/11/02  Deal with OPTION elements.
	[1.001 GT]  25/11/00  Pre-existing class.
	</pre>
*/
public class ServletHTemplate extends LHVariableHandler
{
	private ServletRBackground background=null;

	public ServletHTemplate() {}

	public Object newElement(Object extra) { return new ServletHTemplateData(); }

	/***************************************************************************
		Set the enclosing brackets for a substitution field in this template.
	*/
	public void setBrackets(LVValue left,LVValue right) throws LRException
	{
		ServletHTemplateData myData=(ServletHTemplateData)getData();
		myData.left=left.getStringValue();
		myData.right=right.getStringValue();
	}

	/***************************************************************************
		Set the source file to be used by this template.
	*/
	public void setSource(LVValue source) throws LRException
	{
		ServletHTemplateData myData=(ServletHTemplateData)getData();
		myData.source=source.getStringValue();
	}

	/***************************************************************************
		Set an option in this template.
	*/
	public void setOption(LVValue option,LVValue value) throws LRException
	{
		ServletHTemplateData myData=(ServletHTemplateData)getData();
		String[] key=new String[2];
		key[0]=OPTION;
		key[1]=option.getStringValue();
		myData.parameters.put(key,value.getStringValue());
	}

	/***************************************************************************
		Set a parameter in this template.
	*/
	public void setParameter(LVValue name,LVValue value) throws LRException
	{
		ServletHTemplateData myData=(ServletHTemplateData)getData();
		myData.parameters.put(name.getStringValue(),value.getStringValue());
	}

	/***************************************************************************
		Set a parameter in this template from a hashtable.
	*/
	public void setParameter(LVValue name,LHHashtable table) throws LRException
	{
		ServletHTemplateData myData=(ServletHTemplateData)getData();
		myData.parameters.put(name.getStringValue(),table);
	}

	/***************************************************************************
		Set one or more parameters from a hashtable.
	*/
	public void setParameters(LVValue value) throws LRException
	{
		ServletHTemplateData myData=(ServletHTemplateData)getData();
		Hashtable table=((LVHashtable)value).getTable();
		Enumeration enumeration=table.keys();
		while (enumeration.hasMoreElements())
		{
			String name=(String)enumeration.nextElement();
			myData.parameters.put(name,table.get(name));
		}
	}

	/***************************************************************************
		Clear the parameter table in this template.
	*/
	public void clear() throws LRException
	{
		ServletHTemplateData myData=(ServletHTemplateData)getData();
		myData.parameters=new Hashtable();
	}

	/***************************************************************************
		Return the current value of this template.
	*/
	public String getStringValue() throws LRException
	{
		if (background==null) background=(ServletRBackground)program.getBackground("servlet");
		ServletHTemplateData myData=(ServletHTemplateData)getData();
		StringBuffer sb=new StringBuffer();
		String text="";
		File file=new File(myData.source);
		if (file.exists())
		{
			try
			{
				// Read the entire file into the text buffer
				BufferedReader in=new BufferedReader(new FileReader(file));
				String s;
				while ((s=in.readLine())!=null) sb.append(s+'\n');
				in.close();
				text=sb.toString();
				// Remove everything except the body.  But first extract the body tag
				// so we preserve the background and other information.
				s=text.toUpperCase();
				int index0=s.indexOf("<BODY");
				int index1=s.indexOf(">",index0);
				s=text.substring(index0,index1);
				if (s.length()>6) background.setBodySpec(text.substring(index0+5,index1));
				// Now we've done with the BODY tag so it can be removed.
				index0=index1+1;				// skip past the end of the BODY tag
				index1=text.indexOf("</BODY>");
				text=text.substring(index0+1,index1);
				// Substitute all the parameters
				Enumeration enumeration=myData.parameters.keys();
				while (enumeration.hasMoreElements())
				{
					Object key=enumeration.nextElement();
					if (key instanceof String)				// a replacement parameter
					{
						String name=(String)key;							// the old value
						String oldValue=myData.left+name+myData.right;
						Object data=myData.parameters.get(name);		// the new value
						// If an LHHashtable is found, build a SELECT object.
						// This may have an CHANGE element comprising a line of JavaScript.
						if (data instanceof LHHashtable)
						{
							Hashtable table=((LHHashtable)data).getTable();
							sb=new StringBuffer("<SELECT NAME=\""+name+"\"");
							String change=(String)table.get("CHANGE");
							if (change!=null) sb.append(" onChange=\""+change+"\"");
							sb.append(">\n");
							for (int n=0; ; n++)
							{
								s=(String)table.get(""+n);
								if (s==null) break;
								sb.append("<OPTION");
								if (s.length()>0)
								{
									if (s.charAt(0)=='@')
									{
										sb.append(" SELECTED");
										if (s.length()>0) s=s.substring(1);
										else s="";
									}
									sb.append(">"+s+"</OPTION>\n");
								}
							}
							sb.append("</SELECT>\n");
							int n=text.indexOf(oldValue);
							if (n>=0)
								text=text.substring(0,n)+sb.toString()+text.substring(n+oldValue.length());
						}
						else
						{
							String newValue=(String)data;
							// Do a straight substitution for every occurrence.
							// Move the position pointer on after each change.
							int n=0;
							while ((n=text.indexOf(oldValue,n))>=0)
							{
								text=text.substring(0,n)+newValue+text.substring(n+oldValue.length());
								n+=newValue.length();
							}
						}
					}
					else if (key instanceof String[])	// an option or other special tag
					{
						String name=((String[])key)[0];
						if (name.equals(OPTION))
						{
							String option=((String[])key)[1];
							s=text.toUpperCase();
							index0=0;
							// Look for the appropriate SELECT tag
							while ((index0=s.indexOf("<SELECT",index0))>=0)
							{
								// skip to NAME
								index0=s.indexOf("NAME",index0);
								// Find the end of the tag
								index1=text.indexOf(">",index0);
								// Check if this includes the option we want
								index0=text.indexOf(option,index0);
								if (index0<index1)
								{
									// skip past this tag then look for the end of the list
									// (as a marker)
									index0=s.indexOf(">",index0)+1;
									index1=s.indexOf("</SELECT>",index0);
									s=selectInList(text.substring(index0,index1),
										(String)myData.parameters.get(key));
									// replace the entire list
									text=text.substring(0,index0)+s+text.substring(index1);
								}
								else index0=index1;
							}
						}
					}
				}
			}
			catch (IOException ignored) {}
		}
		return text;
	}

	/***************************************************************************
		Select one item from a list by stripping out all tags then rebuilding from scratch.
		@param list the text of the list
		@param value the value to be selected
		@return the new list text
	*/
	private String selectInList(String list,String value)
	{
//		System.out.println("Process the list: "+value);
		Vector v=new Vector();
		int index0=0;
		int index1=0;
		// Find an opening brace
		while ((index0=list.indexOf("<",index1))>=index1)
		{
			// Write anything before the opening brace
			if (index0>index1) v.addElement(list.substring(index1,index0));
			// Find the matching closing brace
			index1=list.indexOf(">",index0)+1;
		}
		// Write any remaining text after the last closing brace
		if (index1<list.length()) v.addElement(list.substring(index1));
		// Tokenize
		value=value.trim();
		StringBuffer sb=new StringBuffer();
		Enumeration enumeration=v.elements();
		while (enumeration.hasMoreElements())
		{
			String s=((String)enumeration.nextElement()).trim();
			if (s.length()>0)
			{
				sb.append("<OPTION");
				if (s.equals(value)) sb.append(" SELECTED");
				sb.append(">"+s+"</OPTION>\r\n");
			}
		}
		return sb.toString();
	}

	private static final String OPTION="option";

	/***************************************************************************
		The data for a template.
	*/
	class ServletHTemplateData extends LHData
	{
		String left="";
		String right="";
		String source;
		Hashtable parameters=new Hashtable();

		ServletHTemplateData() {}
	}
}


