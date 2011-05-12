//	GraphicsKImage.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.keyword;

import net.eclecity.linguist.graphics.GraphicsLMessages;
import net.eclecity.linguist.graphics.handler.GraphicsHImage;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
   image {symbol} from {source} [at {left} {top}] [size {width} {height}]
*/
public class GraphicsKImage extends GraphicsKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
	   getNextToken();
	   String name=getToken();
	   int left=0;
	   int top=0;
	   int width=-1;
	   int height=-1;
	   getNextToken();
	   if (tokenIs("from"))
	   {
	   	getNextToken();
	   	String source=getString();
	   	boolean extract=false;
	   	while (true)
	   	{
		   	getNextToken();
		   	if (tokenIs("at"))
		   	{
		   		getNextToken();
		   		left=evaluateInt();
		   		getNextToken();
		   		top=evaluateInt();
		   		extract=true;
		   	}
		   	else if (tokenIs("size"))
		   	{
		   		getNextToken();
		   		width=evaluateInt();
		   		getNextToken();
		   		height=evaluateInt();
		   		extract=true;
		   	}
		   	else
		   	{
		   		unGetToken();
		   		break;
		   	}
		   }
	   	GraphicsHImage handler=new GraphicsHImage(line,name,source,left,top,width,height,extract,getPass());
	   	if ((getPass()==2) && handler.getValue()==null) throw new LLException(GraphicsLMessages.badImage(source));
	   	putSymbol(name,handler);
	   	return handler;
	   }
	   return null;
	}
}

