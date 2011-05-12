// ServletKSet.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.servlet.keyword;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHHashtable;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.servlet.ServletLMessages;
import net.eclecity.linguist.servlet.handler.ServletHCookie;
import net.eclecity.linguist.servlet.handler.ServletHElement;
import net.eclecity.linguist.servlet.handler.ServletHSet;
import net.eclecity.linguist.servlet.handler.ServletHSetTextMode;
import net.eclecity.linguist.servlet.handler.ServletHTemplate;
import net.eclecity.linguist.servlet.handler.ServletHUploader;
import net.eclecity.linguist.value.LVConstant;
import net.eclecity.linguist.value.LVHashtable;
import net.eclecity.linguist.value.LVStringConstant;
import net.eclecity.linguist.value.LVValue;

/*******************************************************************************
 * <pre>
 * 
 *  set the title to {value}
 *  set the head to [file] {value}
 *  set the text of {element} to {text}
 *  set the icon of {element} to {icon}
 *  set the color/colour [of {element}] to {color}
 *  set the font [of {element}] to {font}
 *  set the size [of {element}] to {size}
 *  set the style of {element} to plain/bold/italic
 *  set the alignment of {element} to left/right/center/centre
 *  set the border of {element} to {value}
 *  set the width of {element} to {value} [percent]
 *  set the height of {element} to {height}
 *  set the valign of {element} to top/middle/bottom
 *  set the target of {element} to [new] [javascript] {url}
 *  set the action of {element} to {text}
 *  set the method of {element} to get/post
 *  set the type of {element} to table/row/image/text...
 *  set the value of {element} to {text}
 *  set the name of {element} to {text}
 *  set the brackets of {template} to {left} {right}
 *  set the source of {template} to {source}
 *  set the value of {cookie} to {value}
 *  set the expiry of {cookie} to {value}
 *  set option {name} of {template} to {value}
 *  set parameter {name} of {template} to {value}
 *  set parameters of {template} to {table}
 *  set session value {name} to {value}
 *  set the response to text
 *  set the path of {uploader} to {filename}
 *  
 * <p>
 * 
 *  [1.001 GT]  25/11/00  Pre-existing class.
 *  [1.002 GT]  19/05/03  Add uploader.
 *  
 * </pre>
 */
