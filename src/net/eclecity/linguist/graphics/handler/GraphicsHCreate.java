// GraphicsHCreate.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import java.util.Vector;

import net.eclecity.linguist.graphics.value.GraphicsVColor;
import net.eclecity.linguist.graphics.value.GraphicsVFont;
import net.eclecity.linguist.graphics.value.GraphicsVLocation;
import net.eclecity.linguist.graphics.value.GraphicsVSize;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Create a new item.<br>
	This class uses several overloaded constructors to create different objects.
	<pre>
	[1.006 GT]  04/04/02  Add 'create dialog'.
	[1.005 GT]  15/02/01  Add 'create buttongroup'.
	[1.004 GT]  05/01/01  Add 'create canvas'.
	[1.003 GT]  16/12/00  Add 'create styledtext'.
	[1.002 GT]  26/08/00  Add 'create frame'.
	[1.001 GT]  26/08/00  Pre-existing.
	</pre>
*/
public class GraphicsHCreate extends LHHandler
{
	/***************************************************************************
		Create a new frame.
	*/
	private GraphicsHFrame frame=null;
	private LVValue title;
	private GraphicsVLocation location;
	private GraphicsVSize size;
	private boolean resizable;

	public GraphicsHCreate(int line,GraphicsHFrame frame,LVValue title,
		GraphicsVLocation location,GraphicsVSize size,boolean resizable)
	{
		this.line=line;
		this.frame=frame;
		this.title=title;
		this.location=location;
		this.size=size;
		this.resizable=resizable;
	}

	/***************************************************************************
		Create a new window.
	*/
	private GraphicsHWindow window=null;
	private GraphicsHWindow parent=null;
	private int style;

	public GraphicsHCreate(int line,GraphicsHWindow window,GraphicsHWindow parent,LVValue title,
		GraphicsVLocation location,GraphicsVSize size,int style,boolean resizable)
	{
		this.line=line;
		this.parent=parent;
		this.window=window;
		this.title=title;
		this.location=location;
		this.size=size;
		this.style=style;
		this.resizable=resizable;
	}

	/***************************************************************************
		Create a new panel.
	*/
	private GraphicsHPanel panel=null;

	public GraphicsHCreate(int line,GraphicsHPanel panel,GraphicsHComponent container,
		GraphicsVLocation location,GraphicsVSize size)
	{
		this.line=line;
		this.panel=panel;
		this.container=container;
		this.location=location;
		this.size=size;
	}

	/***************************************************************************
		Create a new Button.
	*/
	private GraphicsHButton button=null;
	private int buttonType;
	private LVValue buttonText;

	public GraphicsHCreate(int line,GraphicsHButton button,GraphicsHComponent container,int buttonType,
		LVValue buttonText,GraphicsVLocation location,GraphicsVSize size)
	{
		this.line=line;
		this.button=button;
		this.container=container;
		this.buttonType=buttonType;
		this.buttonText=buttonText;
		this.location=location;
		this.size=size;
	}

	/***************************************************************************
		Create a new Label.
	*/
	private GraphicsHLabel label=null;
	private GraphicsHComponent container;
	private LVValue labelText;

	public GraphicsHCreate(int line, GraphicsHLabel label,GraphicsHComponent container,LVValue labelText,
		GraphicsVLocation location,GraphicsVSize size)
	{
		this.line=line;
		this.label=label;
		this.container=container;
		this.labelText=labelText;
		this.location=location;
		this.size=size;
	}

	/***************************************************************************
		Create a new Canvas.
	*/
	private GraphicsHCanvas canvas=null;

	public GraphicsHCreate(int line, GraphicsHCanvas canvas,GraphicsHComponent container,
		GraphicsVLocation location,GraphicsVSize size)
	{
		this.line=line;
		this.canvas=canvas;
		this.container=container;
		this.location=location;
		this.size=size;
	}

	/***************************************************************************
		Create a new Styledtext.
	*/
	private GraphicsHStyledtext styledText=null;

	public GraphicsHCreate(int line, GraphicsHStyledtext styledText,GraphicsHComponent container,LVValue labelText,
		GraphicsVLocation location,GraphicsVSize size)
	{
		this.line=line;
		this.styledText=styledText;
		this.container=container;
		this.labelText=labelText;
		this.location=location;
		this.size=size;
	}

