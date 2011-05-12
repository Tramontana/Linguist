// GraphicsKSet.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.keyword;

import javax.swing.SwingConstants;

import net.eclecity.linguist.graphics.GraphicsLMessages;
import net.eclecity.linguist.graphics.handler.GraphicsH2DComponent;
import net.eclecity.linguist.graphics.handler.GraphicsHBorder;
import net.eclecity.linguist.graphics.handler.GraphicsHButton;
import net.eclecity.linguist.graphics.handler.GraphicsHCanvas;
import net.eclecity.linguist.graphics.handler.GraphicsHComponent;
import net.eclecity.linguist.graphics.handler.GraphicsHCursor;
import net.eclecity.linguist.graphics.handler.GraphicsHFont;
import net.eclecity.linguist.graphics.handler.GraphicsHImage;
import net.eclecity.linguist.graphics.handler.GraphicsHLabel;
import net.eclecity.linguist.graphics.handler.GraphicsHPanel;
import net.eclecity.linguist.graphics.handler.GraphicsHSet;
import net.eclecity.linguist.graphics.handler.GraphicsHSetAlpha;
import net.eclecity.linguist.graphics.handler.GraphicsHSetBackground;
import net.eclecity.linguist.graphics.handler.GraphicsHSetBorder;
import net.eclecity.linguist.graphics.handler.GraphicsHSetColor;
import net.eclecity.linguist.graphics.handler.GraphicsHSetCursor;
import net.eclecity.linguist.graphics.handler.GraphicsHSetDescription;
import net.eclecity.linguist.graphics.handler.GraphicsHSetFont;
import net.eclecity.linguist.graphics.handler.GraphicsHSetIcon;
import net.eclecity.linguist.graphics.handler.GraphicsHSetLayout;
import net.eclecity.linguist.graphics.handler.GraphicsHSetLocation;
import net.eclecity.linguist.graphics.handler.GraphicsHSetOpaque;
import net.eclecity.linguist.graphics.handler.GraphicsHSetSize;
import net.eclecity.linguist.graphics.handler.GraphicsHSetStatus;
import net.eclecity.linguist.graphics.handler.GraphicsHSetText;
import net.eclecity.linguist.graphics.handler.GraphicsHSetTextAlignment;
import net.eclecity.linguist.graphics.handler.GraphicsHSetToolTip;
import net.eclecity.linguist.graphics.handler.GraphicsHStyle;
import net.eclecity.linguist.graphics.handler.GraphicsHStyledtext;
import net.eclecity.linguist.graphics.handler.GraphicsHSwingComponent;
import net.eclecity.linguist.graphics.handler.GraphicsHTextComponent;
import net.eclecity.linguist.graphics.handler.GraphicsHTextControl;
import net.eclecity.linguist.graphics.handler.GraphicsHTextfield;
import net.eclecity.linguist.graphics.handler.GraphicsHWindow;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.value.LVConstant;
import net.eclecity.linguist.value.LVValue;


/*******************************************************************************
 * <pre>
 * 
 *  set the title of {window} to {title}
 *  set the layout of {window} to flow...
 *  set the size/height/width of {component} to {size}
 *  set the location of {component} to {location}
 *  set the text of {component} to {text}
 *  set the name of {style} to {text}
 *  set the icon of {label}/{canvas} to {name} [scaled]
 *  set the default/selected/pressed/disabled [selected]/rollover [selected] icon of {button] to {name}
 *  set the border of {component} to {border}/none
 *  set the cursor of {component}/{style} to {cursor}
 *  set the style of {component} to opaque/transparent
 *  set the background/foreground color/colour of {component} to {color}
 *  set the color/colour of {font} to {color}
 *  set the font of {component} to {font}
 *  set the tooltip of {component} to {text}
 *  set the status of {component} to on/off/{value}
 *  set the description of {component} to {text}
 *  set the horizontal/vertical text alignment of {label}/{button} to left/right/top/bottom/center/centre
 *  set the caret color/colour of {text component) to {color}
 *  set the mode of {textfield} to integer
 *  set the columns of {textfield} to {n}
 *  set the background of {panel} to {image}/{name}
 *  set the alpha of {2d component} to {value}
 *  
 * <p>
 * 
 *  [1.003 GT]  15/02/01  Add 'set the status of {menuitem}'.
 *  [1.002 GT]  05/01/01  Add 'set the icon of {canvas}'.
 *  [1.001 GT]  14/10/00  Pre-existing.
 *  
 * </pre>
 */
