// GraphicsHStyle.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import net.eclecity.linguist.graphics.value.GraphicsVFont;
import net.eclecity.linguist.handler.LHData;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVStringConstant;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Style handler.
	This maintains information about a style tag used in a textpanel.
*/
public class GraphicsHStyle extends LHVariableHandler implements GraphicsHTextComponent
{
	public GraphicsHStyle() {}

	public Object newElement(Object extra) { return new GraphicsHStyleData(); }
	
	public void setText(LVValue text) throws LRException { ((GraphicsHStyleData)getData()).text=text; }
	public void setFont(GraphicsVFont font) throws LRException { ((GraphicsHStyleData)getData()).font=font; }
	public void setCursor(GraphicsHCursor cursor) throws LRException { ((GraphicsHStyleData)getData()).cursor=cursor; }
	public void setTarget(String s) throws LRException { ((GraphicsHStyleData)getData()).target=s; }

	public void setHorizontalTextAlignment(int a) {}	// required for GraphicsHTextComponent
	public void setVerticalTextAlignment(int a) {}		// required for GraphicsHTextComponent

	public String getText() throws LRException { return ((GraphicsHStyleData)getData()).text.getStringValue(); }
	public Font getFont() throws LRException { return ((GraphicsHStyleData)getData()).font.getFont(); }
	public Color getColor() throws LRException { return ((GraphicsHStyleData)getData()).font.getColor(); }
	public Cursor getCursor() throws LRException
	{
		GraphicsHCursor cursor=((GraphicsHStyleData)getData()).cursor;
		if (cursor!=null) return cursor.getCursor();
		return new Cursor(Cursor.DEFAULT_CURSOR);
	}
	public int getCallback() throws LRException { return ((GraphicsHStyleData)getData()).callback; }
	public String getTarget() throws LRException { return ((GraphicsHStyleData)getData()).target; }
	
	public void onAction(int cb) throws LRException { ((GraphicsHStyleData)getData()).callback=cb; }

	class GraphicsHStyleData extends LHData
	{
		LVValue text=new LVStringConstant("");		// the text of the markup tag
		GraphicsVFont font=null;							// the font and color to use
		GraphicsHCursor cursor=null;					// the cursor when in this style
		String target="";							// the most recent tag with this style
		int callback=0;							// non-zero if this is a hotlink
	}
}
