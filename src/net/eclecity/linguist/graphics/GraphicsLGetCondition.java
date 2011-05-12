// GraphicsLGetCondition

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics;

import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.graphics.condition.GraphicsCExists;
import net.eclecity.linguist.graphics.condition.GraphicsCRightButton;
import net.eclecity.linguist.graphics.condition.GraphicsCSelected;
import net.eclecity.linguist.graphics.condition.GraphicsCStatus;
import net.eclecity.linguist.graphics.condition.GraphicsCVisible;
import net.eclecity.linguist.graphics.handler.GraphicsHButton;
import net.eclecity.linguist.graphics.handler.GraphicsHComponent;
import net.eclecity.linguist.main.LLCompiler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLGetCondition;

/******************************************************************************
	Generate code for a condition:
	
	[not] right button
	{button} is [not] selected
	{component} is [not] visible
	{button} is [not] on/off
	{component} exists
*/
public class GraphicsLGetCondition extends LLGetCondition
{
	public LCCondition getCondition(LLCompiler c) throws LLException
	{
		compiler=c;
		boolean sense=true;
		getNextToken();
		if	(tokenIs("not"))
		{
			sense=false;
			getNextToken();
		}
		if (tokenIs("right"))
		{
			getNextToken();
			if (tokenIs("button"))
			{
				return new GraphicsCRightButton(getProgram(),sense);
			}
			return null;
		}
		else if (isSymbol())
		{
			if (!sense) dontUnderstandToken();
			if (getHandler() instanceof GraphicsHButton)
			{
				skip("is");
				if (tokenIs("not"))
				{
					sense=false;
					getNextToken();
				}
				if (tokenIs("selected")) return new GraphicsCSelected((GraphicsHButton)getHandler(),sense);
				if (tokenIs("visible")) return new GraphicsCVisible((GraphicsHComponent)getHandler(),sense);
				if (tokenIs("on")) return new GraphicsCStatus((GraphicsHButton)getHandler(),sense);
				if (tokenIs("off")) return new GraphicsCStatus((GraphicsHButton)getHandler(),!sense);
				dontUnderstandToken();
			}
			if (getHandler() instanceof GraphicsHComponent)
			{
				getNextToken();
				if (tokenIs("exists")) return new GraphicsCExists((GraphicsHComponent)getHandler(),sense);
				if (tokenIs("is"))
				{
					getNextToken();
					if (tokenIs("not"))
					{
						sense=false;
						getNextToken();
					}
					if (tokenIs("visible")) return new GraphicsCVisible((GraphicsHComponent)getHandler(),sense);
					dontUnderstandToken();
				}
			}
		}
		return null;
	}
}
