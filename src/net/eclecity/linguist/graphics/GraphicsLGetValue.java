//	GraphicsLGetValue.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics;

import net.eclecity.linguist.graphics.handler.GraphicsHButton;
import net.eclecity.linguist.graphics.handler.GraphicsHCombobox;
import net.eclecity.linguist.graphics.handler.GraphicsHComponent;
import net.eclecity.linguist.graphics.handler.GraphicsHDialog;
import net.eclecity.linguist.graphics.handler.GraphicsHMenuitem;
import net.eclecity.linguist.graphics.handler.GraphicsHWindow;
import net.eclecity.linguist.graphics.value.GraphicsVAction;
import net.eclecity.linguist.graphics.value.GraphicsVBottom;
import net.eclecity.linguist.graphics.value.GraphicsVCombobox;
import net.eclecity.linguist.graphics.value.GraphicsVDescription;
import net.eclecity.linguist.graphics.value.GraphicsVHeight;
import net.eclecity.linguist.graphics.value.GraphicsVKeyChar;
import net.eclecity.linguist.graphics.value.GraphicsVKeyCode;
import net.eclecity.linguist.graphics.value.GraphicsVLeft;
import net.eclecity.linguist.graphics.value.GraphicsVMouse;
import net.eclecity.linguist.graphics.value.GraphicsVRight;
import net.eclecity.linguist.graphics.value.GraphicsVScreenHeight;
import net.eclecity.linguist.graphics.value.GraphicsVScreenSize;
import net.eclecity.linguist.graphics.value.GraphicsVScreenWidth;
import net.eclecity.linguist.graphics.value.GraphicsVSelectionIndex;
import net.eclecity.linguist.graphics.value.GraphicsVTarget;
import net.eclecity.linguist.graphics.value.GraphicsVTop;
import net.eclecity.linguist.graphics.value.GraphicsVWidth;
import net.eclecity.linguist.main.LLCompiler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLGetValue;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Get a graphics value; one of the following:
	<pre>
	the screen size/width/height
	the description of {component}
	the left/right/top/bottom of {component}
	the width of {component}
	the height of {component}
	the content width of {window}
	the content height of {window}
	the left/right/top/bottom inset of {component}
	the mouse left
	the mouse top
	the key char/code
	the target
	the action of {button}/{menuitem}
	the selection index of {combobox}
	the row/column of {spreadsheet}
	{combobox}
	{spreadsheet} row {row} column {column}
	the choice/value of {dialog}
	<p>
	[1.001 GT]  20/10/00  Pre-existing.
	</pre>