public class ServletKSet extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		ServletHElement element = null;
		skip("the");
		if (tokenIs("response"))
		{
			// set the response to text
			skip("to");
			if (tokenIs("text")) return new ServletHSetTextMode(line);
			dontUnderstandToken();
		}
		else if (tokenIs("head"))
		{
			// set the head to [file] {value}
			skip("to");
			if (tokenIs("file")) return new ServletHSet(line, getNextValue(), true);
			return new ServletHSet(line, getValue(), false);
		}
		else if (tokenIs("title"))
		{
			// set the title to {value}
			skip("to");
			return new ServletHSet(line, getValue());
		}
		else if (tokenIs("type"))
		{
			// set the type of {element} to table/row/image/text...
			getNextToken();
			if (tokenIs("of"))
			{
				getNextToken();
				if (isSymbol())
				{
					if (getHandler() instanceof ServletHElement)
					{
						element = (ServletHElement) getHandler();
						skip("to");
						int type = ServletHElement.TEXT;
						if (tokenIs("table")) type = ServletHElement.TABLE;
						else if (tokenIs("row")) type = ServletHElement.ROW;
						else if (tokenIs("cell")) type = ServletHElement.CELL;
						else if (tokenIs("text")) type = ServletHElement.TEXT;
						else if (tokenIs("image")) type = ServletHElement.IMAGE;
						else if (tokenIs("paragraph")) type = ServletHElement.PARAGRAPH;
						else if (tokenIs("form")) type = ServletHElement.FORM;
						else if (tokenIs("textfield")) type = ServletHElement.TEXTFIELD;
						else if (tokenIs("textarea")) type = ServletHElement.TEXTAREA;
						else if (tokenIs("submit")) type = ServletHElement.SUBMIT;
						else if (tokenIs("reset")) type = ServletHElement.RESET;
						else dontUnderstandToken();
						return new ServletHSet(line, element, new LVConstant(type),
								ServletHSet.TYPE);
					}
				}
			}
		}
		else if (tokenIs("text"))
		{
			getNextToken();
			if (tokenIs("of"))
			{
				// set the text of {element} to {text}
				getNextToken();
				if (isSymbol())
				{
					if (getHandler() instanceof ServletHElement)
					{
						skip("to");
						return new ServletHSet(line, (ServletHElement) getHandler(),
								getValue(), ServletHSet.TEXT);
					}
				}
			}
		}
		else if (tokenIs("color") || tokenIs("colour"))
		{
			// set the color/colour [of {element}] to {color}
			getNextToken();
			if (tokenIs("of"))
			{
				getNextToken();
				if (isSymbol())
				{
					if (getHandler() instanceof ServletHElement)
					{
						element = (ServletHElement) getHandler();
						skip("to");
						if (tokenIs("color") || tokenIs("colour"))
						{
							LVValue red = getNextValue();
							LVValue green = getNextValue();
							LVValue blue = getNextValue();
							return new ServletHSet(line, element, red, green, blue);
						}
					}
				}
			}
		}
		else if (tokenIs("font"))
		{
			// set the font [of {element}] to {font}
			getNextToken();
			if (tokenIs("of"))
			{
				getNextToken();
				if (isSymbol())
				{
					if (getHandler() instanceof ServletHElement)
					{
						element = (ServletHElement) getHandler();
						skip("to");
						return new ServletHSet(line, element, getValue(),
								ServletHSet.FONT);
					}
				}
			}
		}
		else if (tokenIs("size"))
		{
			// set the size [of {element}] to {font}
			getNextToken();
			if (tokenIs("of"))
			{
				getNextToken();
				if (isSymbol())
				{
					if (getHandler() instanceof ServletHElement)
					{
						element = (ServletHElement) getHandler();
						skip("to");
						return new ServletHSet(line, element, getValue(),
								ServletHSet.SIZE);
					}
				}
			}
		}
		else if (tokenIs("style"))
		{
			// set the style of {element} to plain/bold/italic
			getNextToken();
			if (tokenIs("of"))
			{
				getNextToken();
				if (isSymbol())
				{
					if (getHandler() instanceof ServletHElement)
					{
						element = (ServletHElement) getHandler();
						skip("to");
						boolean isBold = false;
						boolean isItalic = false;
						while (true)
						{
							if (tokenIs("plain"))
							{
								isBold = false;
								isItalic = false;
							}
							else if (tokenIs("bold")) isBold = true;
							else if (tokenIs("italic")) isItalic = true;
							else
							{
								unGetToken();
								break;
							}
							getNextToken();
						}
						return new ServletHSet(line, element, isBold, isItalic);
					}
				}
			}
		}
		else if (tokenIs("icon"))
		{
			// set the icon of {element} to {name}
			getNextToken();
			if (tokenIs("of"))
			{
				getNextToken();
				if (isSymbol())
				{
					if (getHandler() instanceof ServletHElement)
					{
						element = (ServletHElement) getHandler();
						skip("to");
						return new ServletHSet(line, element, getValue(),
								ServletHSet.ICON);
					}
				}
			}
		}
		else if (tokenIs("alignment"))
		{
			// set the alignment of {element} to none/left/right/center/centre
			getNextToken();
			if (tokenIs("of"))
			{
				getNextToken();
				if (isSymbol())
				{
					if (getHandler() instanceof ServletHElement)
					{
						element = (ServletHElement) getHandler();
						skip("to");
						int alignment = ServletHElement.ALIGN_NONE;
						while (true)
						{
							if (tokenIs("none")) alignment = ServletHElement.ALIGN_NONE;
							else if (tokenIs("left")) alignment = ServletHElement.ALIGN_LEFT;
							else if (tokenIs("right")) alignment = ServletHElement.ALIGN_RIGHT;
							else if (tokenIs("center") || tokenIs("centre")) alignment = ServletHElement.ALIGN_CENTER;
							else
							{
								unGetToken();
								break;
							}
							getNextToken();
						}
						return new ServletHSet(line, element, new LVConstant(
								alignment), ServletHSet.ALIGNMENT);
					}
				}
			}
		}
		else if (tokenIs("valign"))
		{
			// set the valign of {element} to top/middle/bottom
			getNextToken();
			if (tokenIs("of"))
			{
				getNextToken();
				if (isSymbol())
				{
					if (getHandler() instanceof ServletHElement)
					{
						element = (ServletHElement) getHandler();
						skip("to");
						int valign = ServletHElement.ALIGN_NONE;
						while (true)
						{
							if (tokenIs("none")) valign = ServletHElement.ALIGN_NONE;
							else if (tokenIs("top")) valign = ServletHElement.ALIGN_TOP;
							else if (tokenIs("middle")) valign = ServletHElement.ALIGN_MIDDLE;
							else if (tokenIs("bottom")) valign = ServletHElement.ALIGN_BOTTOM;
							else
							{
								unGetToken();
								break;
							}
							getNextToken();
						}
						return new ServletHSet(line, element, new LVConstant(valign),
								ServletHSet.VALIGN);
					}
				}
			}
		}
		else if (tokenIs("target"))
		{
			// set the target of {element} to [new] {url}/null
			getNextToken();
			if (tokenIs("of"))
			{
				getNextToken();
				if (isSymbol())
				{
					if (getHandler() instanceof ServletHElement)
					{
						element = (ServletHElement) getHandler();
						skip("to");
						boolean newWindow = false;
						if (tokenIs("new"))
						{
							newWindow = true;
							getNextToken();
						}
						boolean javascript = false;
						if (tokenIs("javascript"))
						{
							javascript = true;
							getNextToken();
						}
						LVValue url = null;
						if (tokenIs("null")) url = new LVStringConstant("");
						else url = getValue();
						return new ServletHSet(line, element, url,
								ServletHSet.TARGET, newWindow, javascript);
					}
				}
			}
		}
		else if (tokenIs("action"))
		{
			// set the action of {element} to {text}
			getNextToken();
			if (tokenIs("of"))
			{
				getNextToken();
				if (isSymbol())
				{
					if (getHandler() instanceof ServletHElement)
					{
						skip("to");
						return new ServletHSet(line, (ServletHElement) getHandler(),
								getValue(), ServletHSet.ACTION);
					}
				}
			}
		}
		else if (tokenIs("method"))
		{
			// set the method of {element} to get/post
			getNextToken();
			if (tokenIs("of"))
			{
				getNextToken();
				if (isSymbol())
				{
					if (getHandler() instanceof ServletHElement)
					{
						skip("to");
						LVValue method = null;
						if (tokenIs("get")) method = new LVStringConstant("GET");
						else if (tokenIs("post")) method = new LVStringConstant(
								"POST");
						else dontUnderstandToken();
						return new ServletHSet(line, (ServletHElement) getHandler(),
								method, ServletHSet.METHOD);
					}
				}
			}
		}
		else if (tokenIs("name"))
		{
			// set the name of {element} to {value}
			getNextToken();
			if (tokenIs("of"))
			{
				getNextToken();
				if (isSymbol())
				{
					if (getHandler() instanceof ServletHElement)
					{
						skip("to");
						return new ServletHSet(line, (ServletHElement) getHandler(),
								getValue(), ServletHSet.NAME);
					}
				}
			}
		}
		else if (tokenIs("value"))
		{
			// set the value of {element} to {value}
			// set the value of {cookie} to {value}
			getNextToken();
			if (tokenIs("of"))
			{
				getNextToken();
				if (isSymbol())
				{
					if (getHandler() instanceof ServletHElement)
					{
						skip("to");
						return new ServletHSet(line, (ServletHElement) getHandler(),
								getValue(), ServletHSet.VALUE);
					}
					if (getHandler() instanceof ServletHCookie)
					{
						skip("to");
						return new ServletHSet(line, (ServletHCookie) getHandler(),
								getValue(), false);
					}
				}
			}
		}
		else if (tokenIs("expiry"))
		{
			// set the expiry of {cookie} to {value}
			getNextToken();
			if (tokenIs("of"))
			{
				getNextToken();
				if (isSymbol())
				{
					if (getHandler() instanceof ServletHCookie)
					{
						skip("to");
						return new ServletHSet(line, (ServletHCookie) getHandler(),
								getValue(), true);
					}
				}
			}
		}
		else if (tokenIs("border"))
		{
			// set the border of {element} to {value}
			getNextToken();
			if (tokenIs("of"))
			{
				getNextToken();
				if (isSymbol())
				{
					if (getHandler() instanceof ServletHElement)
					{
						skip("to");
						return new ServletHSet(line, (ServletHElement) getHandler(),
								getValue(), ServletHSet.BORDER);
					}
				}
			}
		}
		else if (tokenIs("width"))
		{
			// set the width of {element} to auto/{width} [percent]
			getNextToken();
			if (tokenIs("of"))
			{
				getNextToken();
				if (isSymbol())
				{
					if (getHandler() instanceof ServletHElement)
					{
						element = (ServletHElement) getHandler();
						skip("to");
						if (tokenIs("auto")) return new ServletHSet(line, element,
								new LVConstant(-1), ServletHSet.WIDTH);
						LVValue width = getValue();
						getNextToken();
						if (tokenIs("percent")) return new ServletHSet(line, element,
								width, ServletHSet.PCWIDTH);
						unGetToken();
						return new ServletHSet(line, element, width,
								ServletHSet.WIDTH);
					}
				}
			}
		}
		else if (tokenIs("height"))
		{
			// set the height of {element} to {height}
			getNextToken();
			if (tokenIs("of"))
			{
				getNextToken();
				if (isSymbol())
				{
					if (getHandler() instanceof ServletHElement)
					{
						element = (ServletHElement) getHandler();
						skip("to");
						return new ServletHSet(line, element, getValue(),
								ServletHSet.HEIGHT);
					}
				}
			}
		}
		else if (tokenIs("brackets"))
		{
			// set the brackets of {template} to {left} {right}
			getNextToken();
			if (tokenIs("of"))
			{
				getNextToken();
				if (isSymbol())
				{
					if (getHandler() instanceof ServletHTemplate)
					{
						ServletHTemplate template = (ServletHTemplate) getHandler();
						skip("to");
						LVValue left = getValue();
						LVValue right = getNextValue();
						return new ServletHSet(line, template, left, right, false);
					}
				}
			}
		}
		else if (tokenIs("source"))
		{
			// set the source of {template} to {source}
			getNextToken();
			if (tokenIs("of"))
			{
				getNextToken();
				if (isSymbol())
				{
					if (getHandler() instanceof ServletHTemplate)
					{
						ServletHTemplate template = (ServletHTemplate) getHandler();
						skip("to");
						return new ServletHSet(line, template, getValue(), false);
					}
				}
			}
		}
		else if (tokenIs("option"))
		{
			// set option {name} of {template} to {value}
			LVValue name = getNextValue();
			skip("of");
			if (isSymbol())
			{
				if (getHandler() instanceof ServletHTemplate)
				{
					ServletHTemplate template = (ServletHTemplate) getHandler();
					skip("to");
					return new ServletHSet(line, template, name, getValue());
				}
			}
		}
		else if (tokenIs("parameter"))
		{
			// set parameter {name} of {template} to {value}
			LVValue name = getNextValue();
			skip("of");
			if (isSymbol())
			{
				if (getHandler() instanceof ServletHTemplate)
				{
					ServletHTemplate template = (ServletHTemplate) getHandler();
					skip("to");
					if (isSymbol())
					{
						if (getHandler() instanceof LHHashtable) return new ServletHSet(
								line, template, name, (LHHashtable) getHandler());
					}
					return new ServletHSet(line, template, name, getValue(), true);
				}
			}
		}
		else if (tokenIs("parameters"))
		{
			// set parameters of {template} to {table}
			skip("of");
			if (isSymbol())
			{
				if (getHandler() instanceof ServletHTemplate)
				{
					ServletHTemplate template = (ServletHTemplate) getHandler();
					skip("to");
					String token = getToken();
					LVValue value = getValue();
					if (value instanceof LVHashtable) return new ServletHSet(line,
							template, value, true);
					throw new LLException(ServletLMessages.parametersExpected(token));
				}
			}
		}
		else if (tokenIs("session"))
		{
			// set session value {name} to {value}
			getNextToken();
			if (tokenIs("value"))
			{
				LVValue name = getNextValue();
				skip("to");
				return new ServletHSet(line, name, getValue());
			}
		}
		else if (tokenIs("path"))
		{
			// set the path of {uploader} to {path}
			getNextToken();
			if (tokenIs("of"))
			{
				getNextToken();
				if (isSymbol())
				{
					if (getHandler() instanceof ServletHUploader)
					{
						ServletHUploader uploader = (ServletHUploader) getHandler();
						skip("to");
						return new ServletHSet(line, uploader, getValue());
					}
				}
			}
		}
		return null;
	}
}

