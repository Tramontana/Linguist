// ServletHElement.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.servlet.handler;

import java.awt.Color;
import java.util.Enumeration;
import java.util.Vector;

import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.servlet.runtime.ServletRBackground;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	An HTML element.
	<pre>
	[1.001 GT]  25/11/00  Pre-existing class.
	</pre>
*/
public class ServletHElement extends LHVariableHandler
{
	protected ServletRBackground background=null;

	public ServletHElement() {}

	public Object newElement(Object extra) { return new ServletHElementData(); }

	/***************************************************************************
		Get the contents of this element as a string.
	*/
	public String getStringValue() throws LRException
	{
		ServletHElementData myData=(ServletHElementData)getData();
		StringBuffer sb=new StringBuffer();
		switch (myData.elementType)
		{
		case TEXT:
			sb.append(doAlignment(myData));
			sb.append(getBackground().getPrefixes(myData));
			sb.append(getStringValue(myData));
			sb.append(getBackground().getSuffixes(myData));
			break;
		case IMAGE:
			sb.append(doAlignment(myData));
			if (myData.target!=null) sb.append(doLink(myData));
			sb.append("<IMG SRC=\""+myData.icon+"\""+" WIDTH=\""+myData.width
				+"\" HEIGHT=\""+myData.height+"\" ALIGN=\"");
			switch (myData.align)
			{
			case ALIGN_TOP:
				sb.append("TOP");
				break;
			case ALIGN_MIDDLE:
				sb.append("MIDDLE");
				break;
			case ALIGN_BOTTOM:
				sb.append("BOTTOM");
				break;
			}
			sb.append(("\" BORDER=\""+myData.border+"\""));
			if (myData.name!=null) sb.append((" ALT=\""+myData.name+"\""));
			sb.append(">");
			if (myData.target!=null) sb.append("</A>");
			break;
		case TABLE:
			sb.append(doAlignment(myData));
			sb.append("<TABLE BORDER=\""+myData.border+"\"");
			if (myData.width!=-1)
			{
				if (myData.width==0) sb.append(" WIDTH=\"100%\"");
				else
				{
					sb.append(" WIDTH=\""+myData.width);
					if (myData.percent) sb.append("%");
					sb.append("\"");
				}
			}
			if (myData.color!=null) sb.append(" BGCOLOR=\""
				+ServletRBackground.toHex(myData.color)+"\"");
			sb.append(">\n");
			sb.append(getStringValue(myData.elements));
			sb.append("</TABLE>");
			break;
		case ROW:
			sb.append("<TR>");
			sb.append(getStringValue(myData.elements));
			sb.append("</TR>");
			break;
		case CELL:
			sb.append("<TD");
			if (myData.width>0)
			{
				sb.append((" WIDTH=\""+myData.width));
				if (myData.percent) sb.append("%");
				sb.append("\"");
			}
			switch (myData.valign)
			{
				case ALIGN_TOP: sb.append(" VALIGN=\"TOP\""); break;
				case ALIGN_MIDDLE: sb.append(" VALIGN=\"MIDDLE\""); break;
				case ALIGN_BOTTOM: sb.append(" VALIGN=\"BOTTOM\""); break;
			}
			sb.append(">");
			switch (myData.align)
			{
				case ALIGN_LEFT: sb.append(" <P ALIGN=\"LEFT\">"); break;
				case ALIGN_CENTER: sb.append(" <P ALIGN=\"CENTER\">"); break;
				case ALIGN_RIGHT: sb.append(" <P ALIGN=\"RIGHT\">"); break;
			}
			sb.append("\n");
			sb.append(getBackground().getPrefixes(myData));
			sb.append(getStringValue(myData.elements));
			sb.append(getBackground().getSuffixes(myData));
			sb.append("</TD>");
			break;
		case PARAGRAPH:
			sb.append(doAlignment(myData));
			sb.append("<P>");
			sb.append(getBackground().getPrefixes(myData));
			sb.append(getStringValue(myData.elements));
			sb.append(getBackground().getSuffixes(myData));
			sb.append("</P>");
			break;
		case FORM:
			if (getBackground().isForm()) sb.append("</FORM>\n");
			sb.append("<FORM ACTION=\""+myData.action
				+"\" METHOD=\""+myData.method+"\"");
			if (myData.method.equals("POST"))
				sb.append("ENCTYPE=\"application/x-www-form-urlencoded\"");
			sb.append(">\n");
			sb.append(getStringValue(myData.elements));
			getBackground().setForm();
			break;
		case TEXTFIELD:
			if (myData.size==0) myData.size=25;
			sb.append("<INPUT TYPE=\"TEXT\"");
			if (myData.name!=null) sb.append((" NAME=\""+myData.name+"\""));
			if (myData.value!=null) sb.append((" VALUE=\""+myData.value+"\""));
			sb.append((" SIZE=\""+myData.size+"\">"));
			break;
		case SUBMIT:
			sb.append(("<INPUT TYPE=\"SUBMIT\" NAME=\"Submit\""));
			if (myData.value!=null) sb.append((" VALUE=\""+myData.value+"\">"));
			break;
		case RESET:
			sb.append(("<INPUT TYPE=\"RESET\" NAME=\"Reset\""));
			if (myData.value!=null) sb.append((" VALUE=\""+myData.value+"\">"));
			break;
		}
		return sb.toString();
	}

