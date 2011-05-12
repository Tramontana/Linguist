// GraphicsHComponentData.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.util.Hashtable;

import javax.swing.JScrollPane;

import net.eclecity.linguist.handler.LHCallback;
import net.eclecity.linguist.handler.LHData;

	
/***************************************************************************
	Data for a component.
*/
public class GraphicsHComponentData extends LHData
{
	public Component component;
	public Container contentPane;
	public GraphicsHComponent parent;
	public Object metaComponent;
	public JScrollPane scroller=null;
	public Object extra=null;
	public int left;
	public int top;
	public int width;
	public int height;
	public LHCallback onActionCB;
	public LHCallback onMouseEnterCB;
	public LHCallback onMouseExitCB;
	public LHCallback onMouseClickCB;
	public LHCallback onMouseDownCB;
	public LHCallback onMouseUpCB;
	public LHCallback onMouseMoveCB;
	public LHCallback onMouseDragCB;
	public LHCallback onWindowCloseCB;
	public LHCallback onWindowResizeCB;
	public LHCallback onWindowIconifyCB;
	public LHCallback onWindowDeiconifyCB;
	public LHCallback onKeyPressedCB;
	public LHCallback onKeyReleasedCB;
	public LHCallback onKeyTypedCB;
	public boolean sizeSpecified=false;
	public boolean enable=true;
	public String eventName="";
	public Cursor cursor=null;
	public int status=0;
	public String description="";
	public Hashtable table=new Hashtable(11);

	public GraphicsHComponentData() {}
}
