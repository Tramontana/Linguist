//	GraphicsVTarget.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.value;

import net.eclecity.linguist.graphics.handler.GraphicsHTextPanel;
import net.eclecity.linguist.handler.LHEvent;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.runtime.LRProgram;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Return the target of a hot link.
*/
public class GraphicsVTarget extends LVValue
{
	LRProgram program;

	public GraphicsVTarget(LRProgram program)
	{
		this.program=program;
	}

	public long getNumericValue()
	{
		return 0;
	}

	public String getStringValue() throws LRException
	{
		try
		{
			LHEvent evt=(LHEvent)program.getQueueData();
			GraphicsHTextPanel panel=(GraphicsHTextPanel)evt.getSource();
			int index=evt.getIndex();
			return (String)panel.getExtra(index);
		}
		catch (ClassCastException ignored) {}
		return "";
	}
}
