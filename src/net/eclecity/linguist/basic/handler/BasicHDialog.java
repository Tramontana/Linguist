// BasicHDialog.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHData;
import net.eclecity.linguist.handler.LHVariableHandler;

/******************************************************************************
	A dialog variable.
	<pre>
	[1.001 GT]  04/04/02  New class.
	</pre>
*/
public class BasicHDialog extends LHVariableHandler
{
	public Object newElement(Object extra) { return new Data(); }
	
	/***************************************************************************
		A private class that holds data about a dialog.
	*/
	class Data extends LHData
	{
		
		Data() {}
	}
}

