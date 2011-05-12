// GraphicsHSetFont.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import net.eclecity.linguist.graphics.value.GraphicsVFont;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Set the font of a component.
	<pre>
	[1.001 GT]  13/12/00  Pre-existing class.
	</pre>
*/
public class GraphicsHSetFont extends LHHandler
{
	private GraphicsHTextComponent textComponent;
	private GraphicsHStyledtext styledText;
	private GraphicsVFont font;

	public GraphicsHSetFont(int line,GraphicsHTextComponent textComponent,GraphicsVFont font)
	{
		this.line=line;
		this.textComponent=textComponent;
		this.font=font;
	}

	public GraphicsHSetFont(int line,GraphicsHStyledtext styledText,GraphicsVFont font)
	{
		this.line=line;
		this.styledText=styledText;
		this.font=font;
	}

	public int execute() throws LRException
	{
		if (textComponent!=null) textComponent.setFont(font);
		else if (styledText!=null) styledText.setFont(font);
		return pc+1;
	}
}

