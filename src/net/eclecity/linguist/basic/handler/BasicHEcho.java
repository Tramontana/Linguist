// BasicHEcho

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import javax.swing.JOptionPane;

import net.eclecity.linguist.basic.runtime.BasicRBackground;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Echo a string.
	<pre>
	[1.001 GT]  21/05/04  Duplicate of BasicHPrompt.
	</pre>
*/
public class BasicHEcho extends LHHandler
{
	private LVValue value;				// the value to put

	public BasicHEcho(int line,LVValue value)
	{
		super(line);
		this.value=value;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		// Echo as an option dialog or to the console
		if (((BasicRBackground)getBackground("basic")).getPrompt())
		{
			JOptionPane.showMessageDialog(null,value.getStringValue());
		}
		else
		{
			if (getProgram().getSystemOut()==null)
				System.out.println(value.getStringValue());
			else getProgram().systemOut(value.getStringValue());
		}
		return pc+1;
	}
}

