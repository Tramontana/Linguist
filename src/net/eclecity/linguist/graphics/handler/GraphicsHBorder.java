// GraphicsHBorder.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import net.eclecity.linguist.graphics.value.GraphicsVColor;
import net.eclecity.linguist.graphics.value.GraphicsVFont;
import net.eclecity.linguist.handler.LHData;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Button handler.
*/
public class GraphicsHBorder extends LHVariableHandler
{
	public GraphicsHBorder() {}

	/***************************************************************************
		Create a new object of the correct type for this variable.
	*/
	public Object newElement(Object extra) { return new GraphicsHBorderData(); }

	public void createEmptyBorder(LVValue top,LVValue left,LVValue bottom,LVValue right) throws LRException
	{
		((GraphicsHBorderData)getData()).border=new EmptyBorder(top.getIntegerValue(),
			left.getIntegerValue(),bottom.getIntegerValue(),right.getIntegerValue());
	}
	
	public void createEtchedBorder(int etchType,GraphicsVColor highlight,GraphicsVColor shadow) throws LRException
	{
		EtchedBorder border;
		if (highlight==null) border=new EtchedBorder(etchType);
		else border=new EtchedBorder(etchType,highlight.getColor(),shadow.getColor());
		((GraphicsHBorderData)getData()).border=border;
	}
	
	public void createBevelBorder(int bevelType,GraphicsVColor highlight,GraphicsVColor shadow) throws LRException
	{
		BevelBorder border;
		if (highlight==null) border=new BevelBorder(bevelType);
		else border=new BevelBorder(bevelType,highlight.getColor(),shadow.getColor());
		((GraphicsHBorderData)getData()).border=border;
	}
	
	public void createLineBorder(GraphicsVColor color,LVValue thickness) throws LRException
	{
		((GraphicsHBorderData)getData()).border=new LineBorder(color.getColor(),
			thickness.getIntegerValue());
	}
	
	public void createTitledBorder(LVValue text,int justification,
		int position,GraphicsVFont font,GraphicsVColor color) throws LRException
	{
		TitledBorder border=new TitledBorder(text.getStringValue());
		border.setTitleJustification(justification);
		border.setTitlePosition(position);
		border.setTitleFont(font.getFont());
		border.setTitleColor(color.getColor());
		((GraphicsHBorderData)getData()).border=border;
	}
	
	public Border getBorder() throws LRException
	{
		return ((GraphicsHBorderData)getData()).border;
	}
	
	public static final int EMPTY_BORDER=0;
	public static final int ETCHED_BORDER=1;
	public static final int BEVEL_BORDER=2;
	public static final int LINE_BORDER=3;
	public static final int TITLED_BORDER=4;
	
	class GraphicsHBorderData extends LHData
	{
		Border border;
		
		GraphicsHBorderData() {}
	}
}
