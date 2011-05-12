// LToken.java

package net.eclecity.linguist.textpane;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

/******************************************************************************
	The LToken class.
*/
public class LToken
{
	private String text;
	private String tag;
	private Font font;
	private Color color;
	private boolean underline;
	private int left;
	private int top;
	private int width;
	private int height;
	private int leading;
	private int ascent;
	private int descent;
	private boolean isNewLine=false;

	public LToken(Graphics g,LTextPane pane)
	{
		this(g,"\n",pane);
		isNewLine=true;
	}

	public LToken(Graphics g,String text,LTextPane pane)
	{
		applyCurrent(pane);
		this.text=text;
		tag=pane.getCurrentTag();
		
		// Process the token.
		FontMetrics fm=g.getFontMetrics(font);
		width=fm.stringWidth(text);
		height=fm.getHeight();
		leading=fm.getLeading();
		ascent=fm.getMaxAscent();
		descent=fm.getDescent();
		pane.addToken(this);
	}
	
	public boolean isNewLine() { return isNewLine; }
	public Font getFont() { return font; }
	public Color getColor() { return color; }
	public String getText() { return text; }
	public boolean isUnderlined() { return underline; }
	public int getLeft() { return left; }
	public void setLeft(int left) { this.left=left; }
	public void setBase(int base) { this.top=base-ascent; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getAscent() { return ascent; }
	public int getDescent() { return descent; }
	public int getLeading() { return leading; }
	public Rectangle getBounds() { return new Rectangle(left,top,width,height); }
	public String getTag() { return tag; }

	public boolean isWhitespace()
	{
		if (text.length()==0) return true;
		return Character.isWhitespace(text.charAt(0));
	}
	
	private void applyCurrent(LTextPane pane)
	{
		font=pane.getCurrentFont();
		color=pane.getCurrentColor();
		underline=pane.getCurrentUnderline();
	}
}
