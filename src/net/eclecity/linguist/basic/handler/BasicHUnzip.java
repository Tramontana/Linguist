// BasicHUnzip.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Put a value into a variable.
	<pre>
	[1.001 GT]  04/06/03  New class.
	</pre>
*/
public class BasicHUnzip extends LHHandler
{
	private LVValue directory;
	private LVValue zipfile;
	
	/***************************************************************************
		Unzip a set of files.
	*/
	public BasicHUnzip(int line,LVValue zipfile,LVValue directory)
	{
		super(line);
		this.zipfile=zipfile;
		this.directory=directory;
	}

	/***************************************************************************
		(Runtime) Do it now
	*/
	public int execute()
	{
		try
		{
			ZipInputStream zis=new ZipInputStream(
				new FileInputStream(zipfile.getStringValue()));
			ZipEntry ze;
			byte[] buf=new byte[1024];
			while ((ze=zis.getNextEntry())!=null)
			{
				String name=ze.getName();
//				println(name);
				FileOutputStream fos=new FileOutputStream(new File(directory.getStringValue(),name));
				int len;
				while ((len=zis.read(buf))>0) fos.write(buf,0,len);
				fos.close();
			}
			zis.close();
		}
		catch (Throwable e) { e.printStackTrace(); }
		return pc+1;
	}
}

