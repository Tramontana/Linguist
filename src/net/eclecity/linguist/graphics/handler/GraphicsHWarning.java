// GraphicsHWarning.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import java.awt.Component;

import javax.swing.JOptionPane;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Show a warning dialog.
	<pre>
	[1.001 GT]  15/02/01  New class.
	</pre>
*/
public class GraphicsHWarning extends LHHandler
{
	private LVValue message;
	private GraphicsHWindow window;

	public GraphicsHWarning(int line,LVValue message,GraphicsHWindow window)
	{
		this.line=line;
		this.message=message;
		this.window=window;
	}

	/***************************************************************************
		(Runtime) Do it now.
	*/
	public int execute() throws LRException
	{
		Component comp=null;
		if (window!=null) comp=window.getComponent();
		JOptionPane.showMessageDialog(comp,message.getStringValue(),"Warning",
			JOptionPane.WARNING_MESSAGE);
		return pc+1;
	}
}

