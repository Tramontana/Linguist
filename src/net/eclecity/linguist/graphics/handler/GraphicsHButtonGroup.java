// GraphicsHButtonGroup.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;

import net.eclecity.linguist.graphics.runtime.GraphicsRMessages;
import net.eclecity.linguist.handler.LHData;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.runtime.LRException;


/******************************************************************************
	Button group handler.
	<pre>
	[1.001 GT]  15/02/01  New class.
	</pre>
*/
public class GraphicsHButtonGroup extends LHVariableHandler
{
	public GraphicsHButtonGroup() {}

	/***************************************************************************
		Construct a new private data object.
	*/
	public Object newElement(Object extra) { return new GraphicsHButtonGroupData(); }

	/***************************************************************************
		Create a button group.
	*/
	public void create() throws LRException
	{
		((GraphicsHButtonGroupData)getData()).group=new ButtonGroup();
	}
	
	/***************************************************************************
		Add a button to this group.
	*/
	public void add(AbstractButton b) throws LRException 
	{
		if (b instanceof JRadioButton || b instanceof JRadioButtonMenuItem)
			((GraphicsHButtonGroupData)getData()).group.add(b); 
		else throw new LRException(GraphicsRMessages.notRadioButton());
	}
	
	/***************************************************************************
		Inner class to hold data about this object.
	*/
	class GraphicsHButtonGroupData extends LHData
	{
		ButtonGroup group;
		
		GraphicsHButtonGroupData() {}
	}
}
