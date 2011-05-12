// ServletHSet.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.servlet.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHHashtable;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.servlet.runtime.ServletRBackground;
import net.eclecity.linguist.value.LVConstant;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Set something.
	<pre>
	[1.001 GT]  25/11/00  Pre-existing class.
	[1.002 GT]  19/05/03  Add uploader.
	</pre>
*/
public class ServletHSet extends LHHandler
{
	private ServletHElement element;
	private LVValue title;
	private LVValue headFile;
	private LVValue headText;
	private LVValue red;
	private LVValue green;
	private LVValue blue;
	private LVValue size;
	private LVValue width;
	private LVValue height;
	private LVValue text;
	private LVValue action;
	private LVValue method;
	private LVValue name;
	private LVValue value;
	private LVValue option;
	private LVValue target;
	private LVValue icon;
	private LVValue border;
	private LVValue font;
	private LVValue type=new LVConstant(ServletHElement.DEFAULT);
	private LVValue alignment=new LVConstant(ServletHElement.ALIGN_NONE);
	private LVValue valign=new LVConstant(ServletHElement.ALIGN_NONE);
	private boolean percent;
	private boolean isBold;
	private boolean isItalic;
	private boolean newWindow;
	private boolean javascript;
	private int select=SELECT_NONE;
	private ServletHTemplate template;
	private LHHashtable table;
	private LVValue source;
	private LVValue left;
	private LVValue right;
	private LVValue parameters;
	private ServletHCookie cookie;
	private LVValue expiry;
	private ServletHUploader uploader;

	/***************************************************************************
		Set the title of the document.
	*/
	public ServletHSet(int line,LVValue title)
	{
		this.line=line;
		this.title=title;
	}

	/***************************************************************************
		Set the head of the document.
	*/
	public ServletHSet(int line,LVValue head,boolean isFile)
	{
		this.line=line;
		if (isFile) headFile=head;
		else headText=head;
	}

	/***************************************************************************
		Set an attribute of an element.
	*/
	public ServletHSet(int line,ServletHElement element,LVValue attribute,int select)
	{
		this(line,element,attribute,select,false,false);
	}

	/***************************************************************************
		Set an attribute of an element.
	*/
	public ServletHSet(int line,ServletHElement element,LVValue attribute,int select,
		boolean newWindow,boolean javascript)
	{
		this.line=line;
		this.element=element;
		this.select=select;
		switch (select)
		{
		case TYPE:
			type=attribute;
			break;
		case ALIGNMENT:
			alignment=attribute;
			break;
		case VALIGN:
			valign=attribute;
			break;
		case FONT:
			font=attribute;
			break;
		case SIZE:
			size=attribute;
			break;
		case WIDTH:
			width=attribute;
			break;
		case PCWIDTH:
			width=attribute;
			percent=true;
			break;
		case HEIGHT:
			height=attribute;
			break;
		case TEXT:
			text=attribute;
			break;
		case ICON:
			icon=attribute;
			break;
		case TARGET:
			target=attribute;
			this.newWindow=newWindow;
			this.javascript=javascript;
			break;
		case ACTION:
			action=attribute;
			break;
		case METHOD:
			method=attribute;
			break;
		case NAME:
			name=attribute;
			break;
		case VALUE:
			value=attribute;
			break;
		case BORDER:
			border=attribute;
			break;
		}
	}

	/***************************************************************************
		Set the color of an element.
	*/
	public ServletHSet(int line,ServletHElement element,LVValue red,LVValue green,LVValue blue)
	{
		this.line=line;
		this.element=element;
		this.red=red;
		this.green=green;
		this.blue=blue;
		select=COLOR;
	}

	/***************************************************************************
		Set the style of a text element.
	*/
	public ServletHSet(int line,ServletHElement element,boolean isBold,boolean isItalic)
	{
		this.line=line;
		this.element=element;
		this.isBold=isBold;
		this.isItalic=isItalic;
		select=STYLE;
	}

	/***************************************************************************
		Set an option of a template.
	*/
	public ServletHSet(int line,ServletHTemplate template,LVValue option,LVValue value)
	{
		this.line=line;
		this.template=template;
		this.option=option;
		this.value=value;
	}