*/
public class GraphicsLGetValue extends LLGetValue
{
	/********************************************************************
		Get a graphic value.
	*/
	public LVValue getValue(LLCompiler compiler) throws LLException
	{
		this.compiler=compiler;
		numeric=true;
		boolean inset=false;
		if (isSymbol())
		{
			if (getHandler() instanceof GraphicsHCombobox)
			{
				return new GraphicsVCombobox((GraphicsHCombobox)getHandler());
			}
		   warning(this,GraphicsLMessages.componentExpected(getToken()));
		   return null;
		}
	   if (tokenIs("the"))
	   {
	   	getNextToken();
		   if (tokenIs("screen"))
		   {
				// the screen size/width/height
		   	getNextToken();
		   	if (tokenIs("size")) return new GraphicsVScreenSize();
		   	if (tokenIs("width")) return new GraphicsVScreenWidth();
		   	if (tokenIs("height")) return new GraphicsVScreenHeight();
		   }
		   else if (tokenIs("description"))
		   {
		   	skip("of");
		   	if (isSymbol())
		   	{
		   		if (getHandler() instanceof GraphicsHComponent)
		   		{
		   			return new GraphicsVDescription((GraphicsHComponent)getHandler());
		   		}
		   		warning(this,GraphicsLMessages.componentExpected(getToken()));
		   	}
		   }
		   else if (tokenIs("left"))
		   {
		   	getNextToken();
		   	if (tokenIs("inset")) inset=true;
		   	else unGetToken();
		   	skip("of");
		   	if (isSymbol())
		   	{
		   		if (getHandler() instanceof GraphicsHComponent)
		   		{
		   			return new GraphicsVLeft((GraphicsHComponent)getHandler(),inset);
		   		}
		   		warning(this,GraphicsLMessages.componentExpected(getToken()));
		   	}
		   }
		   else if (tokenIs("right"))
		   {
		   	getNextToken();
		   	if (tokenIs("inset")) inset=true;
		   	else unGetToken();
		   	skip("of");
		   	if (isSymbol())
		   	{
		   		if (getHandler() instanceof GraphicsHComponent)
		   		{
		   			return new GraphicsVRight((GraphicsHComponent)getHandler(),inset);
		   		}
		   		warning(this,GraphicsLMessages.componentExpected(getToken()));
		   	}
		   }
		   else if (tokenIs("top"))
		   {
		   	getNextToken();
		   	if (tokenIs("inset")) inset=true;
		   	else unGetToken();
		   	skip("of");
		   	if (isSymbol())
		   	{
		   		if (getHandler() instanceof GraphicsHComponent)
		   		{
		   			return new GraphicsVTop((GraphicsHComponent)getHandler(),inset);
		   		}
		   		warning(this,GraphicsLMessages.componentExpected(getToken()));
		   	}
		   }
		   else if (tokenIs("bottom"))
		   {
		   	getNextToken();
		   	if (tokenIs("inset")) inset=true;
		   	else unGetToken();
		   	skip("of");
		   	if (isSymbol())
		   	{
		   		if (getHandler() instanceof GraphicsHComponent)
		   		{
		   			return new GraphicsVBottom((GraphicsHComponent)getHandler(),inset);
		   		}
		   		warning(this,GraphicsLMessages.componentExpected(getToken()));
		   	}
		   }
		   else if (tokenIs("width"))
		   {
		   	skip("of");
		   	if (isSymbol())
		   	{
		   		if (getHandler() instanceof GraphicsHComponent)
		   		{
		   			if (getHandler() instanceof GraphicsHWindow)
		   				return new GraphicsVWidth((GraphicsHWindow)getHandler(),false);
		   			return new GraphicsVWidth((GraphicsHComponent)getHandler());
		   		}
		   		warning(this,GraphicsLMessages.componentExpected(getToken()));
		   	}
		   }
		   else if (tokenIs("height"))
		   {
		   	skip("of");
		   	if (isSymbol())
		   	{
		   		if (getHandler() instanceof GraphicsHComponent)
		   		{
		   			if (getHandler() instanceof GraphicsHWindow)
		   				return new GraphicsVHeight((GraphicsHWindow)getHandler(),false);
		   			return new GraphicsVHeight((GraphicsHComponent)getHandler());
		   		}
		   		warning(this,GraphicsLMessages.componentExpected(getToken()));
		   	}
		   }
		   else if (tokenIs("content"))
		   {
		   	getNextToken();
		   	if (tokenIs("width"))
		   	{
		   		skip("of");
		   		if (isSymbol())
		   		{
		   			if (getHandler() instanceof GraphicsHWindow)
		   				return new GraphicsVWidth((GraphicsHWindow)getHandler(),true);
		   			warning(this,GraphicsLMessages.windowExpected(getToken()));
		   		}
		   	}
		   	else if (tokenIs("height"))
		   	{
		   		skip("of");
		   		if (isSymbol())
		   		{
		   			if (getHandler() instanceof GraphicsHWindow)
		   				return new GraphicsVHeight((GraphicsHWindow)getHandler(),true);
		   			warning(this,GraphicsLMessages.windowExpected(getToken()));
		   		}
		   	}
		   }
		   else if (tokenIs("target"))
		   {
		   	return new GraphicsVTarget(getProgram());
		   }
		   else if (tokenIs("key"))
		   {
		   	getNextToken();
		   	if (tokenIs("char"))
		   	{
		   		numeric=false;
		   		return new GraphicsVKeyChar(getProgram());
		   	}
		   	else if (tokenIs("code")) return new GraphicsVKeyCode(getProgram());
		   	else dontUnderstandToken();
		   }
		   else if (tokenIs("mouse"))
		   {
		   	getNextToken();
		   	if (tokenIs("left"))
		   	{
		   		numeric=false;
		   		return new GraphicsVMouse(getProgram(),false);
		   	}
		   	if (tokenIs("top"))
		   	{
		   		numeric=false;
		   		return new GraphicsVMouse(getProgram(),true);
		   	}
		   	dontUnderstandToken();
		   }
		   else if (tokenIs("action"))
		   {
		   	skip("of");
		   	if (isSymbol())
		   	{
		   		if (getHandler() instanceof GraphicsHButton)
		   		{
		   			return new GraphicsVAction((GraphicsHButton)getHandler());
		   		}
		   		if (getHandler() instanceof GraphicsHMenuitem)
		   		{
		   			return new GraphicsVAction((GraphicsHMenuitem)getHandler());
		   		}
		   	}
		   	warning(this,GraphicsLMessages.componentExpected(getToken()));
		   }
		   else if (tokenIs("selection"))
		   {
		   	skip("index");
		   	unGetToken();
		   	skip("of");
		   	if (isSymbol())
		   	{
		   		if (getHandler() instanceof GraphicsHCombobox)
		   		{
		   			return new GraphicsVSelectionIndex((GraphicsHCombobox)getHandler());
		   		}
		   	}
		   	warning(this,GraphicsLMessages.componentExpected(getToken()));
		   }
		   else if (tokenIs("choice"))
		   {
		   	skip("of");
		   	if (isSymbol())
		   	{
		   		if (getHandler() instanceof GraphicsHDialog)
		   		{
		   			numeric=true;
		   			return new GraphicsVAction((GraphicsHDialog)getHandler(),GraphicsHDialog.CHOICE);
		   		}
		   	}
		   	warning(this,GraphicsLMessages.dialogExpected(getToken()));
		   }
		   else if (tokenIs("user"))
		   {
		   	getNextToken();
		   	if (!tokenIs("input")) unGetToken();
		   	skip("of");
		   	if (isSymbol())
		   	{
		   		if (getHandler() instanceof GraphicsHDialog)
		   		{
		   			numeric=false;
		   			return new GraphicsVAction((GraphicsHDialog)getHandler(),GraphicsHDialog.VALUE);
		   		}
		   	}
		   	warning(this,GraphicsLMessages.dialogExpected(getToken()));
		   }
		}
	   return null;
	}
}
