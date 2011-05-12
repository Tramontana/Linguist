//	GraphicsKOn.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.keyword;

import net.eclecity.linguist.graphics.GraphicsLMessages;
import net.eclecity.linguist.graphics.handler.GraphicsHButton;
import net.eclecity.linguist.graphics.handler.GraphicsHComponent;
import net.eclecity.linguist.graphics.handler.GraphicsHFrame;
import net.eclecity.linguist.graphics.handler.GraphicsHLabel;
import net.eclecity.linguist.graphics.handler.GraphicsHMenuitem;
import net.eclecity.linguist.graphics.handler.GraphicsHOn;
import net.eclecity.linguist.graphics.handler.GraphicsHStyle;
import net.eclecity.linguist.graphics.handler.GraphicsHTextControl;
import net.eclecity.linguist.graphics.handler.GraphicsHWindow;
import net.eclecity.linguist.handler.LHFlag;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHNoop;
import net.eclecity.linguist.handler.LHStop;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;

/******************************************************************************
	<pre>
	on close {frame} {block}
	on close {window} {block}
	on resize {window} {block}
	on iconify {window} {block}
	on deiconify {window} {block}
	on {button}/{label} {block}
	on {style} {block}
	on mouse down/up/move/drag in {component} {block}
	on mouse enter/exit {component} {block}
	on key pressed/released/typed in {component} {block}
	on {menuitem} {block}
	on change in {textarea} {block}
	on {spreadsheet} {block}
	<p>
	[1.001 GT]  14/10/00  Pre-existing.
	</pre>
*/
public class GraphicsKOn extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
   	getNextToken();
	   if (tokenIs("close"))
	   {
			// on close {window} {block}
	   	getNextToken();
	   	if (isSymbol())
	   	{
				if (getHandler() instanceof GraphicsHWindow)
				{
					GraphicsHWindow window=(GraphicsHWindow)getHandler();
					int here=getPC();
					addCommand(new LHNoop(0));
					doKeyword();
					addCommand(new LHStop(line));
					setCommandAt(new GraphicsHOn(line,GraphicsHComponent.ON_CLOSE,window,getPC()),here);
					return new LHFlag();
				}
				if (getHandler() instanceof GraphicsHFrame)
				{
					GraphicsHFrame frame=(GraphicsHFrame)getHandler();
					int here=getPC();
					addCommand(new LHNoop(0));
					doKeyword();
					addCommand(new LHStop(line));
					setCommandAt(new GraphicsHOn(line,GraphicsHComponent.ON_CLOSE,frame,getPC()),here);
					return new LHFlag();
				}
			}
			warning(this,GraphicsLMessages.windowExpected(getToken()));
		}
	   else if (tokenIs("resize"))
	   {
			// on resize {window} {block}
	   	getNextToken();
	   	if (isSymbol())
	   	{
				if (getHandler() instanceof GraphicsHWindow)
				{
					GraphicsHWindow window=(GraphicsHWindow)getHandler();
					int here=getPC();
					addCommand(new LHNoop(0));
					doKeyword();
					addCommand(new LHStop(line));
					setCommandAt(new GraphicsHOn(line,GraphicsHComponent.ON_RESIZE,window,getPC()),here);
					return new LHFlag();
				}
				if (getHandler() instanceof GraphicsHFrame)
				{
					GraphicsHFrame frame=(GraphicsHFrame)getHandler();
					int here=getPC();
					addCommand(new LHNoop(0));
					doKeyword();
					addCommand(new LHStop(line));
					setCommandAt(new GraphicsHOn(line,GraphicsHComponent.ON_RESIZE,frame,getPC()),here);
					return new LHFlag();
				}
			}
			warning(this,GraphicsLMessages.windowExpected(getToken()));
		}
	   else if (tokenIs("iconify"))
	   {
			// on iconify {window} {block}
	   	getNextToken();
	   	if (isSymbol())
	   	{
				if (getHandler() instanceof GraphicsHWindow)
				{
					GraphicsHWindow window=(GraphicsHWindow)getHandler();
					int here=getPC();
					addCommand(new LHNoop(0));
					doKeyword();
					addCommand(new LHStop(line));
					setCommandAt(new GraphicsHOn(line,GraphicsHComponent.ON_ICONIFY,window,getPC()),here);
					return new LHFlag();
				}
			}
			warning(this,GraphicsLMessages.windowExpected(getToken()));
		}
	   else if (tokenIs("deiconify"))
	   {
			// on deiconify {window} {block}
	   	getNextToken();
	   	if (isSymbol())
	   	{
				if (getHandler() instanceof GraphicsHWindow)
				{
					GraphicsHWindow window=(GraphicsHWindow)getHandler();
					int here=getPC();
					addCommand(new LHNoop(0));
					doKeyword();
					addCommand(new LHStop(line));
					setCommandAt(new GraphicsHOn(line,GraphicsHComponent.ON_DEICONIFY,window,getPC()),here);
					return new LHFlag();
				}
			}
			warning(this,GraphicsLMessages.windowExpected(getToken()));
		}
	   else if (tokenIs("mouse"))
	   {
	   	getNextToken();
	   	if (tokenIs("enter"))
	   	{
				// on mouse enter {component} {block}
	   		getNextToken();
	   		if (isSymbol())
	   		{
					if (getHandler() instanceof GraphicsHComponent)
					{
						GraphicsHComponent component=(GraphicsHComponent)getHandler();
						int here=getPC();
						addCommand(new LHNoop(0));
						doKeyword();
						addCommand(new LHStop(line));
						setCommandAt(new GraphicsHOn(line,component,GraphicsHComponent.MOUSE_ENTER,getPC()),here);
						return new LHFlag();
					}
				}
				warning(this,GraphicsLMessages.componentExpected(getToken()));
			}
	   	else if (tokenIs("exit"))
	   	{
				// on mouse exit {component} {block}
	   		getNextToken();
	   		if (isSymbol())
	   		{
					if (getHandler() instanceof GraphicsHComponent)
					{
						GraphicsHComponent component=(GraphicsHComponent)getHandler();
						int here=getPC();
						addCommand(new LHNoop(0));
						doKeyword();
						addCommand(new LHStop(line));
						setCommandAt(new GraphicsHOn(line,component,GraphicsHComponent.MOUSE_EXIT,getPC()),here);
						return new LHFlag();
					}
				}
				warning(this,GraphicsLMessages.componentExpected(getToken()));
			}
	   	else if (tokenIs("down"))
	   	{
				// on mouse down in {component} {block}
	   		skip("in");
	   		if (isSymbol())
	   		{
					if (getHandler() instanceof GraphicsHComponent)
					{
						GraphicsHComponent component=(GraphicsHComponent)getHandler();
						int here=getPC();
						addCommand(new LHNoop(0));
						doKeyword();
						addCommand(new LHStop(line));
						setCommandAt(new GraphicsHOn(line,component,GraphicsHComponent.MOUSE_DOWN,getPC()),here);
						return new LHFlag();
					}
				}
				warning(this,GraphicsLMessages.componentExpected(getToken()));
			}
	   	else if (tokenIs("up"))
	   	{
				// on mouse up in {component} {block}
	   		skip("in");
	   		if (isSymbol())
	   		{
					if (getHandler() instanceof GraphicsHComponent)
					{
						GraphicsHComponent component=(GraphicsHComponent)getHandler();
						int here=getPC();
						addCommand(new LHNoop(0));
						doKeyword();
						addCommand(new LHStop(line));
						setCommandAt(new GraphicsHOn(line,component,GraphicsHComponent.MOUSE_UP,getPC()),here);
						return new LHFlag();
					}
				}
				warning(this,GraphicsLMessages.componentExpected(getToken()));
			}
	   	else if (tokenIs("move"))
	   	{
				// on mouse move in {component} {block}
	   		skip("in");
	   		if (isSymbol())
	   		{
					if (getHandler() instanceof GraphicsHComponent)
					{
						GraphicsHComponent component=(GraphicsHComponent)getHandler();
						int here=getPC();
						addCommand(new LHNoop(0));
						doKeyword();
						addCommand(new LHStop(line));
						setCommandAt(new GraphicsHOn(line,component,GraphicsHComponent.MOUSE_MOVE,getPC()),here);
						return new LHFlag();
					}
				}
				warning(this,GraphicsLMessages.componentExpected(getToken()));
			}
	   	else if (tokenIs("drag"))
	   	{
				// on mouse drag in {component} {block}
	   		skip("in");
	   		if (isSymbol())
	   		{
					if (getHandler() instanceof GraphicsHComponent)
					{
						GraphicsHComponent component=(GraphicsHComponent)getHandler();
						int here=getPC();
						addCommand(new LHNoop(0));
						doKeyword();
						addCommand(new LHStop(line));
						setCommandAt(new GraphicsHOn(line,component,GraphicsHComponent.MOUSE_DRAG,getPC()),here);
						return new LHFlag();
					}
				}
				warning(this,GraphicsLMessages.componentExpected(getToken()));
			}
		}
	   else if (tokenIs("key"))
	   {
			// on key pressed/released/typed in {component} {block}
			int action=0;
	   	getNextToken();
	   	if (tokenIs("pressed")) action=GraphicsHComponent.KEY_PRESSED;
	   	else if (tokenIs("released")) action=GraphicsHComponent.KEY_RELEASED;
	   	else if (tokenIs("typed")) action=GraphicsHComponent.KEY_TYPED;
	   	else dontUnderstandToken();
	   	skip("in");
	   	if (isSymbol())
	   	{
				if (getHandler() instanceof GraphicsHComponent)
				{
					GraphicsHComponent component=(GraphicsHComponent)getHandler();
					int here=getPC();
					addCommand(new LHNoop(0));
					doKeyword();
					addCommand(new LHStop(line));
					setCommandAt(new GraphicsHOn(line,component,action,getPC()),here);
					return new LHFlag();
				}
			}
			warning(this,GraphicsLMessages.componentExpected(getToken()));
		}
	   else if (tokenIs("change"))
	   {
			// on change in {textarea}/{textfield} {block}
	   	skip("in");
	   	if (isSymbol())
	   	{
				if (getHandler() instanceof GraphicsHTextControl)
				{
					GraphicsHTextControl textArea=(GraphicsHTextControl)getHandler();
					int here=getPC();
					addCommand(new LHNoop(0));
					doKeyword();
					addCommand(new LHStop(line));
					setCommandAt(new GraphicsHOn(line,textArea,getPC()),here);
					return new LHFlag();
				}
			}
			warning(this,GraphicsLMessages.componentExpected(getToken()));
		}
   	else if (isSymbol())
   	{
   		if (getHandler() instanceof GraphicsHButton)
   		{
				// on {button} {block}
				GraphicsHButton button=(GraphicsHButton)getHandler();
				int here=getPC();
				addCommand(new LHNoop(0));
				doKeyword();
				addCommand(new LHStop(line));
				setCommandAt(new GraphicsHOn(line,button,getPC()),here);
				return new LHFlag();
   		}
   		if (getHandler() instanceof GraphicsHLabel)
   		{
				// on {label} {block}
				GraphicsHLabel label=(GraphicsHLabel)getHandler();
				int here=getPC();
				addCommand(new LHNoop(0));
				doKeyword();
				addCommand(new LHStop(line));
				setCommandAt(new GraphicsHOn(line,label,getPC()),here);
				return new LHFlag();
   		}
   		if (getHandler() instanceof GraphicsHStyle)
   		{
				// on {style} {block}
				GraphicsHStyle style=(GraphicsHStyle)getHandler();
				int here=getPC();
				addCommand(new LHNoop(0));
				doKeyword();
				addCommand(new LHStop(line));
				setCommandAt(new GraphicsHOn(line,style,getPC()),here);
				return new LHFlag();
   		}
   		if (getHandler() instanceof GraphicsHMenuitem)
   		{
				// on {menuitem} {block}
				GraphicsHMenuitem item=(GraphicsHMenuitem)getHandler();
				int here=getPC();
				addCommand(new LHNoop(0));
				doKeyword();
				addCommand(new LHStop(line));
				setCommandAt(new GraphicsHOn(line,item,getPC()),here);
				return new LHFlag();
   		}
			warning(this,GraphicsLMessages.buttonExpected(getToken()));
   	}
		return null;
	}
}

