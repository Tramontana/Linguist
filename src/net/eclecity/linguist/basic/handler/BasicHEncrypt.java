// BasicHEncrypt.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHStringHolder;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.util.LUEncrypt;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Encrypt a string.
	<pre>
	[1.001 GT]  06/10/00  New class.
	</pre>
*/
public class BasicHEncrypt extends LHHandler
{
	private LHStringHolder string=null;
	private LVValue key;

	public BasicHEncrypt(int line,LHStringHolder string,LVValue key)
	{
		super(line);
		this.string=string;
		this.key=key;
	}

	public int execute() throws LRException
	{
		if (string!=null)
			string.setValue(LUEncrypt.encrypt(string.getStringValue(),key.getStringValue()));
		return pc+1;
	}
}

