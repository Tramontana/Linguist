//	GraphicsKHandler.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.keyword;

import java.awt.Color;
import java.awt.Font;

import net.eclecity.linguist.graphics.GraphicsLMessages;
import net.eclecity.linguist.graphics.handler.GraphicsHColor;
import net.eclecity.linguist.graphics.handler.GraphicsHComponent;
import net.eclecity.linguist.graphics.handler.GraphicsHFont;
import net.eclecity.linguist.graphics.handler.GraphicsHImage;
import net.eclecity.linguist.graphics.value.GraphicsVCenterOf;
import net.eclecity.linguist.graphics.value.GraphicsVColor;
import net.eclecity.linguist.graphics.value.GraphicsVFont;
import net.eclecity.linguist.graphics.value.GraphicsVLocation;
import net.eclecity.linguist.graphics.value.GraphicsVMouseLocation;
import net.eclecity.linguist.graphics.value.GraphicsVScreenCenter;
import net.eclecity.linguist.graphics.value.GraphicsVScreenSize;
import net.eclecity.linguist.graphics.value.GraphicsVSize;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.value.LVConstant;
import net.eclecity.linguist.value.LVStringConstant;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	* Superclass of all Swing keyword handlers.
*/
public abstract class GraphicsKHandler extends LKHandler
{
	public GraphicsVLocation getLocation() throws LLException
	{
		// the [absolute] mouse location/position
		if (tokenIs("the")) getNextToken();
		boolean absolute=false;
		if (tokenIs("absolute"))
		{
			absolute=true;
			getNextToken();
		}
		if (tokenIs("mouse"))
		{
			getNextToken();
			if (tokenIs("location") || tokenIs("position"))
			{
				return new GraphicsVMouseLocation(getProgram(),absolute);
			}
			dontUnderstandToken();
		}
		else if (tokenIs("screen"))
		{
			getNextToken();
			if (tokenIs("center") || tokenIs("centre")) return new GraphicsVScreenCenter();
			dontUnderstandToken();
		}
		else if (tokenIs("location") || tokenIs("position"))
		{
			skip("of");
			if (isSymbol())
			{
				if (getHandler() instanceof GraphicsHComponent)
				{
					return new GraphicsVLocation((GraphicsHComponent)getHandler());
				}
			}
		}
		else if (tokenIs("center") || tokenIs("centre"))
		{
		   skip("of");
		   if (isSymbol())
		   {
		   	if (getHandler() instanceof GraphicsHComponent)
		   	{
		   		return new GraphicsVCenterOf((GraphicsHComponent)getHandler());
		   	}
		   	warning(this,GraphicsLMessages.componentExpected(getToken()));
		   }
		}
		LVValue left=getValue();
		getNextToken();
		LVValue top=getValue();
		return new GraphicsVLocation(left,top);
	}

	public GraphicsVLocation getNextLocation() throws LLException
	{
		getNextToken();
		return getLocation();
	}
	
	public GraphicsVSize getSize() throws LLException
	{
		if (tokenIs("the")) getNextToken();
		if (tokenIs("size"))
		{
			skip("of");
			if (isSymbol())
			{
				if (getHandler() instanceof GraphicsHImage)
				{
					return new GraphicsVSize((GraphicsHImage)getHandler());
				}
				if (getHandler() instanceof GraphicsHComponent)
				{
					return new GraphicsVSize((GraphicsHComponent)getHandler());
				}
				throw new LLException(GraphicsLMessages.componentExpected(getToken()));
			}
		}
		else if (tokenIs("screen"))
		{
			getNextToken();
			if (tokenIs("size")) return new GraphicsVScreenSize();
			dontUnderstandToken();
		}
		LVValue width=getValue();
		getNextToken();
		LVValue height=getValue();
		return new GraphicsVSize(width,height);
	}

	public GraphicsVSize getNextSize() throws LLException
	{
		getNextToken();
		return getSize();
	}
	
	/***************************************************************************
		Process a color specification.
	*/
	public GraphicsVColor getColor() throws LLException
	{
		if (isSymbol())
		{
			if (getHandler() instanceof GraphicsHColor) return new GraphicsVColor((GraphicsHColor)getHandler());
		}
		else if (tokenIs("color") || tokenIs("colour")) getNextToken();
		return new GraphicsVColor(getValue(),getNextValue(),getNextValue());
	}
	
	public GraphicsVColor getNextColor() throws LLException
	{
		getNextToken();
		return getColor();
	}
	
	/***************************************************************************
		Process a font specification.
	*/
	public GraphicsVFont getFont() throws LLException
	{
		if (isSymbol())
		{
			if (getHandler() instanceof GraphicsHFont) return new GraphicsVFont((GraphicsHFont)getHandler());
		}
		LVValue name=new LVStringConstant("Dialog");
		LVValue style=new LVConstant(Font.PLAIN);
		LVValue size=new LVConstant(14);
		GraphicsVColor color=new GraphicsVColor(Color.black);
		while (true)
		{
			if (tokenIs("name")) name=getNextValue();
			else if (tokenIs("style"))
			{
				int s=Font.PLAIN;
				while (true)
				{
					getNextToken();
					if (tokenIs("plain")) s|=Font.PLAIN;
					else if (tokenIs("bold")) s|=Font.BOLD;
					else if (tokenIs("italic"))s|=Font.ITALIC;
					else
					{
						unGetToken();
						break;
					}
				}
				style=new LVConstant(s);
			}
			else if (tokenIs("size")) size=getNextValue();
			else if (tokenIs("color") || tokenIs("colour")) color=getNextColor();
			else
			{
				unGetToken();
				break;
			}
			getNextToken();
		}
		return new GraphicsVFont(name,style,size,color);
	}
	
	public GraphicsVFont getNextFont() throws LLException
	{
		getNextToken();
		return getFont();
	}
}

