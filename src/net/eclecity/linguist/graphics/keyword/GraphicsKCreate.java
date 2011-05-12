//	GraphicsKCreate.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.keyword;

import java.awt.Color;
import java.awt.Cursor;
import java.util.Vector;

import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import net.eclecity.linguist.graphics.GraphicsLMessages;
import net.eclecity.linguist.graphics.handler.GraphicsHBorder;
import net.eclecity.linguist.graphics.handler.GraphicsHButton;
import net.eclecity.linguist.graphics.handler.GraphicsHButtonGroup;
import net.eclecity.linguist.graphics.handler.GraphicsHCanvas;
import net.eclecity.linguist.graphics.handler.GraphicsHColor;
import net.eclecity.linguist.graphics.handler.GraphicsHCombobox;
import net.eclecity.linguist.graphics.handler.GraphicsHComponent;
import net.eclecity.linguist.graphics.handler.GraphicsHCreate;
import net.eclecity.linguist.graphics.handler.GraphicsHCursor;
import net.eclecity.linguist.graphics.handler.GraphicsHDialog;
import net.eclecity.linguist.graphics.handler.GraphicsHFont;
import net.eclecity.linguist.graphics.handler.GraphicsHFrame;
import net.eclecity.linguist.graphics.handler.GraphicsHLabel;
import net.eclecity.linguist.graphics.handler.GraphicsHMenu;
import net.eclecity.linguist.graphics.handler.GraphicsHMenuitem;
import net.eclecity.linguist.graphics.handler.GraphicsHPanel;
import net.eclecity.linguist.graphics.handler.GraphicsHPopupmenu;
import net.eclecity.linguist.graphics.handler.GraphicsHStyledtext;
import net.eclecity.linguist.graphics.handler.GraphicsHTextControl;
import net.eclecity.linguist.graphics.handler.GraphicsHTextPanel;
import net.eclecity.linguist.graphics.handler.GraphicsHTextarea;
import net.eclecity.linguist.graphics.handler.GraphicsHTextfield;
import net.eclecity.linguist.graphics.handler.GraphicsHWindow;
import net.eclecity.linguist.graphics.value.GraphicsVColor;
import net.eclecity.linguist.graphics.value.GraphicsVFont;
import net.eclecity.linguist.graphics.value.GraphicsVLocation;
import net.eclecity.linguist.graphics.value.GraphicsVScreenCenter;
import net.eclecity.linguist.graphics.value.GraphicsVSize;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.value.LVConstant;
import net.eclecity.linguist.value.LVStringConstant;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Create a new item.<br>
	Syntax:<pre>
	create {frame}
		[title {string}]
		[at {location}]
		[size {size}]
		[resizable]
	create {window}
		[parent {window}]
		[title {string}]
		[at {location}]
		[size {size}]
		[style plain/mask]
		[resizable]
	create {panel}/{spreadsheet} [in {container}]
		[at {location}]
		[size {size}]
		[scrolling]
	create {button} [in {container}]
		[type toggle/radio/checkbox]
		[text {string}]
		[at {location}]
		[size {size}]
	create {label} [in {container}]
		[text {string}]
		[at {location}]
		[size {size}]
	create {canvas} [in {container}]
		[at {location}]
		[size {size}]
	create {styledtext} [in {container}]
		[text {string}]
		[at {location}]
		[size {size}]
	create {combobox} in {container}
		[at {location}]
		[size {size}]
	create {textarea}/{textfield} in {container}
		[text {string}]
		[at {location}]
		[size {size}]
	create {textpanel} in {container}
		[text {string}]
		[at {location}]
		[size {size}]
		[scrolling]
	create {cursor} default/hand/wait...
	create {color} {colordef}
	create {font} {fontdef}
	create {border} style empty
		[top {value}]
		[left {value}]
		[bottom {value}]
		[right {value}]
	create {border} style etched in/out
		[highlight {color}]
		[shadow {color}]
	create {border} style bevel raised/lowered
		[highlight {color}]
		[shadow {color}]
	create {border} style line
		[color {color}]
		[thickness {thickness}]
	create {border} style titled
		[title {title}]
		[justification left/right/center/centre]
		[position [above/below] top/bottom ]
		[color {color}]
	create {dialog} [in {window}]
		[title {title}]
		[text {text}]
		[option {option} [and {option}...]
	create {popupmenu} in {container}
	create {menu} {text}
	create {menuitem} {text} [style checkbox/radiobutton]
	create {buttongroup}

	[1.006 GT]  04/04/02  Add 'create dialog'.
	[1.005 GT]  15/02/01  Add 'create buttongroup'.
	[1.004 GT]  05/01/01  Add 'create canvas'.
	[1.003 GT]  16/12/00  Add 'create styledtext'.
	[1.002 GT]  26/08/00  Add 'create frame'.
	[1.001 GT]  26/08/00  Pre-existing.
	</pre>
*/
public class GraphicsKCreate extends GraphicsKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	  	if (isSymbol())
	  	{
	  		LHHandler handler=getHandler();
			if (handler instanceof GraphicsHFrame)
			{
				// create {frame}
				//	   [title {string}]
				//	   [at {location}]
				//	   [size {size}]
				//	   [resizable]
		   	LVValue title=new LVStringConstant("Untitled");
		   	GraphicsVLocation location=null;
		   	GraphicsVSize size=null;
		   	boolean resizable=false;
		   	while (true)
		   	{
		   		getNextToken();
		   		if (tokenIs("title"))
		   		{
		   			getNextToken();
		   			title=getValue();
		   		}
		   		else if (tokenIs("at"))
		   		{
						getNextToken();
						location=getLocation();
		   		}
		   		else if (tokenIs("size"))
		   		{
						getNextToken();
						size=getSize();
		   		}
		   		else if (tokenIs("resizable")) resizable=true;
		   		else break;
		   	}
		   	unGetToken();
		   	return new GraphicsHCreate(line,(GraphicsHFrame)handler,title,location,size,resizable);
		   }
			if (handler instanceof GraphicsHWindow)
			{
				// create {window}
				//    [parent {window}]
				//	   [title {string}]
				//	   [at {location}]
				//	   [size {size}]
				//    [style plain/mask]
				//	   [resizable]
				GraphicsHWindow parent=null;
		   	LVValue title=new LVStringConstant("New Window");
		   	GraphicsVLocation location=new GraphicsVScreenCenter();
		   	GraphicsVSize size=new GraphicsVSize(600,400);
		   	int style=GraphicsHWindow.STYLE_DEFAULT;
		   	boolean resizable=false;
		   	while (true)
		   	{
		   		getNextToken();
		   		if (tokenIs("parent"))
		   		{
		   			getNextToken();
		   			if (isSymbol())
		   			{
		   				if (getHandler() instanceof GraphicsHWindow) parent=(GraphicsHWindow)getHandler();
			   			else throw new LLException(GraphicsLMessages.windowExpected(getToken()));
		   			}
		   			else throw new LLException(GraphicsLMessages.windowExpected(getToken()));
		   		}
		   		else if (tokenIs("title"))
		   		{
		   			getNextToken();
		   			title=getValue();
		   		}
		   		else if (tokenIs("at"))
		   		{
						getNextToken();
						location=getLocation();
		   		}
		   		else if (tokenIs("size"))
		   		{
						getNextToken();
						size=getSize();
		   		}
		   		else if (tokenIs("style"))
		   		{
		   			getNextToken();
		   			if (tokenIs("plain")) style=GraphicsHWindow.STYLE_PLAIN;
		   			else if (tokenIs("mask")) style=GraphicsHWindow.STYLE_MASK;
		   			else if (tokenIs("child")) style=GraphicsHWindow.STYLE_CHILD;
		   			else dontUnderstandToken();
		   		}
		   		else if (tokenIs("resizable")) resizable=true;
		   		else break;
		   	}
		   	unGetToken();
		   	return new GraphicsHCreate(line,(GraphicsHWindow)handler,parent,title,location,size,style,resizable);
		   }
			if (handler instanceof GraphicsHPanel)
			{
				// create {panel} [in {container}]
				//	[at {location}]
				//	[size {size}]
				GraphicsHComponent container=null;
				getNextToken();
				if (tokenIs("in"))
				{
					getNextToken();
					if (isSymbol())
					{
						if (getHandler() instanceof GraphicsHComponent)
						{
							container=(GraphicsHComponent)getHandler();
						}
						else throw new LLException(GraphicsLMessages.componentExpected(getToken()));
					}
					else throw new LLException(GraphicsLMessages.componentExpected(getToken()));
				}
				else unGetToken();
		   	GraphicsVLocation location=null;
		   	GraphicsVSize size=null;
		   	while (true)
		   	{
		   		getNextToken();
		   		if (tokenIs("at")) location=getNextLocation();
		   		else if (tokenIs("size")) size=getNextSize();
		   		else break;
		   	}
		   	unGetToken();
				return new GraphicsHCreate(line,(GraphicsHPanel)handler,container,location,size);
		   }
			if (handler instanceof GraphicsHButton)
			{
				// create {button} [in {container}]
				// [type toggle/radio/checkbox]
				//	[text {string}]
				//	[at {location}]
				//	[size {size}]
				GraphicsHComponent container=null;
				getNextToken();
				if (tokenIs("in"))
				{
					getNextToken();
					if (isSymbol())
					{
						if (getHandler() instanceof GraphicsHComponent)
						{
							container=(GraphicsHComponent)getHandler();
						}
						else throw new LLException(GraphicsLMessages.componentExpected(getToken()));
					}
					else throw new LLException(GraphicsLMessages.componentExpected(getToken()));
				}
				else unGetToken();
				int type=GraphicsHButton.TYPE_DEFAULT;
		   	LVValue text=new LVStringConstant("");
		   	GraphicsVLocation location=null;
		   	GraphicsVSize size=null;
		   	while (true)
		   	{
		   		getNextToken();
		   		if (tokenIs("type"))
		   		{
		   			getNextToken();
		   			if (tokenIs("toggle")) type|=GraphicsHButton.TYPE_TOGGraphicsLE;
		   			else if (tokenIs("radio")) type|=GraphicsHButton.TYPE_RADIO;
		   			else if (tokenIs("checkbox")) type|=GraphicsHButton.TYPE_CHECKBOX;
		   			else dontUnderstandToken();
		   		}
		   		else if (tokenIs("text")) text=getNextValue();
		   		else if (tokenIs("at")) location=getNextLocation();
		   		else if (tokenIs("size")) size=getNextSize();
		   		else break;
		   	}
		   	unGetToken();
		   	return new GraphicsHCreate(line,(GraphicsHButton)handler,container,type,text,location,size);
		   }
			if (handler instanceof GraphicsHLabel)
			{
				// create {label} [in {container}]
				//	[text {string}]
				//	[at {location}]
				//	[size {size}]
				GraphicsHComponent container=null;
				getNextToken();
				if (tokenIs("in"))
				{
					getNextToken();
					if (isSymbol())
					{
						if (getHandler() instanceof GraphicsHComponent)
						{
							container=(GraphicsHComponent)getHandler();
						}
						else throw new LLException(GraphicsLMessages.componentExpected(getToken()));
					}
					else throw new LLException(GraphicsLMessages.componentExpected(getToken()));
				}
				else unGetToken();
		   	LVValue text=new LVStringConstant("");
		   	GraphicsVLocation location=null;
		   	GraphicsVSize size=null;
		   	while (true)
		   	{
		   		getNextToken();
		   		if (tokenIs("text")) text=getNextValue();
		   		else if (tokenIs("at")) location=getNextLocation();
		   		else if (tokenIs("size")) size=getNextSize();
		   		else break;
		   	}
		   	unGetToken();
		   	return new GraphicsHCreate(line,(GraphicsHLabel)handler,container,text,location,size);
		   }
			if (handler instanceof GraphicsHCanvas)
			{
				// create {canvas} [in {container}]
				//	[at {location}]
				//	[size {size}]
				GraphicsHComponent container=null;
				getNextToken();
				if (tokenIs("in"))
				{
					getNextToken();
					if (isSymbol())
					{
						if (getHandler() instanceof GraphicsHComponent)
						{
							container=(GraphicsHComponent)getHandler();
						}
						else throw new LLException(GraphicsLMessages.componentExpected(getToken()));
					}
					else throw new LLException(GraphicsLMessages.componentExpected(getToken()));
				}
				else unGetToken();
		   	GraphicsVLocation location=null;
		   	GraphicsVSize size=null;
		   	while (true)
		   	{
		   		getNextToken();
		   		if (tokenIs("at")) location=getNextLocation();
		   		else if (tokenIs("size")) size=getNextSize();
		   		else break;
		   	}
		   	unGetToken();
		   	return new GraphicsHCreate(line,(GraphicsHCanvas)handler,container,location,size);
		   }
			if (handler instanceof GraphicsHStyledtext)
			{
				// create {styledtext} [in {container}]
				//	[text {string}]
				//	[at {location}]
				//	[size {size}]
				GraphicsHComponent container=null;
				getNextToken();
				if (tokenIs("in"))
				{
					getNextToken();
					if (isSymbol())
					{
						if (getHandler() instanceof GraphicsHComponent)
						{
							container=(GraphicsHComponent)getHandler();
						}
						else throw new LLException(GraphicsLMessages.componentExpected(getToken()));
					}
					else throw new LLException(GraphicsLMessages.componentExpected(getToken()));
				}
				else unGetToken();
		   	LVValue text=new LVStringConstant("");
		   	GraphicsVLocation location=null;
		   	GraphicsVSize size=null;
		   	while (true)
		   	{
		   		getNextToken();
		   		if (tokenIs("text")) text=getNextValue();
		   		else if (tokenIs("at")) location=getNextLocation();
		   		else if (tokenIs("size")) size=getNextSize();
		   		else break;
		   	}
		   	unGetToken();
		   	return new GraphicsHCreate(line,(GraphicsHStyledtext)handler,container,text,location,size);
		   }
			if (handler instanceof GraphicsHCombobox)
			{
				// create {combobox} in {container}
				// 	[at {location}]
				// 	[size {size}]
				skip("in");
				GraphicsHComponent container=null;
				if (isSymbol())
				{
					if (getHandler() instanceof GraphicsHComponent)
						container=(GraphicsHComponent)getHandler();
				}
				else throw new LLException(GraphicsLMessages.containerExpected(getToken()));
		   	GraphicsVLocation location=new GraphicsVLocation(10,10);
		   	GraphicsVSize size=null;
		   	while (true)
		   	{
		   		getNextToken();
		   		if (tokenIs("at")) location=getNextLocation();
		   		else if (tokenIs("size")) size=getNextSize();
		   		else break;
		   	}
		   	unGetToken();
		   	return new GraphicsHCreate(line,(GraphicsHCombobox)handler,container,location,size);
		   }
			if (handler instanceof GraphicsHTextControl)
			{
				// create {textarea}/{textfield} in {container}
				//	[text {string}]
				//	[at {location}]
				//	[size {size}]
				// [readonly]
				skip("in");
				GraphicsHComponent container=null;
				if (isSymbol())
				{
					if (getHandler() instanceof GraphicsHComponent)
						container=(GraphicsHComponent)getHandler();
				}
				else throw new LLException(GraphicsLMessages.containerExpected(getToken()));
		   	LVValue text=new LVStringConstant("");
		   	GraphicsVLocation location=new GraphicsVLocation(10,10);
		   	GraphicsVSize size=null;
		   	boolean editable=true;
		   	while (true)
		   	{
		   		getNextToken();
		   		if (tokenIs("text")) text=getNextValue();
		   		else if (tokenIs("at")) location=getNextLocation();
		   		else if (tokenIs("size")) size=getNextSize();
		   		else if (tokenIs("readonly")) editable=false;
		   		else break;
		   	}
		   	unGetToken();
		   	if (handler instanceof GraphicsHTextarea)
			   	return new GraphicsHCreate(line,(GraphicsHTextarea)handler,container,text,location,size,editable);
			   return new GraphicsHCreate(line,(GraphicsHTextfield)handler,container,text,location,size,editable);
		   }
			if (handler instanceof GraphicsHTextPanel)
			{
				// create {textpanel} in {container}
				//	[text {string}]
				//	[at {location}]
				//	[size {size}]
				// [scrolling]
				skip("in");
				GraphicsHComponent container=null;
				if (isSymbol())
				{
					if (getHandler() instanceof GraphicsHComponent)
						container=(GraphicsHComponent)getHandler();
				}
				else throw new LLException(GraphicsLMessages.containerExpected(getToken()));
		   	LVValue text=new LVStringConstant("");
		   	GraphicsVLocation location=new GraphicsVLocation(10,10);
		   	GraphicsVSize size=null;
		   	boolean scrolling=false;
		   	while (true)
		   	{
		   		getNextToken();
		   		if (tokenIs("text")) text=getNextValue();
		   		else if (tokenIs("at")) location=getNextLocation();
		   		else if (tokenIs("size")) size=getNextSize();
		   		else if (tokenIs("scrolling")) scrolling=true;
		   		else break;
		   	}
		   	unGetToken();
		   	return new GraphicsHCreate(line,(GraphicsHTextPanel)handler,container,text,location,size,scrolling);
		   }
			if (handler instanceof GraphicsHCursor)
			{
				// create {cursor} default/hand/wait...
				getNextToken();
				if (tokenIs("default")) return new GraphicsHCreate(line,(GraphicsHCursor)handler,Cursor.DEFAULT_CURSOR);
				else if (tokenIs("hand")) return new GraphicsHCreate(line,(GraphicsHCursor)handler,Cursor.HAND_CURSOR);
				else if (tokenIs("wait")) return new GraphicsHCreate(line,(GraphicsHCursor)handler,Cursor.WAIT_CURSOR);
		   	dontUnderstandToken();
		   }
			if (handler instanceof GraphicsHColor)
			{
				// create {color} {colordef}
				getNextToken();
				return new GraphicsHCreate(line,(GraphicsHColor)handler,getColor());
		   }
			if (handler instanceof GraphicsHFont)
			{
				// create {font} {fontdef}
				getNextToken();
				return new GraphicsHCreate(line,(GraphicsHFont)handler,getFont());
		   }
			if (handler instanceof GraphicsHBorder)
			{
				// create {border} ...
				getNextToken();
				if (tokenIs("style"))
				{
					LVValue top=new LVConstant(0);
					LVValue left=new LVConstant(0);
					LVValue bottom=new LVConstant(0);
					LVValue right=new LVConstant(0);
					getNextToken();
					if (tokenIs("empty"))
					{
						// create {border} style empty
						//		[top {value}]
						//		[left {value}]
						//		[bottom {value}]
						//		[right {value}]
						while (true)
						{
							getNextToken();
							if (tokenIs("top")) top=getNextValue();
							else if (tokenIs("left")) left=getNextValue();
							else if (tokenIs("bottom")) bottom=getNextValue();
							else if (tokenIs("right")) right=getNextValue();
							else
							{
								unGetToken();
								break;
							}
						}
						return new GraphicsHCreate(line,(GraphicsHBorder)handler,GraphicsHBorder.EMPTY_BORDER,top,left,bottom,right);
					}
					if (tokenIs("etched"))
					{
						// create {border} style etched in/out
						//		[highlight {color}]
						//		[shadow {color}]
						getNextToken();
						int type=EtchedBorder.LOWERED;
						if (tokenIs("in")) {}
						else if (tokenIs("out")) type=EtchedBorder.RAISED;
						else unGetToken();
						GraphicsVColor highlight=null;
						GraphicsVColor shadow=new GraphicsVColor(Color.darkGray);
						while (true)
						{
							getNextToken();
							if (tokenIs("highlight")) highlight=getNextColor();
							else if (tokenIs("shadow")) shadow=getNextColor();
							else
							{
								unGetToken();
								break;
							}
						}
						return new GraphicsHCreate(line,(GraphicsHBorder)handler,GraphicsHBorder.ETCHED_BORDER,type,highlight,shadow);
					}
					if (tokenIs("bevel"))
					{
						// create {border} style bevel raised/lowered
						//		[highlight {color}]
						//		[shadow {color}]
						getNextToken();
						int type=BevelBorder.LOWERED;
						if (tokenIs("lowered")) {}
						else if (tokenIs("raised")) type=BevelBorder.RAISED;
						else unGetToken();
						GraphicsVColor highlight=null;
						GraphicsVColor shadow=new GraphicsVColor(Color.darkGray);
						while (true)
						{
							getNextToken();
							if (tokenIs("highlight")) highlight=getNextColor();
							else if (tokenIs("shadow")) shadow=getNextColor();
							else
							{
								unGetToken();
								break;
							}
						}
						return new GraphicsHCreate(line,(GraphicsHBorder)handler,GraphicsHBorder.BEVEL_BORDER,type,highlight,shadow);
					}
					if (tokenIs("line"))
					{
						// create {border} style line
						//		[color {color}]
						//		[thickness {thickness}]
						GraphicsVColor color=new GraphicsVColor(Color.black);
						LVValue thickness=new LVConstant(1);
						while (true)
						{
							getNextToken();
							if (tokenIs("color") || tokenIs("colour")) color=getNextColor();
							else if (tokenIs("thickness")) thickness=getNextValue();
							else
							{
								unGetToken();
								break;
							}
						}
						return new GraphicsHCreate(line,(GraphicsHBorder)handler,GraphicsHBorder.LINE_BORDER,color,thickness);
					}
					if (tokenIs("titled"))
					{
						// create {border} style titled
						//		[title {title}]
						//		[justification left/right/center/centre]
						//		[position [above/below] top/bottom ]
						//		[color {color}]
						LVValue title=new LVStringConstant("");
						int justification=TitledBorder.DEFAULT_JUSTIFICATION;
						int position=TitledBorder.DEFAULT_POSITION;
						GraphicsVFont font=new GraphicsVFont();
						GraphicsVColor color=new GraphicsVColor(Color.black);
						while (true)
						{
							getNextToken();
							if (tokenIs("title")) title=getNextValue();
							else if (tokenIs("color") || tokenIs("colour")) color=getNextColor();
							else
							{
								unGetToken();
								break;
							}
						}
						return new GraphicsHCreate(line,(GraphicsHBorder)handler,GraphicsHBorder.TITLED_BORDER,
							title,justification,position,font,color);
					}
					throw new LLException(GraphicsLMessages.unknownBorderStyle(getToken()));
				}
		   	throw new LLException(GraphicsLMessages.borderStyleRequired());
		   }
			if (handler instanceof GraphicsHPopupmenu)
			{
				// create {popupmenu} in {container}
				skip("in");
				if (isSymbol())
				{
					if (getHandler() instanceof GraphicsHComponent)
						return new GraphicsHCreate(line,(GraphicsHPopupmenu)handler,(GraphicsHComponent)getHandler());
				}
				warning(this,GraphicsLMessages.componentExpected(getToken()));
		   }
			if (handler instanceof GraphicsHMenu)
			{
				// create {menu} {text}
				return new GraphicsHCreate(line,(GraphicsHMenu)handler,getNextValue());
		   }
			if (handler instanceof GraphicsHMenuitem)
			{
				// create {menuitem} {text} [style checkbox/radiobutton]
				LVValue text=getNextValue();
				int style=GraphicsHMenuitem.PLAIN;
				getNextToken();
				if (tokenIs("style"))
				{
					getNextToken();
					if (tokenIs("checkbox")) style=GraphicsHMenuitem.CHECKBOX;
					if (tokenIs("radiobutton")) style=GraphicsHMenuitem.RADIOBUTTON;
					else dontUnderstandToken();
				}
				else unGetToken();
				return new GraphicsHCreate(line,(GraphicsHMenuitem)handler,text,style);
		   }
			if (handler instanceof GraphicsHButtonGroup)
			{
				// create {buttongroup}
				return new GraphicsHCreate(line,(GraphicsHButtonGroup)handler);
		   }
			if (handler instanceof GraphicsHDialog)
			{
				/*
					create {dialog} [in {window}]
						[type confirm/input/information]
						[title {title}]
						[text {text}]
						[duration {duration}]
						[option {option} [and {option}...]
				*/
				GraphicsHWindow window=null;
				getNextToken();
				if (tokenIs("in"))
				{
					getNextToken();
					if (isSymbol())
					{
						if (getHandler() instanceof GraphicsHWindow)
							window=(GraphicsHWindow)getHandler();
					}
				}
				else unGetToken();
				int type=GraphicsHDialog.CONFIRM;
				LVValue title=new LVStringConstant("");
				LVValue text=new LVStringConstant("");
				Vector options=new Vector();
	   		while (true)
	   		{
					getNextToken();
					if (tokenIs("type"))
					{
						getNextToken();
						if (tokenIs("confirm")) type=GraphicsHDialog.CONFIRM;
						else if (tokenIs("input")) type=GraphicsHDialog.INPUT;
						else if (tokenIs("information")) type=GraphicsHDialog.INFO;
						else return null;
					}
					else if (tokenIs("title")) title=getNextValue();
					else if (tokenIs("text")) text=getNextValue();
					else if (tokenIs("duration")) options.addElement(getNextValue());
					else if (tokenIs("option") || tokenIs("options"))
					{
						options.addElement(getNextValue());
						while (true)
						{
							getNextToken();
							if (tokenIs("and")) options.addElement(getNextValue());
							else break;
						}
						unGetToken();
					}
					else
					{
						unGetToken();
						break;
						}
				}
				return new GraphicsHCreate(line,(GraphicsHDialog)handler,type,window,title,text,options);
			}
		}
	   return null;
	}
}

