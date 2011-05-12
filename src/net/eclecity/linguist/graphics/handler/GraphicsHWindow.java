// GraphicsHWindow.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JWindow;

import net.eclecity.linguist.graphics.runtime.GraphicsRMessages;
import net.eclecity.linguist.graphics.value.GraphicsVColor;
import net.eclecity.linguist.graphics.value.GraphicsVLocation;
import net.eclecity.linguist.graphics.value.GraphicsVScreenCenter;
import net.eclecity.linguist.graphics.value.GraphicsVSize;
import net.eclecity.linguist.handler.LHData;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Window handler.
	<pre>
	[1.003 GT]  25/09/01  Add mask windows.
	[1.002 GT]  30/10/00  Remove setting a background image for a window.
	[1.001 GT]  19/10/00  Pre-existing.
	</pre>
*/
public class GraphicsHWindow extends GraphicsHComponent
{
	private static Component firstFrame;

	public GraphicsHWindow() {}

	public void create(GraphicsHWindow parent,LVValue title,GraphicsVLocation location,GraphicsVSize size,
		int style,boolean resizable) throws LRException
	{
		GraphicsHWindowData myData=new GraphicsHWindowData();
		Component window=null;
		Container contentPane=null;
		Dimension screenSize=new Frame().getToolkit().getScreenSize();
		switch (style)
		{
			case STYLE_DEFAULT:
				if (parent==null)
				{
					window=myData.frame=new JFrame(title.getStringValue());
					contentPane=((JFrame)window).getContentPane();
					((JFrame)window).setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					((JFrame)window).setResizable(resizable);
				}
				else
				{
					myData.frame=parent.getFrame();
					window=new JDialog(myData.frame,title.getStringValue());
					contentPane=((JDialog)window).getContentPane();
					((JDialog)window).setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
					((JDialog)window).setResizable(resizable);
				}
				if (firstFrame==null) firstFrame=window;
				break;
			case STYLE_PLAIN:
				myData.frame=(parent!=null)?(Frame)parent.getFrame():new Frame();
				window=new JWindow(myData.frame);
				contentPane=((JWindow)window).getContentPane();
				if (firstFrame==null) firstFrame=myData.frame;
				break;
			case STYLE_MASK:
				myData.frame=new Frame();
				window=contentPane=new Window(myData.frame);
//				window.setEnabled(false);
				window.addFocusListener(new FocusAdapter()
				{
					public void focusGained(FocusEvent e)
					{
						if (firstFrame!=null) firstFrame.requestFocus();
					}
				});
				break;
			case STYLE_CHILD:
				if (parent==null) throw new LRException(GraphicsRMessages.noParent(getName()));
				myData.frame=parent.getFrame();
				window=contentPane=new JPanel();
				((JPanel)window).setOpaque(true);
//				((JFrame)myData.frame).getContentPane().add(window,0);
				break;
		}
		myData.window=window;
		contentPane.setLayout(null);
		window.setBounds(-100,-100,100,100);
		window.validate();
		window.setVisible(true);
		int width=size.getWidth();
		int height=size.getHeight();
		int left=0;
		int top=0;
		Insets insets=new Insets(0,0,0,0);
		if (window instanceof Window) insets=((Window)window).getInsets();
		if (window instanceof JFrame || window instanceof JDialog)
		{
			width+=(insets.left+insets.right);
			height+=(insets.top+insets.bottom);
		}
		if (location instanceof GraphicsVScreenCenter)
		{
			switch (style)
			{
				case STYLE_CHILD:
					if ((window instanceof JFrame || window instanceof JDialog)
						&& screenSize.equals(size.getSize()))
					{
						left=-insets.left;
					}
					else
					{
						left=(screenSize.width-width)/2;
						top=(screenSize.height-height)/2;
					}
					break;
				case STYLE_MASK:
					left=-10;
					top=-insets.top;
					width=screenSize.width+20;
					height=screenSize.height+100;
					break;
			}
		}
		else
		{
			left=location.getLeft();
			top=location.getTop();
		}
		window.setBounds(left,top,width,height);
		if (window instanceof Window) ((Window)window).addWindowListener(this);
		
		create((style==STYLE_CHILD)?parent:null,window,contentPane,false);
		if (style==STYLE_CHILD) parent.add(this);
		getComponentData().extra=myData;
		window.validate();
		window.addKeyListener(myData);
	}
	
	private GraphicsHWindowData getMyData() throws LRException
	{
		return (GraphicsHWindowData)getComponentData().extra;
	}
	
