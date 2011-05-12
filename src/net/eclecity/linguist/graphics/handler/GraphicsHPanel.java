// GraphicsHPanel.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;

import net.eclecity.linguist.graphics.value.GraphicsVLocation;
import net.eclecity.linguist.graphics.value.GraphicsVSize;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Panel handler.
	<pre>
	[1.001 GT]  19/10/00  New class.
	</pre>
*/
public class GraphicsHPanel extends GraphicsHComponent
{
	public GraphicsHPanel() {}

	/***************************************************************************
		Create the panel.
	*/
	public void create(GraphicsHComponent container,GraphicsVLocation location,GraphicsVSize size)
		throws LRException
	{
		LPanel panel=new LPanel();
		if (location!=null) panel.setLocation(location.getLocation());
		if (size!=null) panel.setFixedSize(size.getWidth(),size.getHeight());
		panel.setBorder(null);
		create(container,panel,panel,true);
		if (container!=null) container.add(this);
	}

	/***************************************************************************
		Set the location of the component.
	*/
	public void setLocation(LVValue left,LVValue top) throws LRException
	{
		JComponent c=(JComponent)getComponentData().component;
		c.setLocation(left.getIntegerValue(),top.getIntegerValue());
	}
	public void setLocation(GraphicsVLocation location) throws LRException
	{
		JComponent c=(JComponent)getComponentData().component;
		c.setLocation(location.getLeft(),location.getTop());
	}
	
	/***************************************************************************
		Set the size of the component.
	*/
	public void setSize(GraphicsVSize size) throws LRException
	{
		setSize(size.getWidth(),size.getHeight());
	}
	public void setSize(LVValue width,LVValue height) throws LRException
	{
		setSize(width.getIntegerValue(),height.getIntegerValue());
	}
	public void setSize(int width,int height) throws LRException
	{
		LPanel panel=(LPanel)getComponentData().component;
		panel.setFixedSize(width,height);
	}

	/***************************************************************************
		Set the background of the panel.
	*/
	public void setBackground(GraphicsHImage image) throws LRException
	{
		setBackground(image.getIcon());
	}
	
	public void setBackground(LVValue name) throws LRException
	{
		setBackground(new ImageIcon(name.getStringValue()));
	}
	
	private void setBackground(ImageIcon icon) throws LRException
	{
		GraphicsHComponentData myData=getComponentData();
		LPanel panel=(LPanel)getComponentData().component;
		panel.icon=icon;
		int width=icon.getIconWidth();
		int height=icon.getIconHeight();
		setSize(width,height);
		if (myData.scroller!=null)
		{
			myData.scroller.getHorizontalScrollBar().setUnitIncrement(width/100);
			myData.scroller.getVerticalScrollBar().setUnitIncrement(height/100);
		}
		panel.repaint();
	}
	
	/***************************************************************************
		A private Panel class to allow a background to be set.
	*/
	private class LPanel extends JPanel
	{
		ImageIcon icon=null;
		boolean fixedSize=false;

		LPanel() {}
		
		public void paint(Graphics g)
		{
			if (icon!=null) g.drawImage(icon.getImage(),0,0,this);
			else super.paint(g);
		}
		
		public void setSize(int width,int height)
		{
			if (!fixedSize)
			{
				super.setSize(width,height);
				Dimension d=new Dimension(width,height);
				setPreferredSize(d);
				setMinimumSize(d);
			}
		}
		
		public void setSize(Dimension d)
		{
			setSize(d.width,d.height);
		}
		
		void setFixedSize(int width,int height)
		{
			fixedSize=false;
			setSize(width,height);
			fixedSize=true;
		}
	}
}