	/***************************************************************************
		Get the link for this element.
	*/
	private String getStringValue(ServletHElementData myData) throws LRException
	{
		StringBuffer sb=new StringBuffer();
		if (myData.target!=null) sb.append(doLink(myData));
		sb.append(getStringValue(myData.elements));
		if (myData.target!=null) sb.append("</A>");
		return sb.toString();
	}

	/***************************************************************************
		Do the actual <A ...> link.
	*/
	private String doLink(ServletHElementData myData)
	{
		StringBuffer sb=new StringBuffer();
		sb.append("<A ");
		// Check if there's a JavaScript function
		if (myData.javascript) sb.append(myData.target);
		else sb.append("HREF=\""+myData.target);
		sb.append("\"");
		if (myData.newWindow) sb.append(" target=\"_blank\"");
		sb.append(">");
		return sb.toString();
	}

	/***************************************************************************
		Get the text for all the elements in the array given.
	*/
	private String getStringValue(Vector elements) throws LRException
	{
		StringBuffer sb=new StringBuffer();
		Enumeration en=elements.elements();
		while (en.hasMoreElements())
		{
			Object element=en.nextElement();
			if (element instanceof ServletHElement)
				sb.append(((ServletHElement)element).getStringValue());
			else sb.append((String)element);
		}
		return sb.toString();
	}

	/***************************************************************************
		Generate an alignment string.
	*/
	protected String doAlignment(ServletHElementData myData)
	{
		String text="";
		if (myData.align!=ALIGN_NONE)
		{
			switch (myData.align)
			{
				case ALIGN_LEFT: text="<LEFT>"; break;
				case ALIGN_RIGHT: text="<RIGHT>"; break;
				case ALIGN_CENTER: text="<CENTER>"; break;
			}
		}
		return text;
	}

	/***************************************************************************
		Get the background.
	*/
	public ServletRBackground getBackground() throws LRException
	{
		if (background==null) background=(ServletRBackground)getBackground("servlet");
		return background;
	}

	/***************************************************************************
		Set the type of this element.
	*/
	public void setElementType(LVValue type) throws LRException
	{
		ServletHElementData myData=(ServletHElementData)getData();
		myData.elementType=type.getIntegerValue();
	}

	/***************************************************************************
		Get the type of this element.
	*/
	public int getElementType() throws LRException
	{
		ServletHElementData myData=(ServletHElementData)getData();
		return myData.elementType;
	}

	/***************************************************************************
		Set the text of this element.
	*/
	public void setText(LVValue text) throws LRException
	{
		ServletHElementData myData=(ServletHElementData)getData();
		myData.elements=new Vector();
		String s=text.getStringValue();
		if (s.length()==0) s="&nbsp;";
		myData.elements.addElement(getElement(s));
	}

	/***************************************************************************
		Set the color of the current element.
	*/
	public void setColor(LVValue red,LVValue green,LVValue blue) throws LRException
	{
		ServletHElementData myData=(ServletHElementData)getData();
		myData.color=new Color(red.getIntegerValue(),green.getIntegerValue(),
			blue.getIntegerValue());
	}

	/***************************************************************************
		Set the font of the current element.
	*/
	public void setFont(LVValue font) throws LRException
	{
		ServletHElementData myData=(ServletHElementData)getData();
		myData.font=font.getStringValue();
	}

	/***************************************************************************
		Set the size of the current element.
	*/
	public void setSize(LVValue size) throws LRException
	{
		ServletHElementData myData=(ServletHElementData)getData();
		myData.size=size.getIntegerValue();
	}

	/***************************************************************************
		Set the style of the current element.
	*/
	public void setStyle(boolean isBold,boolean isItalic) throws LRException
	{
		ServletHElementData myData=(ServletHElementData)getData();
		myData.isBold=isBold;
		myData.isItalic=isItalic;
	}

	/***************************************************************************
		Set the alignment of this element.
	*/
	protected void setAlignment(LVValue align) throws LRException
	{
		ServletHElementData myData=(ServletHElementData)getData();
		myData.align=align.getIntegerValue();
	}

