// BasicHEncode.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHStringHolder;
import net.eclecity.linguist.support.Base64;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Encode a hashtable or a file.
	<pre>
	[1.001 GT]  11/01/03  New class.
	[1.001 GT]  14/06/03  Add base64.
	</pre>
*/
public class BasicHEncode extends LHHandler
{
	private BasicHHashtable table;
	private LHStringHolder string;
	private LVValue file;

	/***************************************************************************
		Encode a hashtable to a string.
	*/
	public BasicHEncode(int line,BasicHHashtable table,LHStringHolder string)
	{
		super(line);
		this.table=table;
		this.string=string;
	}

	/***************************************************************************
		Base64 encode a file.
	*/
	public BasicHEncode(int line,LVValue file,LHStringHolder string)
	{
		super(line);
		this.file=file;
		this.string=string;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute()
	{
		if (table!=null) table.encode(string);
		else if (file!=null)
		{
			try
			{
				ByteArrayOutputStream bos=new ByteArrayOutputStream();
				FileInputStream fis=new FileInputStream(file.getStringValue());
				int count;
				byte[] buf=new byte[1000];
				while ((count=fis.read(buf))>0) bos.write(buf,0,count);
				fis.close();
				string.setValue(Base64.encodeBytes(bos.toByteArray()));
				bos.close();
			}
			catch (Throwable e) {e.printStackTrace();}			
		}
		return pc+1;
	}
}

