// ServletHAdd.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.servlet.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.servlet.runtime.ServletRBackground;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Add something to something.
*/
public class ServletHAdd extends LHHandler
{
	private Object item=null;
	private ServletHElement element=null;

	/***************************************************************************
		Add an object to the document.
	*/
	public ServletHAdd(int line,Object item)
	{
		this.line=line;
		this.item=item;
	}

	/***************************************************************************
		Add an object to another element.
	*/
	public ServletHAdd(int line,Object item,ServletHElement element)
	{
		this.line=line;
		this.item=item;
		this.element=element;
	}

	/***************************************************************************
		Add a cookie to the response.
	*/
	public ServletHAdd(int line,ServletHCookie cookie)
	{
		this.line=line;
		this.item=cookie;
	}

	/***************************************************************************
		Add a type to an uploader.
	*/
	private ServletHUploader uploader;
	private LVValue type;

	public ServletHAdd(int line,LVValue type,ServletHUploader uploader)
	{
		this.line=line;
		this.type=type;
		this.uploader=uploader;
	}

	/***************************************************************************
		Runtime.  Do it now.
	*/
	public int execute() throws LRException
	{
		if (element!=null)
		{
			if (item!=null) element.add(item);
		}
		else if (uploader!=null) uploader.add(type);
		else
		{
			ServletRBackground background=(ServletRBackground)getBackground("servlet");
			if (item!=null) background.add(item);
		}
		return pc+1;
	}
}