	public void setTitle(LVValue title) throws LRException
	{
		Window window=getWindow();
		if (window instanceof JFrame) ((JFrame)window).setTitle(title.getStringValue());
		else if (window instanceof JDialog) ((JDialog)window).setTitle(title.getStringValue());
	}
	
	/***************************************************************************
		Set the background color of this window.
	*/
	public void setBackground(GraphicsVColor color) throws LRException
	{
		getContentPane().setBackground(color.getColor());
	}
	
//	public void setBackground(LVValue red,LVValue green,LVValue blue) throws LRError
//	{
//		getContentPane().setBackground(
//			new Color(red.getNumericValue(),green.getNumericValue(),blue.getNumericValue()));
//	}
	
	public void addComponent(JComponent c) throws LRException
	{
		getContentPane().add(c,0);
	}
	
	public Window getWindow()
	{
		return (Window)super.getComponent();
	}
	
	public Frame getFrame()
	{
		try
		{
			GraphicsHWindowData myData=getMyData();
			return (myData!=null)?myData.frame:null;
		}
		catch (LRException e) {}
		return null;
	}
	
	/***************************************************************************
		Add a menu to the menubar.
	*/
	public void addMenu(GraphicsHMenu menu)
	{
		Window window=getFrame();
		if (window instanceof JFrame)
		{
			JFrame frame=(JFrame)window;
			JMenuBar mb=frame.getJMenuBar();
			if (mb==null) frame.setJMenuBar(mb=new JMenuBar());
			mb.add((JMenu)menu.getComponent());
			frame.validate();
		}
		else if (window instanceof JDialog)
		{
			JDialog frame=(JDialog)window;
			JMenuBar mb=frame.getJMenuBar();
			if (mb==null) frame.setJMenuBar(mb=new JMenuBar());
			mb.add((JMenu)menu.getComponent());
			frame.validate();
		}
	}

	public void setLocation(GraphicsVLocation location) throws LRException
	{
		getComponent().setLocation(location.getLeft(),location.getTop());
	}
	
	public void moveTo(LVValue left,LVValue top) throws LRException
	{
		getContentPane().setLocation(left.getIntegerValue(),top.getIntegerValue());
	}

	public void moveLeft(LVValue distance) throws LRException
	{
		Window window=(Window)getContentPane();
		int left=window.getLocation().x;
		int top=window.getLocation().y;
		window.setLocation(left-distance.getIntegerValue(),top);
	}

	public void moveRight(LVValue distance) throws LRException
	{
		Window window=(Window)getContentPane();
		int left=window.getLocation().x;
		int top=window.getLocation().y;
		window.setLocation(left+distance.getIntegerValue(),top);
	}

	public void moveUp(LVValue distance) throws LRException
	{
		Window window=(Window)getContentPane();
		int left=window.getLocation().x;
		int top=window.getLocation().y;
		window.setLocation(left,top-distance.getIntegerValue());
	}

	public void moveDown(LVValue distance) throws LRException
	{
		Window window=(Window)getContentPane();
		int left=window.getLocation().x;
		int top=window.getLocation().y;
		window.setLocation(left,top+distance.getIntegerValue());
	}

	public int getLeft() throws LRException
	{
		return getContentPane().getLocation().x;
	}

	public int getRight() throws LRException
	{
		Container c=getContentPane();
		return c.getLocation().x+c.getSize().width-1;
	}

	public int getTop() throws LRException
	{
		return getContentPane().getLocation().y;
	}

	public int getBottom() throws LRException
	{
		Container c=getContentPane();
		return c.getLocation().y+c.getSize().height-1;
	}

	public int getContentWidth() throws LRException
	{
		return getContentPane().getSize().width;
	}

	public int getContentHeight() throws LRException
	{
		return getContentPane().getSize().height;
	}

	/***************************************************************************
		Dispose of this window.
	*/
	public void dispose() throws LRException
	{
		GraphicsHWindowData myData=getMyData();
		if (myData!=null)
		{
			Component window=myData.window;
			if (window!=null && window instanceof Window) ((Window)window).dispose();
		}
		super.dispose();
	}
	
	public static final int
		STYLE_DEFAULT=0,
		STYLE_PLAIN=1,
		STYLE_MASK=2,
		STYLE_CHILD=3;
	
	// Private data for this class

	class GraphicsHWindowData extends LHData implements KeyListener
	{
		Component window;
		Frame frame;
		
		GraphicsHWindowData()
		{
		}
		
		public void keyPressed(KeyEvent e)
		{
		}
		
		public void keyReleased(KeyEvent e)
		{
		}
		
		public void keyTyped(KeyEvent e)
		{
		}
	}
}
