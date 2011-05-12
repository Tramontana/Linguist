// LTextPane.java

package net.eclecity.linguist.textpane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

import net.eclecity.linguist.support.FixedLayout;

/*******************************************************************************
 * This component renders text according to internal style tags. Tag rules are
 * set by an external listener, whose methods are called as the text is
 * processed.
 * <p>
 * A tag is denoted by a pair of braces -<>- inside which can be any text
 * string, the content of which is only understood by the listener(s). If the
 * content is empty the style reverts to the default set for the document, which
 * can be overridden by a method in the listener interface.
 */
public class LTextPane extends JComponent
{
	private JScrollPane scrollPane = null;
	private LTextPaneListener listener = null;
	private Vector tokens;
	private Vector lines;
	private String text = "";
	private boolean parsed = false;
	private LToken insideToken; // the token we're inside
	private String currentTag; // the tag currently in effect.
	private int width = 0; // the width required for this component
	private int height; // the total height needed for this width
	private Object extraData = null; // Data owned by my owner.

	// Default attributes
	private Font defaultFont = new Font("Monospaced", Font.PLAIN, 12);
	private Color defaultColor = Color.black;
	private boolean defaultUnderline = false;

	// Current attributes
	private Font currentFont;
	private Color currentColor;
	private boolean currentUnderline = false;

