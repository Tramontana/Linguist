//	GraphicsVLocation.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.value;

import java.awt.Point;

import net.eclecity.linguist.graphics.handler.GraphicsHComponent;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVConstant;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Hold a location.
*/
public class GraphicsVLocation extends LVValue
{
	private GraphicsHComponent component=null;
	private LVValue left;
	private LVValue top;
	
	public GraphicsVLocation () {}

	public GraphicsVLocation(GraphicsHComponent component)
	{
		this.component=component;
	}

	public GraphicsVLocation(LVValue left,LVValue top)
	{
		this.left=left;
		this.top=top;
	}

	public GraphicsVLocation(int left,int top)
	{
		this.left=new LVConstant(left);
		this.top=new LVConstant(top);
	}

	public int getLeft() throws LRException
	{
		if (component!=null) return component.getLeft();
		if (left!=null) return left.getIntegerValue();
		return 0;
	}
	
	public int getTop() throws LRException
	{
		if (component!=null) return component.getTop();
		if (top!=null) return top.getIntegerValue();
		return 0;
	}
	
	public Point getLocation() throws LRException
	{
		if (component!=null) return component.getLocation();
		if (left!=null && top!=null)
			return new Point(left.getIntegerValue(),top.getIntegerValue());
		return new Point(0,0);
	}
	
	public long getNumericValue() { return 0; }
	public String getStringValue() { return ""; }
}
