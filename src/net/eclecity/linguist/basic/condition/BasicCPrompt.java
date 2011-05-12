//	BasicCPrompt.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.condition;

import javax.swing.JOptionPane;

import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Put up a prompt box and get OK or Cancel.
	<pre>
	[1.001 GT]  12/10/00  New class.
	</pre>
*/
public class BasicCPrompt extends LCCondition
{
	private LVValue prompt;

	public BasicCPrompt(LVValue prompt)
	{
		this.prompt=prompt;
	}

	public boolean test() throws LRException
	{
		return (JOptionPane.showConfirmDialog(null,prompt.getStringValue(),"Linguist",
			JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE)==JOptionPane.OK_OPTION);
	}
}
