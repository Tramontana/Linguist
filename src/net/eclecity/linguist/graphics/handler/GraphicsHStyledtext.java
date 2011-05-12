// GraphicsHStyledtext.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

import net.eclecity.linguist.graphics.value.GraphicsVFont;
import net.eclecity.linguist.graphics.value.GraphicsVLocation;
import net.eclecity.linguist.graphics.value.GraphicsVSize;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Styled text handler.
	<pre>
	[1.001 GT]  13/12/00  New class.
	</pre>
*/
public class GraphicsHStyledtext extends GraphicsH2DComponent
{
	public GraphicsHStyledtext() {}

	public void create(GraphicsHComponent container,LVValue text,GraphicsVLocation location,
		GraphicsVSize size) throws LRException
	{
		GraphicsHCStyledText styledText=new GraphicsHCStyledText(text.getStringValue());
		if (location!=null) styledText.setLocation(location.getLocation());
		if (size!=null) getComponentData().sizeSpecified=true;
		else size=new GraphicsVSize(50,20);
		styledText.setSize(size.getWidth(),size.getHeight());
		styledText.setBorder(null);
		create(container,styledText,false);
		container.getContentPane().add(styledText,0);
	}
	
	public void setText(LVValue text) throws LRException
	{
		GraphicsHCStyledText styledText=(GraphicsHCStyledText)getComponent();
		styledText.setText(text.getStringValue());
	}
	
	public void setFont(GraphicsVFont font) throws LRException
	{
		GraphicsHCStyledText styledText=(GraphicsHCStyledText)getComponent();
		styledText.setFont(font.getFont());
		styledText.setColor(font.getColor());
	}
	
	/***************************************************************************
		The actual component.
	*/
	class GraphicsHCStyledText extends GraphicsHC2DComponent
	{
		private String s;
		private Font font=null;
		private Color color=null;
		private AttributedString as;
		
		GraphicsHCStyledText(String s)
		{
			setText(s);
		}
		
		public void setText(String s)
		{
			as=new AttributedString(this.s=s);
			if (font!=null && s.length()>0) as.addAttribute(TextAttribute.FONT,font);
			if (color!=null && s.length()>0) as.addAttribute(TextAttribute.FOREGROUND,color);
			repaint();
		}
		
		public void setFont(Font font)
		{
			this.font=font;
			if (s.length()>0) as.addAttribute(TextAttribute.FONT,font);
			repaint();
		}
		
		void setColor(Color color)
		{
			this.color=color;
			if (s.length()>0) as.addAttribute(TextAttribute.FOREGROUND,color);
			repaint();
		}
		
		public void paint(Graphics g)
		{
			Graphics2D g2=(Graphics2D)g;
			super.paint(g2);
			int centerX=getBounds().width/2;
			int centerY=getBounds().height/2;
			if (font==null) font=g2.getFont();
			if (color==null) color=g2.getColor();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

			FontRenderContext frc=g2.getFontRenderContext();
			g2.drawString(as.getIterator(),
				centerX-(int)font.getStringBounds(s,frc).getWidth()/2,
				centerY+(int)font.getLineMetrics(s,frc).getAscent()/2);
		}
	}
}
