// GraphicsH2DComponent.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	The base class for any 2D component.
	<pre>
	[1.001 GT]  05/01/01  New class.
	</pre>
*/
public class GraphicsH2DComponent extends GraphicsHComponent
{
	/***************************************************************************
		Set the alpha value of the component.
	*/
	public void setAlpha(LVValue alpha) throws LRException
	{
		((GraphicsHC2DComponent)getComponent()).setAlpha(alpha.getIntegerValue());
	}
}