	/***************************************************************************
		Create a new Combobox.
	*/
	private GraphicsHCombobox comboBox=null;

	public GraphicsHCreate(int line,GraphicsHCombobox comboBox,GraphicsHComponent container,
		GraphicsVLocation location,GraphicsVSize size)
	{
		this.line=line;
		this.comboBox=comboBox;
		this.container=container;
		this.location=location;
		this.size=size;
	}

	/***************************************************************************
		Create a new text area.
	*/
	private GraphicsHTextarea textArea=null;
	private LVValue textareaText;
	private boolean editable;

	public GraphicsHCreate(int line,GraphicsHTextarea textArea,GraphicsHComponent container,LVValue textareaText,
		GraphicsVLocation location,GraphicsVSize size,boolean editable)
	{
		this.line=line;
		this.textArea=textArea;
		this.container=container;
		this.textareaText=textareaText;
		this.location=location;
		this.size=size;
		this.editable=editable;
	}

	/***************************************************************************
		Create a new text field.
	*/
	private GraphicsHTextfield textField=null;
	private LVValue textfieldText;

	public GraphicsHCreate(int line,GraphicsHTextfield textField,GraphicsHComponent container,LVValue textfieldText,
		GraphicsVLocation location,GraphicsVSize size,boolean editable)
	{
		this.line=line;
		this.textField=textField;
		this.container=container;
		this.textfieldText=textfieldText;
		this.location=location;
		this.size=size;
		this.editable=editable;
	}

	/***************************************************************************
		Create a new textpanel.
	*/
	private GraphicsHTextPanel textPanel=null;
	private LVValue textpanelText;
	private boolean scrolling;

	public GraphicsHCreate(int line,GraphicsHTextPanel textPanel,GraphicsHComponent container,LVValue textpanelText,
		GraphicsVLocation location,GraphicsVSize size,boolean scrolling)
	{
		this.line=line;
		this.textPanel=textPanel;
		this.container=container;
		this.textpanelText=textpanelText;
		this.location=location;
		this.size=size;
		this.scrolling=scrolling;
	}

	/***************************************************************************
		Create a new Cursor.
	*/
	private GraphicsHCursor cursor=null;
	private int cursorType;

	public GraphicsHCreate(int line,GraphicsHCursor cursor,int cursorType)
	{
		this.line=line;
		this.cursor=cursor;
		this.cursorType=cursorType;
	}

	/***************************************************************************
		Create a new Color.
	*/
	private GraphicsHColor color=null;
	private GraphicsVColor colorValue;

	public GraphicsHCreate(int line,GraphicsHColor color,GraphicsVColor colorValue)
	{
		this.line=line;
		this.color=color;
		this.colorValue=colorValue;
	}

	/***************************************************************************
		Create a new Font.
	*/
	private GraphicsHFont font=null;
	private GraphicsVFont fontValue;

	public GraphicsHCreate(int line,GraphicsHFont font,GraphicsVFont fontValue)
	{
		this.line=line;
		this.font=font;
		this.fontValue=fontValue;
	}

	/***************************************************************************
		Create a new Border.
	*/
	private GraphicsHBorder border=null;
	private int borderType;
	private LVValue top;
	private LVValue left;
	private LVValue bottom;
	private LVValue right;
	private int etchType;
	private GraphicsVColor highlight;
	private GraphicsVColor shadow;
	private GraphicsVColor borderColor;
	private LVValue text;
	private LVValue thickness;
	private int justification;
	private int position;
	private GraphicsVFont borderFont;

	/***************************************************************************
		Create an empty border.
	*/
	public GraphicsHCreate(int line,GraphicsHBorder border,int borderType,LVValue top,LVValue left,LVValue bottom,LVValue right)
	{
		this.line=line;
		this.border=border;
		this.borderType=borderType;
		this.top=top;
		this.left=left;
		this.bottom=bottom;
		this.right=right;
	}

	/***************************************************************************
		Create an etched border.
	*/
	public GraphicsHCreate(int line,GraphicsHBorder border,int borderType,int etchType,GraphicsVColor highlight,GraphicsVColor shadow)
	{
		this.line=line;
		this.border=border;
		this.borderType=borderType;
		this.etchType=etchType;
		this.highlight=highlight;
		this.shadow=shadow;
	}

