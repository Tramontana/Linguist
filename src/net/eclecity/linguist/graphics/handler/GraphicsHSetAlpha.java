// GraphicsHSetAlpha.java

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
	Set the alpha of a component.
	<pre>
	[1.001 GT]  05/01/01  New class.
	</pre>
*/
public class GraphicsHSetAlpha extends LHHandler
{
	private GraphicsH2DComponent component;
	private LVValue alpha;

	public GraphicsHSetAlpha(int line,GraphicsH2DComponent component,LVValue alpha)
	{
		this.line=line;
		this.component=component;
		this.alpha=alpha;
	}

	public int execute() throws LRException
	{
		component.setAlpha(alpha);
		return pc+1;
	}
}