	/****************************************************************************
	 * The constructor. Create a scrollpane if asked. Register myself as a mouse
	 * listener.
	 */
	public LTextPane(boolean scroll)
	{
		super();
		setLayout(new FixedLayout());
		if (scroll) scrollPane = new JScrollPane(this);
		setOpaque(false);
		addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				doMouseClicked(e);
			}
		});
		addMouseMotionListener(new MouseMotionAdapter()
		{
			public void mouseMoved(MouseEvent e)
			{
				doMouseMoved(e);
			}
		});
	}

	public JComponent getComponent()
	{
		if (scrollPane != null) return scrollPane;
		return this;
	}

	public void setWidth(int w)
	{
		width = w;
	}

	public int getTextHeight()
	{
		return height;
	}

	public void setDefaultFont(Font f)
	{
		defaultFont = f;
	}

	public Font getDefaultFont()
	{
		return defaultFont;
	}

	public void setDefaultColor(Color c)
	{
		defaultColor = c;
	}

	public Color getDefaultColor()
	{
		return defaultColor;
	}

	public void setCurrentFont(Font f)
	{
		currentFont = f;
	}

	public Font getCurrentFont()
	{
		return currentFont;
	}

	public void setCurrentColor(Color c)
	{
		currentColor = c;
	}

	public Color getCurrentColor()
	{
		return currentColor;
	}

	public void setCurrentUnderline(boolean b)
	{
		currentUnderline = b;
	}

	public boolean getCurrentUnderline()
	{
		return currentUnderline;
	}

	public String getCurrentTag()
	{
		return currentTag;
	}

	public void setExtraData(Object data)
	{
		this.extraData = data;
	}

	public Object getExtraData()
	{
		return extraData;
	}

	public void applyDefaults()
	{
		currentFont = defaultFont;
		currentColor = defaultColor;
		currentUnderline = defaultUnderline;
	}

	/****************************************************************************
	 * Manage a single tag listener. There doesn't seem much point in having more
	 * than one at a time.
	 */
	public void addListener(LTextPaneListener l)
	{
		listener = l;
	}

	public void removeListener(LTextPaneListener l)
	{
		listener = null;
	}

	/****************************************************************************
	 * Set the location of the component.
	 */
	public void setLocation(int left, int top)
	{
		if (scrollPane != null) scrollPane.setLocation(left, top);
		else super.setLocation(left, top);
	}

	/****************************************************************************
	 * Set the size of the component.
	 */
	public void setSize(int width, int height)
	{
		if (scrollPane != null) scrollPane.setSize(width, height);
		setPreferredSize(new Dimension(width, height));
		super.setSize(width, height);
	}

	/****************************************************************************
	 * Set new text or refresh the current text after styles have been changed.
	 */
	public void setText(String text)
	{
		this.text = text;
		setText();
	}

	public void setText()
	{
		applyDefaults();
		parsed = false;
		repaint();
	}

	public String getText()
	{
		return text;
	}

	/****************************************************************************
	 * The Component paint() method. If the text pane has not already been
	 * parsed, do it now. Then draw all the tokens that will be visible.
	 */
	public void paint(Graphics g)
	{
		if (!parsed)
		{
			parse(g);
			if (isOpaque())
			{
				g.setColor(getBackground());
				g.fillRect(0, 0, getSize().width, getSize().height);
			}
		}

		Rectangle viewRect;
		if (scrollPane != null) viewRect = scrollPane.getViewport().getViewRect();
		else
		{
			viewRect = getBounds();
			viewRect.setLocation(0, 0);
		}

		// Draw the tokens on every visible line.
		if (lines == null) return;
		for (int n = 0; n < lines.size(); n++)
		{
			LLine line = (LLine) lines.elementAt(n);
			int base = line.getBase();
			if (base > viewRect.y && line.getTop() < viewRect.y + viewRect.height)
			{
				for (int m = 0; m < line.size(); m++)
				{
					LToken token = line.getToken(m);
					int left = token.getLeft();
					g.setFont(token.getFont());
					g.setColor(token.getColor());
					g.drawString(token.getText(), left, base);
					if (token.isUnderlined())
					{
						g.drawLine(left, base + 1, left + token.getWidth(), base + 1);
					}
				}
			}
		}
	}

	/****************************************************************************
	 * Parse the content string by tokenizing it. As each token is created, style
	 * tags are scanned and the effect on the following token(s) is noted. At the
	 * end of the pass there is a complete list of all tokens.
	 */
	private void parse(Graphics g)
	{
		tokens = new Vector();
		if (text.length() == 0) return;
		currentTag = "";
		String s = "";
		boolean isSpace = Character.isWhitespace(text.charAt(0));
		int position = 0;
		int finish = text.length();
		Main: while (position < finish)
		{
			char c = text.charAt(position++);
			if (c == '\n')
			{
				if (s.length() > 0) new LToken(g, s, this); // flush any text
				new LToken(g, this); // create a newline token
				s = "";
				continue;
			}
			if (c == '\t') c = ' '; //  ******** ToDo
			// Keep going until we change from space to non-space or vice-versa.
			// Also break if we see the start of a tag.
			// Also deal with #.
			boolean sp = Character.isWhitespace(c);
			if (c == '#')
			{
				c = text.charAt(position++);
				if (c == '\n') c = '#'; // treat as a mistake
			}
			// check for the start of a tag
			else if (c == '<')
			{
				if (s.length() > 0) new LToken(g, s, this);
				s = "<";
				while (position < finish)
				{
					c = text.charAt(position++);
					// If we've found a tag, send it to the listener.
					if (c == '>')
					{
						currentTag = s.substring(1);
						if (listener != null) listener
								.handleEvent(new LTextPaneEvent(this,
										LTextPaneEvent.DEFINE_TAG, currentTag));
						s = "";
						continue Main;
					}
					s += c;
				}
			}
			else if (isSpace != sp)
			{
				new LToken(g, s, this);
				s = "";
				isSpace = sp;
			}
			s += c;
		}
		if (s.length() > 0) new LToken(g, s, this); // flush any text

		// At this point we have a set of tokens, each with its font, color,
		// width and height known. Next we must fit them into lines.
		int leading = 0;
		int ascent = 0;
		int descent = 0;
		int height = 0;
		int top = 0;
		int dx = 0;
		int base;
		int currentLine = 0;
		int currentLineStart = 0;
		int lastSpace = 0;
		boolean overflow = false;
		lines = new Vector();
		LLine line = new LLine();
		for (int currentToken = 0; currentToken < tokens.size(); currentToken++)
		{
			LToken token = (LToken) tokens.elementAt(currentToken);
			if (overflow)
			{
				overflow = false;
				// if the text overflowed at a space token, ignore it.
				if (token.isWhitespace()) continue;
			}
			leading = Math.max(leading, token.getLeading());
			ascent = Math.max(ascent, token.getAscent());
			descent = Math.max(descent, token.getDescent());
			height = Math.max(height, token.getHeight());
			base = (top == 0) ? ascent : top + leading + ascent;

			// Deal with a new line character.
			if (token.isNewLine())
			{
				line.setTop(base - height);
				line.setBase(base);
				lines.addElement(line);
				top += height;
				line = new LLine();
				currentLine++;
				dx = leading = ascent = descent = height = 0;
				currentLineStart = currentToken;
				continue;
			}

			// Check if we've run out of room on this line.
			// If so, start a new line at the most recent space token.
			if (dx + token.getWidth() >= getSize().width)
			{
				if (line.size() > 0)
				{
					base = (top == 0) ? ascent : top + leading + ascent;
					leading = token.getLeading();
					ascent = token.getAscent();
					descent = token.getDescent();
					height = token.getHeight();
					line.setTop(base - height);
					line.setBase(base);
					currentLine++;
					top += height;
					currentToken--;
					while (line.size() > lastSpace)
					{
						line.removeElementAt(lastSpace);
						currentToken--;
					}
					lines.addElement(line);
					line = new LLine();
					dx = leading = ascent = descent = height = 0;
					currentLineStart = currentToken;
					continue;
				}
			}

			// Not a newline so just add this token to the current line.
			if (dx > 0 || !token.isWhitespace())
			{
				line.addToken(token);
				token.setLeft(dx);
				dx += token.getWidth();
			}
			if (token.isWhitespace()) lastSpace = currentToken - currentLineStart;
		}

		// Deal with any remaining tokens.
		if (line.size() > 0)
		{
			base = (top == 0) ? ascent : top + leading + ascent;
			top = base - height;
			line.setBase(base);
			lines.addElement(line);
			top += height;
		}
		Dimension size = getSize();
		if (width > 0) size.width = width;
		size.height = this.height = top;
		setPreferredSize(size);

		parsed = true;
	}

	public void addToken(LToken token)
	{
		tokens.addElement(token);
	}

	/****************************************************************************
	 * Deal with mouse movement in this component.
	 */
	void doMouseMoved(MouseEvent e)
	{
		if (lines == null) return;
		if (lines.size() == 0) return;

		// First check if we're already inside a token.
		Rectangle bounds;
		if (insideToken != null)
		{
			bounds = insideToken.getBounds();
			// We were inside, so check we still are. If so, nothing to do.
			if (bounds.contains(e.getX(), e.getY())) return;
		}
		// Examine each token in turn.
		// If the token has a non-empty tag, check if we're
		// inside that token; if so, send an appropriate event.
		Enumeration en = tokens.elements();
		while (en.hasMoreElements())
		{
			LToken token = (LToken) en.nextElement();
			String tag = token.getTag();
			if (tag.length() > 0)
			{
				// This token is tagged.
				bounds = token.getBounds();
				if (bounds.contains(e.getX(), e.getY()))
				{
					// We moved into the token, so generate an event.
					if (listener != null)
					{
						// Check if we moved out of a token with a different tag.
						if (insideToken == null) reportInEvent(tag);
						else
						{
							String ttag = insideToken.getTag();
							if (!ttag.equals(tag))
							{
								reportOutEvent(ttag);
								reportInEvent(tag);
							}
						}
					}
					insideToken = token;
					return; // Don't look any further
				}
			}
		}
		// If we got here we must be outside all tagged tokens.
		if (insideToken != null)
		{
			reportOutEvent(insideToken.getTag());
			insideToken = null;
		}
	}

	private void reportInEvent(String tag)
	{
		listener.handleEvent(new LTextPaneEvent(this, LTextPaneEvent.MOUSE_ENTER,
				tag));
	}

	private void reportOutEvent(String tag)
	{
		listener.handleEvent(new LTextPaneEvent(this, LTextPaneEvent.MOUSE_EXIT,
				tag));
	}

	/****************************************************************************
	 * Deal with a mouse click in this component.
	 */
	void doMouseClicked(MouseEvent e)
	{
		if (insideToken != null)
		{
			if (listener != null) listener.handleEvent(new LTextPaneEvent(this,
					LTextPaneEvent.MOUSE_CLICK, insideToken.getTag()));
		}
	}
}