	/***************************************************************************
		Create a line border.
	*/
	public GraphicsHCreate(int line,GraphicsHBorder border,int borderType,GraphicsVColor borderColor,LVValue thickness)
	{
		this.line=line;
		this.border=border;
		this.borderType=borderType;
		this.borderColor=borderColor;
		this.thickness=thickness;
	}

	/***************************************************************************
		Create a titled border.
	*/
	public GraphicsHCreate(int line,GraphicsHBorder border,int borderType,LVValue text,int justification,
		int position,GraphicsVFont borderFont,GraphicsVColor borderColor)
	{
		this.line=line;
		this.border=border;
		this.borderType=borderType;
		this.text=text;
		this.justification=justification;
		this.position=position;
		this.borderFont=borderFont;
		this.borderColor=borderColor;
	}

	/***************************************************************************
		Create a popup menu.
	*/
	private GraphicsHPopupmenu popup=null;

	public GraphicsHCreate(int line,GraphicsHPopupmenu popup,GraphicsHComponent container)
	{
		this.line=line;
		this.popup=popup;
		this.container=container;
	}

	/***************************************************************************
		Create a menu.
	*/
	private GraphicsHMenu menu=null;

	public GraphicsHCreate(int line,GraphicsHMenu menu,LVValue text)
	{
		this.line=line;
		this.menu=menu;
		this.text=text;
	}

	/***************************************************************************
		Create a menu item.
	*/
	private GraphicsHMenuitem menuItem=null;

	public GraphicsHCreate(int line,GraphicsHMenuitem menuItem,LVValue text,int style)
	{
		this.line=line;
		this.menuItem=menuItem;
		this.text=text;
		this.style=style;
	}

	/***************************************************************************
		Create a menu item.
	*/
	private GraphicsHButtonGroup buttonGroup=null;

	public GraphicsHCreate(int line,GraphicsHButtonGroup buttonGroup)
	{
		this.line=line;
		this.buttonGroup=buttonGroup;
	}

	/***************************************************************************
		Create a dialog.
	*/
	private GraphicsHDialog dialog=null;
	private int type;
	private Vector options;

	public GraphicsHCreate(int line,GraphicsHDialog dialog,int type,
		GraphicsHWindow window,LVValue title,LVValue text,Vector options)
	{
		super(line);
		this.dialog=dialog;
		this.type=type;
		this.window=window;
		this.title=title;
		this.text=text;
		this.options=options;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		if (frame!=null) frame.create(title,location,size,resizable);
		else if (window!=null) window.create(parent,title,location,size,style,resizable);
		else if (panel!=null) panel.create(container,location,size);
		else if (button!=null) button.create(container,buttonType,buttonText,location,size);
		else if (label!=null) label.create(container,labelText,location,size);
		else if (canvas!=null) canvas.create(container,location,size);
		else if (styledText!=null) styledText.create(container,labelText,location,size);
		else if (comboBox!=null) comboBox.create(container,location,size);
		else if (textArea!=null) textArea.create(container,textareaText,location,size,editable);
		else if (textField!=null) textField.create(container,textfieldText,location,size,editable);
		else if (textPanel!=null) textPanel.create(container,textpanelText,location,size,scrolling);
		else if (cursor!=null) cursor.create(cursorType);
		else if (color!=null) color.create(colorValue);
		else if (font!=null) font.create(fontValue);
		else if (border!=null)
		{
			switch (borderType)
			{
			case GraphicsHBorder.EMPTY_BORDER:
				border.createEmptyBorder(top,left,bottom,right);
				break;
			case GraphicsHBorder.ETCHED_BORDER:
				border.createEtchedBorder(etchType,highlight,shadow);
				break;
			case GraphicsHBorder.BEVEL_BORDER:
				border.createBevelBorder(etchType,highlight,shadow);
				break;
			case GraphicsHBorder.LINE_BORDER:
				border.createLineBorder(borderColor,thickness);
				break;
			case GraphicsHBorder.TITLED_BORDER:
				border.createTitledBorder(text,justification,position,borderFont,borderColor);
				break;
			}
		}
		else if (popup!=null) popup.create(container);
		else if (menu!=null) menu.create(text);
		else if (menuItem!=null) menuItem.create(text,style);
		else if (buttonGroup!=null) buttonGroup.create();
		else if (dialog!=null) dialog.create(type,window,title,text,options);
		return pc+1;
	}
}

