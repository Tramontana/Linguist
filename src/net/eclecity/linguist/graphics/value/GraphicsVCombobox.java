//	GraphicsVCombobox.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.value;

import net.eclecity.linguist.graphics.handler.GraphicsHCombobox;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Return the current selection of a combo box.
	<pre>
	[1.001 GT]  10/09/01  New class.
	</pre>
*/
public class GraphicsVCombobox extends LVValue
{
	private GraphicsHCombobox combo;

	public GraphicsVCombobox(GraphicsHCombobox combo)
	{
		this.combo=combo;
	}

	public long getNumericValue() throws LRException
	{
	   return longValue();
	}

	public String getStringValue()
	{
		return combo.getValue();
	}
}