	/***************************************************************************
		Set the brackets or a parameter of a template.
		If the flag is false, set the brackets; else set a parameter.
	*/
	public ServletHSet(int line,ServletHTemplate template,LVValue value1,LVValue value2,boolean flag)
	{
		this.line=line;
		this.template=template;
		if (flag)
		{
			this.name=value1;
			this.value=value2;
		}
		else
		{
			this.left=value1;
			this.right=value2;
		}
	}

	/***************************************************************************
		Set the source or a set of parameters of a template.
		If the flag is false, set the source; else set the parameters.
	*/
	public ServletHSet(int line,ServletHTemplate template,LVValue value,boolean flag)
	{
		this.line=line;
		this.template=template;
		if (flag) this.parameters=value;
		else this.source=value;
	}

	/***************************************************************************
		Set a 'table' parameter of a template.
	*/
	public ServletHSet(int line,ServletHTemplate template,LVValue name,LHHashtable table)
	{
		this.line=line;
		this.template=template;
		this.name=name;
		this.table=table;
	}

	/***************************************************************************
		Set an attribute of a cookie.
	*/
	public ServletHSet(int line,ServletHCookie cookie,LVValue value,boolean flag)
	{
		this.line=line;
		this.cookie=cookie;
		if (flag) this.expiry=value;
		else this.value=value;
	}

	/***************************************************************************
		Set a session value.
	*/
	public ServletHSet(int line,LVValue name,LVValue value)
	{
		this.line=line;
		this.name=name;
		this.value=value;
	}

	/***************************************************************************
		Set the path of an uploader.
	*/
	public ServletHSet(int line,ServletHUploader uploader,LVValue value)
	{
		this.line=line;
		this.uploader=uploader;
		this.value=value;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		if (element!=null)
		{
			switch (select)
			{
			case TYPE:
				element.setElementType(type);
				break;
			case TEXT:
				element.setText(text);
				break;
			case COLOR:
				element.setColor(red,green,blue);
				break;
			case FONT:
				element.setFont(font);
				break;
			case SIZE:
				element.setSize(size);
				break;
			case STYLE:
				element.setStyle(isBold,isItalic);
				break;
			case WIDTH:
			case PCWIDTH:
				element.setWidth(width,percent);
				break;
			case HEIGHT:
				element.setHeight(height);
				break;
			case BORDER:
				element.setBorder(border);
				break;
			case ACTION:
				element.setAction(action);
				break;
			case METHOD:
				element.setMethod(method);
				break;
			case NAME:
				element.setName(name);
				break;
			case VALUE:
				element.setValue(value);
				break;
			case ICON:
				element.setIcon(icon);
				break;
			case TARGET:
				element.setTarget(target,newWindow,javascript);
				break;
			case ALIGNMENT:
				element.setAlignment(alignment);
				break;
			case VALIGN:
				element.setValign(valign);
				break;
			}
		}
		else if (template!=null)
		{
			if (source!=null) template.setSource(source);
			else if (name!=null)
			{
				if (value!=null) template.setParameter(name,value);
				else if (table!=null) template.setParameter(name,table);
			}
			else if (parameters!=null) template.setParameters(parameters);
			else if (left!=null) template.setBrackets(left,right);
			else if (option!=null) template.setOption(option,value);
		}
		else if (cookie!=null)
		{
			if (value!=null) cookie.setValue(value);
			else if (expiry!=null) cookie.setExpiry(expiry);
		}
		else if (name!=null && value!=null)
		{
			ServletRBackground background=(ServletRBackground)getBackground("servlet");
			background.putSessionValue(name,value);
		}
		else if (uploader!=null)
		{
			uploader.setPath(value);
		}
		else
		{
			ServletRBackground background=(ServletRBackground)getBackground("servlet");
			if (title!=null) background.setTitle(title.getStringValue());
			else if (headFile!=null) background.setHead(headFile.getStringValue(),true);
			else if (headText!=null) background.setHead(headText.getStringValue(),false);
		}
		return pc+1;
	}

	public static final int
		SELECT_NONE=0,
		TYPE=1,
		ALIGNMENT=2,
		VALIGN=3,
		SIZE=4,
		WIDTH=5,
		PCWIDTH=6,
		HEIGHT=7,
		TEXT=8,
		ICON=9,
		TARGET=10,
		ACTION=11,
		METHOD=12,
		NAME=13,
		VALUE=14,
		BORDER=15,
		COLOR=16,
		STYLE=17,
		FONT=18;
}

