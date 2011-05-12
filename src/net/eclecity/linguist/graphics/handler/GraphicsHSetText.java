// GraphicsHSetText.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Set the text of a text component.
	<pre>
	[1.001 GT]  14/12/00  Prexisting class.
	</pre>
*/
public class GraphicsHSetText extends LHHandler
{
	private GraphicsHTextComponent textComponent=null;
	private GraphicsHStyledtext styledText=null;
	private LVValue text;

	public GraphicsHSetText(int line,GraphicsHTextComponent textComponent,LVValue text)
	{
		this.line=line;
		this.textComponent=textComponent;
		this.text=text;
	}

	public GraphicsHSetText(int line,GraphicsHStyledtext styledText,LVValue text)
	{
		this.line=line;
		this.styledText=styledText;
		this.text=text;
	}

	public int execute() throws LRException
	{
		if (textComponent!=null) textComponent.setText(text);
		else if (styledText!=null) styledText.setText(text);
		return pc+1;
	}
}

