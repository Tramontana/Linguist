// BasicHDecode.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import java.io.FileOutputStream;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHStringHolder;
import net.eclecity.linguist.support.Base64;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Decode a hashtable.
	<pre>
	[1.001 GT]  11/01/03  New class.
	</pre>
*/
public class BasicHDecode extends LHHandler
{
	private BasicHHashtable table;
	private LHStringHolder string;
	private LVValue file;

	/***************************************************************************
		Decode a string to a hashtable.
	*/
	public BasicHDecode(int line,BasicHHashtable table,LHStringHolder string)
	{
		this.line=line;
		this.table=table;
		this.string=string;
	}

	/***************************************************************************
		Base64 decode a string to a file.
	*/
	public BasicHDecode(int line,LHStringHolder string,LVValue file)
	{
		super(line);
		this.string=string;
		this.file=file;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute()
	{
		if (table!=null) table.decode(string);
		else if (string!=null)
		{
			try
			{
				FileOutputStream fos=new FileOutputStream(file.getStringValue());
				Base64.Base64OutputStream baos=new Base64.Base64OutputStream(fos,false);
				baos.write(string.getStringValue().getBytes());
				baos.flush();
				baos.close();
			}
			catch (Throwable e) {e.printStackTrace();}			
		}
		return pc+1;
	}
}