	/***************************************************************************
		Set the vertical alignment of this element.
	*/
	protected void setValign(LVValue valign) throws LRException
	{
		ServletHElementData myData=(ServletHElementData)getData();
		myData.valign=valign.getIntegerValue();
	}

	/***************************************************************************
		Set the border size of this element.
	*/
	public void setBorder(LVValue border) throws LRException
	{
		ServletHElementData myData=(ServletHElementData)getData();
		myData.border=border.getIntegerValue();
	}

	/***************************************************************************
		Set the width of this element.
	*/
	public void setWidth(LVValue width,boolean percent) throws LRException
	{
		ServletHElementData myData=(ServletHElementData)getData();
		myData.width=width.getIntegerValue();
		myData.percent=percent;
	}

	/***************************************************************************
		Set the height of this element.
	*/
	public void setHeight(LVValue height) throws LRException
	{
		ServletHElementData myData=(ServletHElementData)getData();
		myData.height=height.getIntegerValue();
	}

	/***************************************************************************
		Set the target of this element.
	*/
	public void setTarget(LVValue target,boolean newWindow,boolean javascript) throws LRException
	{
		ServletHElementData myData=(ServletHElementData)getData();
		String url=target.getStringValue();
		if (url.length()==0) url=null;
		myData.target=url;
		myData.newWindow=newWindow;
		myData.javascript=javascript;
	}

	/***************************************************************************
		Set the icon of this element.
	*/
	public void setIcon(LVValue icon) throws LRException
	{
		ServletHElementData myData=(ServletHElementData)getData();
		myData.icon=icon.getStringValue();
	}

	/***************************************************************************
		Set the action of this element.
	*/
	public void setAction(LVValue action) throws LRException
	{
		ServletHElementData myData=(ServletHElementData)getData();
		myData.action=action.getStringValue();
	}

	/***************************************************************************
		Set the method of this element.
	*/
	public void setMethod(LVValue method) throws LRException
	{
		ServletHElementData myData=(ServletHElementData)getData();
		myData.method=method.getStringValue();
	}

	/***************************************************************************
		Set the name of this element.
	*/
	public void setName(LVValue name) throws LRException
	{
		ServletHElementData myData=(ServletHElementData)getData();
		myData.name=name.getStringValue();
	}

	/***************************************************************************
		Set the value of this element.
	*/
	public void setValue(LVValue value) throws LRException
	{
		ServletHElementData myData=(ServletHElementData)getData();
		myData.value=value.getStringValue();
	}

	/***************************************************************************
		Clear the current element.
	*/
	public void clear() throws LRException
	{
		ServletHElementData myData=(ServletHElementData)getData();
		myData.elements=new Vector();
		myData.color=null;
		myData.target=null;
		myData.width=0;
	}

	/***************************************************************************
		Put an element or a string into the current element.
	*/
	public void put(Object item) throws LRException
	{
		ServletHElementData myData=(ServletHElementData)getData();
		myData.elements=new Vector();
		myData.elements.addElement(getElement(item));
	}

	/***************************************************************************
		Add an element or a string to the current element.
	*/
	public void add(Object item) throws LRException
	{
		ServletHElementData myData=(ServletHElementData)getData();
		myData.elements.addElement(getElement(item));
	}

	/***************************************************************************
		Get a duplicate copy of an item.
	*/
	public static Object getElement(Object item) throws LRException
	{
		if (item instanceof String)
			return new String((String)item);
		if (item instanceof LVValue)
			return new String(((LVValue)item).getStringValue());
		if (item instanceof ServletHTemplate)
			return new String(((ServletHTemplate)item).getStringValue());
		return ((ServletHElement)item).duplicate();
	}

	/***************************************************************************
		Duplicate an element.
	*/
	public ServletHElement duplicate() throws LRException
	{
		ServletHElement newElement=getNewElement();
		newElement.init(line,name,pc,1);
		newElement.program=program;
		newElement.setData(((ServletHElementData)getData()).duplicate());
		return newElement;
	}

	/***************************************************************************
		Get a new instance of this variable type.
	*/
	protected ServletHElement getNewElement() { return new ServletHElement(); }

	public static final int DEFAULT=0;
	public static final int TEXT=1;
	public static final int IMAGE=2;
	public static final int TABLE=3;
	public static final int ROW=4;
	public static final int CELL=5;
	public static final int PARAGRAPH=6;
	public static final int FORM=7;
	public static final int TEXTFIELD=8;
	public static final int TEXTAREA=9;
	public static final int SUBMIT=10;
	public static final int RESET=11;

	public static final int ALIGN_NONE=0;
	public static final int ALIGN_LEFT=1;
	public static final int ALIGN_RIGHT=2;
	public static final int ALIGN_CENTER=3;
	public static final int ALIGN_TOP=4;
	public static final int ALIGN_MIDDLE=5;
	public static final int ALIGN_BOTTOM=6;
}


