// BasicHDecrypt.java

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
	Decrypt a string.
	<pre>
	[1.001 GT]  06/10/00  New class.
	</pre>
*/
public class BasicHDecrypt extends LHHandler
{
	private LHStringHolder string=null;
	private LVValue key;

	public BasicHDecrypt(int line,LHStringHolder string,LVValue key)
	{
		super(line);
		this.string=string;
		this.key=key;
	}

	public int execute() throws LRException
	{
		if (string!=null)
		{
			String value="";
			try
			{
				value=(String)LUEncrypt.decrypt(string.getStringValue(),key.getStringValue());
			}
			catch (Throwable e) {}
			string.setValue(value);
		}
		return pc+1;
	}
}

