//	GraphicsVKeyChar.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.value;

import java.awt.event.KeyEvent;

import net.eclecity.linguist.handler.LHEvent;
import net.eclecity.linguist.runtime.LRProgram;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Return the character last typed.
*/
public class GraphicsVKeyChar extends LVValue
{
	LRProgram program;

	public GraphicsVKeyChar(LRProgram program)
	{
		this.program=program;
	}

	public long getNumericValue()
	{
		return 0;
	}

	public String getStringValue()
	{
		LHEvent event=(LHEvent)program.getQueueData();
		KeyEvent evt=(KeyEvent)event.getEvent();
		return ""+evt.getKeyChar();
	}
}
