// BasicHZip.java

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
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Put a value into a variable.
	<pre>
	[1.001 GT]  04/06/03  New class.
	</pre>
*/
public class BasicHZip extends LHHandler
{
	private LVValue directory;
	private LVValue zipfile;
	private ZipOutputStream zos;
	
	/***************************************************************************
		Zip a set of files.
	*/
	public BasicHZip(int line,LVValue directory,LVValue zipfile)
	{
		super(line);
		this.directory=directory;
		this.zipfile=zipfile;
	}

	/***************************************************************************
		(Runtime) Do it now
	*/
	public int execute() throws LRException
	{
		String outFilename=zipfile.getStringValue();
		try
		{
			// Create zip output stream.
			zos = new ZipOutputStream(new FileOutputStream(outFilename));
			// Start walking the file system.
			walk(new File(directory.getStringValue()));
			zos.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return pc+1;
	}

	/***************************************************************************
		Walk the file tree.
	*/

	void walk(File file)
	{
		if (file.isDirectory())
		{
			String[] list = file.list();
			if (list != null)
			{
				for (int n=0; n<list.length; n++)
					walk(new File(file,list[n]));
			}
		}
		else add(file);
	}

	/***************************************************************************
		This method is called for each file that the file walker discovers.
	*/
	public void add(File f)
	{
		byte[] buf = new byte[1024];
		int len;

		try
		{
			ZipEntry ze = new ZipEntry(convertToZIPName(f));
			FileInputStream is = new FileInputStream(f);

			// Initialize entry with the file's last modified time.
			ze.setTime(f.lastModified());

			// Add the zip entry.
			zos.putNextEntry(ze);

			// Now read and write the zip entry data.
			while ((len = is.read(buf)) >= 0)
			{
				zos.write(buf, 0, len);
			}

			// This isn't necessary since the next call to putNextEntry()
			// will close the zip entry.
			zos.closeEntry();

			try
			{
				System.out.println(ze.getName() + " (" 
					+ ze.getCompressedSize()*100/ze.getSize() + "%)");
			}
			catch (Throwable e) {}

			is.close();

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (LRException e)
		{
			e.printStackTrace();
		}
	}

	/***************************************************************************
		Converts f's pathname to a form acceptable to ZIP files.
	*/
	String convertToZIPName(File f) throws IOException, LRException
	{
		File dir=new File(directory.getStringValue());
		String root = dir.getCanonicalPath();
		String pname = f.getCanonicalPath();
		pname = pname.substring(root.length() + 1);
		pname = pname.replace(File.separatorChar, '/');
		return pname;
	}

}

