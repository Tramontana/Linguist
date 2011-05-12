// ServletHElementData.java

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

import net.eclecity.linguist.handler.LHData;
import net.eclecity.linguist.runtime.LRException;


/***************************************************************************
	The data for an element.
	<pre>
	[1.001 GT]  25/11/00  Pre-existing class.
	</pre>
*/
public class ServletHElementData extends LHData
{
	public Vector elements=new Vector();
	public Color color=null;
	public boolean isBold=false;
	public boolean isItalic=false;
	public boolean newWindow=false;
	public boolean javascript=false;
	public int elementType=ServletHElement.DEFAULT;
	public int align=ServletHElement.ALIGN_NONE;
	public int valign=ServletHElement.ALIGN_MIDDLE;
	public int border=1;
	public int width=0;
	public int height=0;
	public int size=0;
	public boolean percent=true;
	public String target=null;
	public String action=null;
	public String method="GET";
	public String value=null;
	public String name=null;
	public String font=null;
	public String icon="";

	public ServletHElementData() {}

	/************************************************************************
		Duplicate this data item.
	*/
	public ServletHElementData duplicate() throws LRException
	{
		ServletHElementData newData=new ServletHElementData();
		Enumeration en=elements.elements();
		while (en.hasMoreElements())
		{
			Object item=en.nextElement();
			if (item instanceof String) item=new String((String)item);
			else item=((ServletHElement)item).duplicate();
			newData.elements.addElement(item);
		}
		if (color!=null) newData.color=new Color(color.getRGB());
		if (font!=null) newData.font=new String(font);
		newData.elementType=elementType;
		newData.isBold=isBold;
		newData.isItalic=isItalic;
		newData.newWindow=newWindow;
		newData.javascript=javascript;
		newData.align=align;
		newData.valign=valign;
		newData.border=border;
		newData.width=width;
		newData.height=height;
		newData.size=size;
		newData.percent=percent;
		newData.icon=new String(icon);
		if (target!=null) newData.target=new String(target);
		if (action!=null) newData.action=new String(action);
		newData.method=new String(method);
		if (name!=null) newData.name=new String(name);
		if (value!=null) newData.value=new String(value);
		return newData;
	}
}