public class GraphicsKSet extends GraphicsKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		boolean flag;

		skip("the");
		if (tokenIs("title"))
		{
			// set the title of {window} to {title}
			skip("of");
			if (isSymbol())
			{
				LHHandler handler = getHandler();
				if (handler instanceof GraphicsHWindow)
				{
					skip("to");
					return new GraphicsHSet(line, (GraphicsHWindow) handler,
							getValue());
				}
			}
			warning(this, GraphicsLMessages.windowExpected(getToken()));
		}
		else if (tokenIs("layout"))
		{
			// set the layout of {component} to none/flow/border...
			skip("of");
			if (isSymbol())
			{
				LHHandler handler = getHandler();
				if (handler instanceof GraphicsHComponent)
				{
					skip("to");
					if (tokenIs("none")) return new GraphicsHSetLayout(line,
							(GraphicsHComponent) handler, GraphicsHComponent.NO_LAYOUT);
					if (tokenIs("flow")) return new GraphicsHSetLayout(line,
							(GraphicsHComponent) handler,
							GraphicsHComponent.FLOW_LAYOUT);
					if (tokenIs("border")) return new GraphicsHSetLayout(line,
							(GraphicsHComponent) handler,
							GraphicsHComponent.BORDER_LAYOUT);
					dontUnderstandToken();
				}
				throw new LLException(GraphicsLMessages
						.componentExpected(getToken()));
			}
			dontUnderstandToken();
		}
		else if (tokenIs("background"))
		{
			getNextToken();
			if (tokenIs("color") || tokenIs("colour")) return doColor(false);
			// set the background of {panel} to {name}
			unGetToken();
			skip("of");
			if (isSymbol())
			{
				if (getHandler() instanceof GraphicsHPanel)
				{
					GraphicsHPanel panel = (GraphicsHPanel) getHandler();
					skip("to");
					if (isSymbol())
					{
						if (getHandler() instanceof GraphicsHImage) return new GraphicsHSetBackground(
								line, panel, (GraphicsHImage) getHandler());
						return new GraphicsHSetBackground(line, panel, getValue());
					}
					return new GraphicsHSetBackground(line, panel, getValue());
				}
				warning(this, GraphicsLMessages.panelExpected(getToken()));
				return null;
			}
			dontUnderstandToken();
		}
		else if (tokenIs("size"))
		{
			// set the size of {component} to {size}
			skip("of");
			if (isSymbol())
			{
				if (getHandler() instanceof GraphicsHSwingComponent)
				{
					skip("to");
					return new GraphicsHSetSize(line,
							(GraphicsHSwingComponent) getHandler(), getSize());
				}
				if (getHandler() instanceof GraphicsHWindow)
				{
					skip("to");
					return new GraphicsHSetSize(line,
							(GraphicsHWindow) getHandler(), getSize());
				}
				if (getHandler() instanceof GraphicsHPanel)
				{
					skip("to");
					return new GraphicsHSetSize(line, (GraphicsHPanel) getHandler(),
							getSize());
				}
			}
			return null;
		}
		else if (tokenIs("width"))
		{
			// set the width of {component} to {value}
			skip("of");
			if (isSymbol())
			{
				if (getHandler() instanceof GraphicsHSwingComponent)
				{
					skip("to");
					return new GraphicsHSetSize(line,
							(GraphicsHSwingComponent) getHandler(), getValue(), true);
				}
				if (getHandler() instanceof GraphicsHWindow)
				{
					skip("to");
					return new GraphicsHSetSize(line,
							(GraphicsHWindow) getHandler(), getValue(), true);
				}
			}
			return null;
		}
		else if (tokenIs("height"))
		{
			// set the height of {component} to {size}
			skip("of");
			if (isSymbol())
			{
				if (getHandler() instanceof GraphicsHSwingComponent)
				{
					skip("to");
					return new GraphicsHSetSize(line,
							(GraphicsHSwingComponent) getHandler(), getValue(), false);
				}
				if (getHandler() instanceof GraphicsHWindow)
				{
					skip("to");
					return new GraphicsHSetSize(line,
							(GraphicsHWindow) getHandler(), getValue(), false);
				}
			}
			return null;
		}
		else if (tokenIs("location"))
		{
			// set the location of {component} to {location}
			skip("of");
			if (isSymbol())
			{
				if (getHandler() instanceof GraphicsHSwingComponent)
				{
					skip("to");
					return new GraphicsHSetLocation(line,
							(GraphicsHSwingComponent) getHandler(), getLocation());
				}
				if (getHandler() instanceof GraphicsHWindow)
				{
					skip("to");
					return new GraphicsHSetLocation(line,
							(GraphicsHWindow) getHandler(), getLocation());
				}
			}
			return null;
		}
		else if (tokenIs("text"))
		{
			// set the text of {component} to {text}
			skip("of");
			if (isSymbol())
			{
				if (getHandler() instanceof GraphicsHTextComponent)
				{
					skip("to");
					return new GraphicsHSetText(line,
							(GraphicsHTextComponent) getHandler(), getValue());
				}
				if (getHandler() instanceof GraphicsHStyledtext)
				{
					skip("to");
					return new GraphicsHSetText(line,
							(GraphicsHStyledtext) getHandler(), getValue());
				}
			}
			return null;
		}
		else if (tokenIs("name"))
		{
			// set the name of {style} to {text}
			skip("of");
			if (isSymbol())
			{
				if (getHandler() instanceof GraphicsHStyle)
				{
					skip("to");
					return new GraphicsHSetText(line,
							(GraphicsHTextComponent) getHandler(), getValue());
				}
			}
			return null;
		}
		else if (tokenIs("icon"))
		{
			// set the icon of {label}/{canvas} to {name} [scaled]
			skip("of");
			if (isSymbol())
			{
				if (getHandler() instanceof GraphicsHLabel)
				{
					boolean scaled = false;
					GraphicsHLabel label = (GraphicsHLabel) getHandler();
					skip("to");
					if (isSymbol())
					{
						if (getHandler() instanceof GraphicsHImage)
						{
							getNextToken();
							if (tokenIs("scaled")) scaled = true;
							else unGetToken();
							return new GraphicsHSetIcon(line, label,
									(GraphicsHImage) getHandler(), scaled);
						}
					}
					LVValue value = getValue();
					getNextToken();
					if (tokenIs("scaled")) scaled = true;
					else unGetToken();
					return new GraphicsHSetIcon(line, label, value, scaled);
				}
				if (getHandler() instanceof GraphicsHCanvas)
				{
					GraphicsHCanvas canvas = (GraphicsHCanvas) getHandler();
					skip("to");
					if (isSymbol())
					{
						if (getHandler() instanceof GraphicsHImage) return new GraphicsHSetIcon(
								line, canvas, (GraphicsHImage) getHandler());
						return new GraphicsHSetIcon(line, canvas, getValue());
					}
					return new GraphicsHSetIcon(line, canvas, getValue());
				}
			}
			return null;
		}
		else if (tokenIs("default"))
		{
			getNextToken();
			if (tokenIs("icon"))
			// set the default icon of {button] to {name}
			return doButtonIcon(GraphicsHButton.DEFAULT_ICON);
		}
		else if (tokenIs("selected"))
		{
			// set the selected icon of {button] to {name}
			getNextToken();
			if (tokenIs("icon")) return doButtonIcon(GraphicsHButton.SELECTED_ICON);
		}
		else if (tokenIs("pressed"))
		{
			// set the pressed icon of {button] to {name}
			getNextToken();
			if (tokenIs("icon")) return doButtonIcon(GraphicsHButton.PRESSED_ICON);
		}
		else if (tokenIs("disabled"))
		{
			// set the disabled [selected] icon of {button] to {name}
			getNextToken();
			if (tokenIs("disabled"))
			{
				getNextToken();
				flag = true;
			}
			else flag = false;
			if (tokenIs("icon")) return doButtonIcon(flag ? GraphicsHButton.DISABLED_SELECTED_ICON
					: GraphicsHButton.DISABLED_ICON);
		}
		else if (tokenIs("rollover"))
		{
			// set the rollover [selected] icon of {button] to {name}
			getNextToken();
			if (tokenIs("rollover"))
			{
				getNextToken();
				flag = true;
			}
			else flag = false;
			if (tokenIs("icon")) return doButtonIcon(flag ? GraphicsHButton.ROLLOVER_SELECTED_ICON
					: GraphicsHButton.ROLLOVER_ICON);
		}
		else if (tokenIs("border"))
		{
			// set the border of {button} to {border}/none
			skip("of");
			if (isSymbol())
			{
				if (getHandler() instanceof GraphicsHSwingComponent)
				{
					GraphicsHSwingComponent component = (GraphicsHSwingComponent) getHandler();
					skip("to");
					if (tokenIs("none")) return new GraphicsHSetBorder(line,
							component, null);
					if (isSymbol())
					{
						if (getHandler() instanceof GraphicsHBorder) return new GraphicsHSetBorder(
								line, component, (GraphicsHBorder) getHandler());
					}
					throw new LLException(GraphicsLMessages
							.borderExpected(getToken()));
				}
				warning(this, GraphicsLMessages.componentExpected(getToken()));
			}
		}
		else if (tokenIs("cursor"))
		{
			// set the cursor of {component}/{style} to {cursor}
			skip("of");
			if (isSymbol())
			{
				if (getHandler() instanceof GraphicsHComponent)
				{
					GraphicsHComponent component = (GraphicsHComponent) getHandler();
					skip("to");
					if (isSymbol())
					{
						if (getHandler() instanceof GraphicsHCursor) { return new GraphicsHSetCursor(
								line, component, (GraphicsHCursor) getHandler()); }
					}
					throw new LLException(GraphicsLMessages
							.cursorExpected(getToken()));
				}
				if (getHandler() instanceof GraphicsHStyle)
				{
					GraphicsHStyle style = (GraphicsHStyle) getHandler();
					skip("to");
					if (isSymbol())
					{
						if (getHandler() instanceof GraphicsHCursor) { return new GraphicsHSetCursor(
								line, style, (GraphicsHCursor) getHandler()); }
					}
					throw new LLException(GraphicsLMessages
							.cursorExpected(getToken()));
				}
			}
			warning(this, GraphicsLMessages.componentExpected(getToken()));
		}
		else if (tokenIs("style"))
		{
			// set the style of {component} to opaque/transparent
			skip("of");
			if (isSymbol())
			{
				if (getHandler() instanceof GraphicsHComponent)
				{
					skip("to");
					if (tokenIs("opaque")) return new GraphicsHSetOpaque(line,
							(GraphicsHComponent) getHandler(), true);
					if (tokenIs("transparent")) return new GraphicsHSetOpaque(line,
							(GraphicsHComponent) getHandler(), false);
					dontUnderstandToken();
				}
			}
			return null;
		}
		else if (tokenIs("foreground"))
		{
			// set the foreground color/colour of {component} to {color}
			getNextToken();
			if (tokenIs("color") || tokenIs("colour")) return doColor(true);
		}
		else if (tokenIs("font"))
		{
			// set the font of {component} to {font}
			skip("of");
			if (isSymbol())
			{
				if (getHandler() instanceof GraphicsHTextComponent)
				{
					skip("to");
					return new GraphicsHSetFont(line,
							(GraphicsHTextComponent) getHandler(), getFont());
				}
				if (getHandler() instanceof GraphicsHStyledtext)
				{
					skip("to");
					return new GraphicsHSetFont(line,
							(GraphicsHStyledtext) getHandler(), getFont());
				}
			}
			throw new LLException(GraphicsLMessages.componentExpected(getToken()));
		}
		else if (tokenIs("color") || tokenIs("colour"))
		{
			// set the color of {font} to {color}
			skip("of");
			if (isSymbol())
			{
				if (getHandler() instanceof GraphicsHFont)
				{
					skip("to");
					return new GraphicsHSet(line, (GraphicsHFont) getHandler(),
							getColor());
				}
			}
			throw new LLException(GraphicsLMessages.fontExpected(getToken()));
		}
		else if (tokenIs("caret"))
		{
			// set the caret color/colour of {text component) to {color}
			getNextToken();
			if (tokenIs("color") || tokenIs("colour"))
			{
				skip("of");
				if (isSymbol())
				{
					if (getHandler() instanceof GraphicsHTextControl)
					{
						skip("to");
						return new GraphicsHSet(line,
								(GraphicsHTextControl) getHandler(), getColor());
					}
				}
				throw new LLException(GraphicsLMessages
						.textComponentExpected(getToken()));
			}
		}
		else if (tokenIs("tooltip"))
		{
			// set the tooltip of {component} to {text}
			skip("of");
			if (isSymbol())
			{
				if (getHandler() instanceof GraphicsHSwingComponent)
				{
					skip("to");
					return new GraphicsHSetToolTip(line,
							(GraphicsHSwingComponent) getHandler(), getValue());
				}
			}
			warning(this, GraphicsLMessages.componentExpected(getToken()));
		}
		else if (tokenIs("status"))
		{
			// set the status of {component} to on/off/{value}
			skip("of");
			if (isSymbol())
			{
				if (getHandler() instanceof GraphicsHSwingComponent)
				{
					skip("to");
					LVValue value = null;
					if (tokenIs("off")) value = new LVConstant(0);
					else if (tokenIs("on")) value = new LVConstant(1);
					else value = getValue();
					return new GraphicsHSetStatus(line,
							(GraphicsHSwingComponent) getHandler(), value);
				}
			}
			warning(this, GraphicsLMessages.componentExpected(getToken()));
		}
		else if (tokenIs("description"))
		{
			// set the description of {component} to {text}
			skip("of");
			if (isSymbol())
			{
				if (getHandler() instanceof GraphicsHSwingComponent)
				{
					skip("to");
					return new GraphicsHSetDescription(line,
							(GraphicsHSwingComponent) getHandler(), getValue());
				}
			}
			throw new LLException(GraphicsLMessages.componentExpected(getToken()));
		}
		else if (tokenIs("horizontal") || tokenIs("vertical"))
		{
			// set the horizontal/vertical text alignment of {label}/{button} to
			// left/right/top/bottom/center/centre
			String s = getToken();
			getNextToken();
			if (tokenIs("text")) return doTextPosition(s);
		}
		else if (tokenIs("mode"))
		{
			skip("of");
			if (isSymbol())
			{
				if (getHandler() instanceof GraphicsHTextfield)
				{
					// set the mode of {textfield} to integer
					skip("to");
					int mode = GraphicsHTextfield.TEXT;
					if (tokenIs("integer")) mode = GraphicsHTextfield.INTEGER;
					else dontUnderstandToken();
					return new GraphicsHSet(line, (GraphicsHTextfield) getHandler(),
							mode);
				}
			}
		}
		else if (tokenIs("columns"))
		{
			skip("of");
			if (isSymbol())
			{
				if (getHandler() instanceof GraphicsHTextfield)
				{
					// set the columns of {textfield} to {n}
					skip("to");
					return new GraphicsHSet(line, (GraphicsHTextfield) getHandler(),
							getValue());
				}
			}
		}
		else if (tokenIs("alpha"))
		{
			// set the alpha of {2d component} to {value}
			skip("of");
			if (isSymbol())
			{
				if (getHandler() instanceof GraphicsH2DComponent)
				{
					skip("to");
					return new GraphicsHSetAlpha(line,
							(GraphicsH2DComponent) getHandler(), getValue());
				}
			}
			inappropriateType();
		}
		return null;
	}

	/****************************************************************************
	 * Set an icon for a button.
	 */
	private LHHandler doButtonIcon(int view) throws LLException
	{
		skip("of");
		if (isSymbol())
		{
			if (getHandler() instanceof GraphicsHButton)
			{
				GraphicsHButton button = (GraphicsHButton) getHandler();
				skip("to");
				if (isSymbol())
				{
					if (getHandler() instanceof GraphicsHImage) return new GraphicsHSetIcon(
							line, button, view, (GraphicsHImage) getHandler());
					throw new LLException(GraphicsLMessages
							.imageExpected(getToken()));
				}
				return new GraphicsHSetIcon(line, button, view, getValue());
			}
			throw new LLException(GraphicsLMessages.buttonExpected(getToken()));
		}
		return null;
	}

	/****************************************************************************
	 * Do a background or foreground color.
	 */
	private LHHandler doColor(boolean foreground) throws LLException
	{
		skip("of");
		if (isSymbol())
		{
			if (getHandler() instanceof GraphicsHComponent)
			{
				skip("to");
				return new GraphicsHSetColor(line,
						(GraphicsHComponent) getHandler(), foreground, getColor());
			}
			throw new LLException(GraphicsLMessages.componentExpected(getToken()));
		}
		return null;
	}

	/****************************************************************************
	 * Set the alignment of text.
	 */
	private LHHandler doTextPosition(String s) throws LLException
	{
		getNextToken();
		if (tokenIs("position"))
		{}
		else if (tokenIs("alignment"))
		{
			skip("of");
			if (isSymbol())
			{
				if (getHandler() instanceof GraphicsHTextComponent)
				{
					skip("to");
					boolean vertical = s.equals("vertical");
					int alignment = 0;
					if (tokenIs("left")) alignment = SwingConstants.LEFT;
					else if (tokenIs("right")) alignment = SwingConstants.RIGHT;
					else if (tokenIs("top")) alignment = SwingConstants.TOP;
					else if (tokenIs("bottom")) alignment = SwingConstants.BOTTOM;
					else if (tokenIs("center") || tokenIs("centre")) alignment = SwingConstants.CENTER;
					return new GraphicsHSetTextAlignment(line,
							(GraphicsHTextComponent) getHandler(), vertical, alignment);
				}
			}
			throw new LLException(GraphicsLMessages
					.labelOrButtonExpected(getToken()));
		}
		return null;
	}
}

