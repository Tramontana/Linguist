//	ServletKTemplate.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.servlet.keyword;

import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.keyword.LKVariableHandler;
import net.eclecity.linguist.servlet.handler.ServletHTemplate;

/******************************************************************************
	<pre>
	template {name} {elements}
	<p>
	[1.001 GT]  25/11/00  Pre-existing class.
	</pre>
*/
public class ServletKTemplate extends LKVariableHandler
{
	/***************************************************************************
		Return an instance of the runtime type.
	*/
	public LHVariableHandler getRuntimeClass() { return new ServletHTemplate(); }
}
