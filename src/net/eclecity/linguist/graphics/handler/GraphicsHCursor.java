// GraphicsHCursor.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import java.awt.Cursor;

import net.eclecity.linguist.handler.LHData;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.runtime.LRException;


/******************************************************************************
	Cursor handler.
*/
public class GraphicsHCursor extends LHVariableHandler
{
	public GraphicsHCursor() {}

	public Object newElement(Object extra) { return new GraphicsHCursorData(); }
	
	public void create(int type) throws LRException
	{
		((GraphicsHCursorData)getData()).cursor=new Cursor(type);
	}
	
	public Cursor getCursor() throws LRException
	{
		return ((GraphicsHCursorData)getData()).cursor;
	}

	class GraphicsHCursorData extends LHData
	{
		Cursor cursor;
	}
}
