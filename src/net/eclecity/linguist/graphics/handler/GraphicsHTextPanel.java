// GraphicsHTextPanel.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.util.Vector;

import net.eclecity.linguist.graphics.value.GraphicsVFont;
import net.eclecity.linguist.graphics.value.GraphicsVLocation;
import net.eclecity.linguist.graphics.value.GraphicsVSize;
import net.eclecity.linguist.handler.LHEvent;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.textpane.LTextPane;
import net.eclecity.linguist.textpane.LTextPaneEvent;
import net.eclecity.linguist.textpane.LTextPaneListener;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Text panel handler.  This implements a private mark-up format.
*/
public class GraphicsHTextPanel extends GraphicsHSwingComponent
	implements GraphicsHTextComponent, LTextPaneListener
{
	public GraphicsHTextPanel() {}

	public void create(GraphicsHComponent container,LVValue text,GraphicsVLocation location,
		GraphicsVSize size,boolean scrolling) throws LRException
	{
		LTextPane textPane=new LTextPane(scrolling);
		textPane.setVisible(false);
		textPane.setLocation(location.getLeft(),location.getTop());
		if (size!=null) getComponentData().sizeSpecified=true;
		else size=new GraphicsVSize(200,100);
		textPane.setSize(size.getWidth(),size.getHeight());
		textPane.addListener(this);
		textPane.setWidth(300);
		textPane.setDefaultFont(new Font("SanSerif",Font.PLAIN,12));
		textPane.setDefaultColor(Color.black);
		textPane.setText(text.getStringValue());
		textPane.setExtraData(new GraphicsHTextPanelData());
		create(container,textPane,false);
		container.getContentPane().add(textPane,0);
	}
	
	/***************************************************************************
		Set and get the text of the panel.
	*/
	public void setText(LVValue text) throws LRException
	{
		((LTextPane)getComponent()).setText(text.getStringValue());
	}
	public String getText()
	{
		return ((LTextPane)getComponent()).getText();
	}

	/***************************************************************************
		Set and get the font of the panel.
	*/
	public void setFont(GraphicsVFont font) throws LRException
	{
		LTextPane pane=(LTextPane)getComponent();
		pane.setDefaultFont(font.getFont());
		pane.setDefaultColor(font.getColor());
		pane.setText();
	}
	public Font getFont()
	{
		LTextPane pane=(LTextPane)getComponent();
		return pane.getCurrentFont();
	}
	public Color getColor()
	{
		LTextPane pane=(LTextPane)getComponent();
		return pane.getCurrentColor();
	}
	
	/***************************************************************************
		Add a style to this panel.
	*/
	public void addStyle(GraphicsHStyle style)
	{
		((GraphicsHTextPanelData)((LTextPane)getComponent()).getExtraData()).addStyle(style);
	}
	
	/***************************************************************************
		Handle an event message from the text component.
	*/
   public void handleEvent(LTextPaneEvent evt)
   {
   	LTextPane pane=evt.getTextPane();
   	String tag=evt.getTag();
   	GraphicsHTextPanelData myData=(GraphicsHTextPanelData)pane.getExtraData();
   	int index=myData.getIndex();
   	GraphicsHStyle style;
   	Component component;
   	int n;
   	switch (evt.getID())
   	{
   	case LTextPaneEvent.DEFINE_TAG:
   		if (tag.length()==0) pane.applyDefaults();
   		else
   		{
   			// Test for a link.  Link tags have no name,
   			// so reset the tag as we don't need its contents here.
   			if (tag.charAt(0)==':') tag="";
   			// See if this style is defined.
   			for (n=0; n<myData.countStyles(); n++)
   			{
   				style=myData.getStyle(n);
   				try
   				{
   					// When we find this style, set the font and color.
   					if (style.getText().equals(tag))
   					{
							if (style.getFont()!=null) pane.setCurrentFont(style.getFont());
							if (style.getColor()!=null) pane.setCurrentColor(style.getColor());
							// If the link has a callback, underline it.
							if (style.getCallback()>0) pane.setCurrentUnderline(true);
   						break;
						}
					}
					catch (LRException ignored) {}
   			}
   		}
   		break;
   	case LTextPaneEvent.MOUSE_ENTER:
   		// Test for a link.  Link tags have no name,
   		// so reset the tag as we don't need its contents here.
   		if (tag.charAt(0)==':') tag="";
   		// See if this style is defined.
   		for (n=0; n<myData.countStyles(); n++)
   		{
   			style=myData.getStyle(n);
   			try
   			{
   				// When we find this style, set the cursor.
   				if (style.getText().equals(tag))
   				{
   					component=((GraphicsHComponentData)getData(index)).component;
   					Cursor cursor=style.getCursor();
   					if (component!=null && cursor!=null)
							((GraphicsHComponentData)getData(index)).component.setCursor(cursor);
   					break;
					}
				}
				catch (LRException ignored) {}
   		}
   		break;
   	case LTextPaneEvent.MOUSE_EXIT:
			try
			{
   			GraphicsHComponentData cd=(GraphicsHComponentData)getData(index);
				if (cd.component!=null && cd.cursor!=null) cd.component.setCursor(cd.cursor);
			}
			catch (LRException ignored) {}
   		break;
   	case LTextPaneEvent.MOUSE_CLICK:
   		// Test for a link.  Link tags have no name,
   		// so reset the tag but save its contents first.
   		String target="";
   		if (tag.charAt(0)==':')
   		{
   			target=tag.substring(1);
   			tag="";
   		}
   		// See if this style is defined.
   		for (n=0; n<myData.countStyles(); n++)
   		{
   			style=myData.getStyle(n);
   			try
   			{
   				// When we find this style, add an event to the queue.
   				if (style.getText().equals(tag))
   				{
						if (style.getCallback()!=0)
						{
							setExtra(target,index);		// save the target in my extra data
							addQueue(style.getCallback(),new LHEvent(this,evt,index));
						}
   					break;
					}
				}
				catch (LRException ignored) {}
   		}
   		break;
		}
	}

	/***************************************************************************
		Methods that may be overridden.
	*/
	public void setTitle(LVValue title) {}
	public void setHorizontalTextAlignment(int a) {}
	public void setVerticalTextAlignment(int a) {}
	
	/***************************************************************************
		Private data stored in a LTextPane object.
	*/
	class GraphicsHTextPanelData
	{
		private Vector styles;
		private int index;
		
		public GraphicsHTextPanelData()
		{
			styles=new Vector();
			index=getTheIndex();
		}
		
		public void addStyle(GraphicsHStyle style) { styles.addElement(style); }
		public GraphicsHStyle getStyle(int n) { return (GraphicsHStyle)styles.elementAt(n); }
		public int countStyles() { return styles.size(); }
		public int getIndex() { return index; }
	}
}
