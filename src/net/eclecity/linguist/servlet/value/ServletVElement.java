//	ServletVElement.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.servlet.value;

import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.servlet.handler.ServletHElement;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Return the value from an element.
*/
public class ServletVElement extends LVValue
{
	private ServletHElement element;

	public ServletVElement(ServletHElement element)
	{
		this.element=element;
	}

	public long getNumericValue()
	{
	   return 0;
	}

	public String getStringValue() throws LRException
	{
		return element.getStringValue();
	}
}
