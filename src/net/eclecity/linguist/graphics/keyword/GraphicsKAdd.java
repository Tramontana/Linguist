//	GraphicsKAdd.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.keyword;

import java.awt.BorderLayout;

import net.eclecity.linguist.graphics.GraphicsLMessages;
import net.eclecity.linguist.graphics.handler.GraphicsHAdd;
import net.eclecity.linguist.graphics.handler.GraphicsHButton;
import net.eclecity.linguist.graphics.handler.GraphicsHButtonGroup;
import net.eclecity.linguist.graphics.handler.GraphicsHCombobox;
import net.eclecity.linguist.graphics.handler.GraphicsHComponent;
import net.eclecity.linguist.graphics.handler.GraphicsHMenu;
import net.eclecity.linguist.graphics.handler.GraphicsHMenuitem;
import net.eclecity.linguist.graphics.handler.GraphicsHPopupmenu;
import net.eclecity.linguist.graphics.handler.GraphicsHStyle;
import net.eclecity.linguist.graphics.handler.GraphicsHTextPanel;
import net.eclecity.linguist.graphics.handler.GraphicsHWindow;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	<pre>
	add {style} to {textpanel}
	add {menu} to {window}/{popupmenu}
	add {menuitem}/separator to {menu}/{popupmenu}/{buttongroup}
	add {button} to {buttongroup}
	add {text} to {combobox}
	add {component} to {container} [at {constraints}]
	<p>
	[1.001 GT]  17/10/00  Pre-existing.
	</pre>
*/
public class GraphicsKAdd extends GraphicsKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		getNextToken();
		if (tokenIs("separator"))
		{
			// add separator to {menu}/{popupmenu}
			skip("to");
			if (isSymbol())
			{
				if (getHandler() instanceof GraphicsHMenu)
					return new GraphicsHAdd(line,(GraphicsHMenu)getHandler(),null);
				if (getHandler() instanceof GraphicsHPopupmenu)
					return new GraphicsHAdd(line,(GraphicsHPopupmenu)getHandler(),null);
				inappropriateType();
			}
		}
		else if (isSymbol())
		{
			// add {menu} to {window}/{menu}/{popupmenu}
			if (getHandler() instanceof GraphicsHMenu)
			{
				GraphicsHMenu menu=(GraphicsHMenu)getHandler();
				skip("to");
				if (isSymbol())
				{
					if (getHandler() instanceof GraphicsHWindow)
						return new GraphicsHAdd(line,(GraphicsHWindow)getHandler(),menu);
					if (getHandler() instanceof GraphicsHMenu)
						return new GraphicsHAdd(line,(GraphicsHMenu)getHandler(),menu);
					if (getHandler() instanceof GraphicsHPopupmenu)
						return new GraphicsHAdd(line,(GraphicsHPopupmenu)getHandler(),menu);
					inappropriateType();
				}
			}
			if (getHandler() instanceof GraphicsHMenuitem)
			{
				// add {menuitem} to {menu}/{popupmenu}/{buttongroup}
				GraphicsHMenuitem item=(GraphicsHMenuitem)getHandler();
				skip("to");
				if (isSymbol())
				{
					if (getHandler() instanceof GraphicsHMenu)
						return new GraphicsHAdd(line,(GraphicsHMenu)getHandler(),item);
					if (getHandler() instanceof GraphicsHPopupmenu)
						return new GraphicsHAdd(line,(GraphicsHPopupmenu)getHandler(),item);
					if (getHandler() instanceof GraphicsHButtonGroup)
						return new GraphicsHAdd(line,(GraphicsHButtonGroup)getHandler(),item);
					inappropriateType();
				}
			}
			if (getHandler() instanceof GraphicsHButton)
			{
				// add {button} to {buttongroup}
				GraphicsHButton button=(GraphicsHButton)getHandler();
				skip("to");
				if (isSymbol())
				{
					if (getHandler() instanceof GraphicsHButtonGroup)
						return new GraphicsHAdd(line,(GraphicsHButtonGroup)getHandler(),button);
					inappropriateType();
				}
			}
			if (getHandler() instanceof GraphicsHStyle)
			{
				GraphicsHStyle style=(GraphicsHStyle)getHandler();
				skip("to");
				if (isSymbol())
				{
					if (getHandler() instanceof GraphicsHTextPanel)
						return new GraphicsHAdd(line,(GraphicsHTextPanel)getHandler(),style);
					inappropriateType();
				}
			}
			// add {component} to {container} [at {constraints}]
			if (getHandler() instanceof GraphicsHComponent)
			{
				GraphicsHComponent component=(GraphicsHComponent)getHandler();
				skip("to");
				if (isSymbol())
				{
					if (getHandler() instanceof GraphicsHComponent)
					{
						Object constraints=null;
						getNextToken();
						if (tokenIs("at"))
						{
							getNextToken();
							if (tokenIs("border"))
							{
								// border layout
								getNextToken();
								if (tokenIs("north")) constraints=BorderLayout.NORTH;
								else if (tokenIs("south")) constraints=BorderLayout.SOUTH;
								else if (tokenIs("east")) constraints=BorderLayout.EAST;
								else if (tokenIs("west")) constraints=BorderLayout.WEST;
								else if (tokenIs("center") || tokenIs("centre"))
									constraints=BorderLayout.CENTER;
							}
							else dontUnderstandToken();
						}
						else unGetToken();
						return new GraphicsHAdd(line,component,(GraphicsHComponent)getHandler(),constraints);
					}
					inappropriateType();
				}
				throw new LLException(GraphicsLMessages.containerExpected(getToken()));
			}
			if (getHandler()==null) return null;
			if (!((LHVariableHandler)getHandler()).hasValue()) return null;
		}
		// If we get here it's probably an instruction to add to a combobox or listbox.
		try
		{
			LVValue value=getValue();
			if (value!=null)
			{
				skip("to");
				if (isSymbol())
				{
					if (getHandler() instanceof GraphicsHCombobox)
					{
						return new GraphicsHAdd(line,(GraphicsHCombobox)getHandler(),value);
					}
				}
			}
		}
		catch (LLException e) {}
		return null;
	}
}

