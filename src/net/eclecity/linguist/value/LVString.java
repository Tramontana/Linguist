//	LVString.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.value;

import java.util.Vector;

import net.eclecity.linguist.runtime.LRException;


/******************************************************************************
	Handle a string built up of one or more elements in a Vector.
*/
public class LVString extends LVValue
{
	Vector items;				// the set of elements

	public LVString(Vector items)
	{
		this.items=items;
	}

	public LVString(String item)
	{
		items=new Vector();
		items.addElement(item);
	}

	public long getNumericValue() throws LRException
	{
		return longValue();
	}

	public String getStringValue() throws LRException
	{
		String s="";
		for (int n=0; n<items.size(); n++)
		{
			s+=((LVValue)items.elementAt(n)).getStringValue();
		}
		return s;
	}

}
