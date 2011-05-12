// GraphicsHFrame.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import net.eclecity.linguist.graphics.runtime.GraphicsRMessages;
import net.eclecity.linguist.graphics.value.GraphicsVLocation;
import net.eclecity.linguist.graphics.value.GraphicsVSize;
import net.eclecity.linguist.handler.LHData;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Frame handler.<br>
	This wraps a JFrame and gives access to much of its functionality.
	<p><pre>
	[1.001 GT]  26/08/00  Created.
	</pre>
*/
public class GraphicsHFrame extends GraphicsHComponent
{
	public GraphicsHFrame() {}

	public void create(LVValue title,GraphicsVLocation location,GraphicsVSize size,
		boolean resizable) throws LRException
	{
		JFrame frame=new JFrame(title.getStringValue());
		if (location!=null) frame.setLocation(location.getLocation());
		if (size==null) size=new GraphicsVSize(640,480);
		frame.setSize(size.getSize());
		if (!resizable) frame.setResizable(false);
		frame.addWindowListener(this);

		GraphicsHFrameData myData=new GraphicsHFrameData(this);
		myData.frame=frame;
		myData.location=location;
		myData.size=size;
		getComponentData().extra=myData;
		create(null,frame,frame.getContentPane(),false);
	}

	/***************************************************************************
		Set the visibility of this Frame.
	*/
	public void setVisible(boolean visible) throws LRException
	{
		if (visible)
		{
			GraphicsHFrameData myData=getMyData();
			if (myData.location==null)
			{
				Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
				myData.frame.setLocation((screenSize.width-myData.size.getWidth())/2,
					(screenSize.height-myData.size.getHeight())/2);
				myData.frame.validate();
			}
		}
		super.setVisible(visible);
	}

	public void setTitle(LVValue title) throws LRException
	{
		getJFrame().setTitle(title.getStringValue());
	}

	public void addComponent(JComponent c) throws LRException
	{
		getJFrame().getContentPane().add(c);
	}

	public void addComponent(JComponent c,String constraints) throws LRException
	{
		getJFrame().getContentPane().add(c,constraints);
	}

	private JFrame getJFrame() throws LRException
	{
		Frame frame=getFrame();
		if (frame instanceof JFrame) return (JFrame)frame;
		throw new LRException(GraphicsRMessages.notJFrame(getName()));
	}

	/***************************************************************************
		Add a menu to the menubar.
	*/
	public void addMenu(GraphicsHMenu menu) throws LRException
	{
		JFrame frame=getJFrame();
		JMenuBar mb=frame.getJMenuBar();
		if (mb==null) frame.setJMenuBar(mb=new JMenuBar());
		mb.add((JMenu)menu.getComponent());
//		frame.validate();
	}

	public void setLocation(GraphicsVLocation location) throws LRException
	{
		getFrame().setLocation(location.getLocation());
	}

	public void moveTo(LVValue left,LVValue top) throws LRException
	{
		getFrame().setLocation(left.getIntegerValue(),top.getIntegerValue());
	}

	public void moveLeft(LVValue distance) throws LRException
	{
		JFrame frame=getJFrame();
		int left=frame.getLocation().x;
		int top=frame.getLocation().y;
		frame.setLocation(left-distance.getIntegerValue(),top);
	}

	public void moveRight(LVValue distance) throws LRException
	{
		JFrame frame=getJFrame();
		int left=frame.getLocation().x;
		int top=frame.getLocation().y;
		frame.setLocation(left+distance.getIntegerValue(),top);
	}

	public void moveUp(LVValue distance) throws LRException
	{
		JFrame frame=getJFrame();
		int left=frame.getLocation().x;
		int top=frame.getLocation().y;
		frame.setLocation(left,top-distance.getIntegerValue());
	}

	public void moveDown(LVValue distance) throws LRException
	{
		JFrame frame=getJFrame();
		int left=frame.getLocation().x;
		int top=frame.getLocation().y;
		frame.setLocation(left,top+distance.getIntegerValue());
	}

	public int getLeft() throws LRException
	{
		return getJFrame().getLocation().x;
	}

	public int getRight() throws LRException
	{
		return getLeft()+getJFrame().getSize().width-1;
	}

	public int getTop() throws LRException
	{
		return getJFrame().getLocation().y;
	}

	public int getBottom() throws LRException
	{
		return getTop()+getJFrame().getSize().height-1;
	}

	/***************************************************************************
		Dispose of this window.
	*/
	public void dispose() throws LRException
	{
		getJFrame().dispose();
		super.dispose();
	}

	private GraphicsHFrameData getMyData() throws LRException
	{
		return (GraphicsHFrameData)getComponentData().extra;
	}

	class GraphicsHFrameData extends LHData
	{
		GraphicsHFrame owner;
		GraphicsVLocation location;
		GraphicsVSize size;
		JFrame frame;

		GraphicsHFrameData(GraphicsHFrame owner)
		{
			this.owner=owner;
		}
	}
}
