//	ServletVTemplate.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.servlet.value;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.runtime.LRProgram;
import net.eclecity.linguist.servlet.handler.ServletHTemplate;
import net.eclecity.linguist.servlet.runtime.ServletRBackground;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Return a template.
*/
public class ServletVTemplate extends LVValue
{
	private ServletHTemplate template;
	private LRProgram program;
	private LVValue fileName;
	private Hashtable params;

	public ServletVTemplate(ServletHTemplate template)
	{
		this.template=template;
	}

	public ServletVTemplate(LRProgram program,LVValue fileName,Hashtable params)
	{
		this.program=program;
		this.fileName=fileName;
		this.params=params;
	}

	public long getNumericValue() throws LRException
	{
	   return longValue();
	}

	public String getStringValue() throws LRException
	{
		if (template!=null) return template.getStringValue();
		ServletRBackground background=(ServletRBackground)program.getBackground("servlet");
		String text="";
		File file=new File(fileName.getStringValue());
		if (file.exists())
		{
			try
			{
				// Read the entire file into the text buffer
				BufferedReader in=new BufferedReader(new FileReader(file));
				String s;
				while ((s=in.readLine())!=null) text+=(s+'\n');
				in.close();
				// Remove everything except the body.  But first extract the body tag
				// so we preserve the background and other information.
				s=text.toUpperCase();
				int index0=s.indexOf("<BODY");
				int index1=s.indexOf(">",index0);
				s=text.substring(index0,index1);
				if (s.length()>6) background.setBodySpec(text.substring(index0+5,index1));
				// Now we've done with the BODY tag so it can be removed.
				index0=index1+1;				// skip past the end of the BODY tag
				index1=text.toUpperCase().indexOf("</BODY>");
				text=text.substring(index0+1,index1);
				// Substitute all the parameters
				Enumeration en=params.keys();
				while (en.hasMoreElements())
				{
					LVValue[] key=(LVValue[])en.nextElement();
					String qualifier=null;
					if (key[0]!=null) qualifier=key[0].getStringValue();
					String value=key[1].getStringValue();		// the value to replace
					String data=((LVValue)params.get(key)).getStringValue();
					if (qualifier==null)
					{
						// Do a straight substitution for every occurrence.
						// Move the position pointer on after each change.
						int n=0;
						while ((n=text.indexOf(value,n))>=0)
						{
							text=text.substring(0,n)+data+text.substring(n+value.length());
							n+=data.length();
						}
					}
					else
					{
						// Handle the special case(s)
						if (qualifier.equals("select"))		// a drop-down list
						{
							// This only works if all tags are in UPPER CASE.
							// Mark the start and end of the control and extract all its contents.
							index0=text.indexOf("<SELECT NAME=\""+value+"\">");
							index1=text.indexOf("</SELECT>",index0)+9;
							s=text.substring(index0,index1);
							// Now we have the control, so remove the existing selected value
							int index=s.indexOf(" SELECTED>");
							if (index>0) s=s.substring(0,index)+s.substring(index+9);
							index=s.indexOf("<OPTION>"+data+"</OPTION>");
							if (index>0) s=s.substring(0,index+7)+" SELECTED>"+data+s.substring(index+8+data.length());
							text=text.substring(0,index0)+s+text.substring(index1);
						}
					}
				}
			}
			catch (IOException ignored) {}
		}
		return text;
	}
}
