// BasicHFile.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import net.eclecity.linguist.basic.runtime.BasicRBackground;
import net.eclecity.linguist.basic.runtime.BasicRMessages;
import net.eclecity.linguist.handler.LHData;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVStringConstant;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	A file variable.
	<pre>
	[1.001 GT]  23/11/00  Pre-existing.
	</pre>
*/
public class BasicHFile extends LHVariableHandler
{
	public Object newElement(Object extra) { return new Data(); }

	/***************************************************************************
		Tell callers this type doesn't return a value.
	*/
	public boolean hasValue() { return false; }

	/***************************************************************************
		Create a new file or overwrite an existing one.
	*/
	private void create(String directory,String name) throws LRException
	{
		Data fdata=(Data)getData();
		fdata.file=null;
		fdata.atEOF=false;
		if (directory==null || name==null) return;
		fdata.directory=directory;
		File f=new File(directory);
		f.mkdirs();
		fdata.file=new File(f,name);
		try
		{
			fdata.writer=new BufferedWriter(new FileWriter(fdata.file));
		}
		catch (IOException e) { fdata.file=null; }
	}

	/***************************************************************************
		Create a new file or overwrite an existing one.
	*/
	public void create(LVValue path) throws LRException
	{
		String name=path.getStringValue();
		String directory="";
		int n=name.lastIndexOf("/");
		if (n>=0) directory=name.substring(0,n);
		name=name.substring(n+1);
		create(directory,name);
	}

	/***************************************************************************
		Create a new file using a file dialog.
	*/
	public void create(LVValue name,LVValue left,LVValue top) throws LRException
	{
		Frame f=new Frame();
		f.setLocation(left.getIntegerValue(),top.getIntegerValue());
		FileDialog fd=new FileDialog(f,name.getStringValue(),FileDialog.SAVE);
		BasicRBackground bg=(BasicRBackground)getBackground("basic");
		fd.setDirectory(bg.getDirectory());
		fd.setVisible(true);
		bg.setDirectory(fd.getDirectory());
		create(fd.getDirectory(),fd.getFile());
	}

	/***************************************************************************
		Open an existing file.
	*/
	private void open(String directory,String name) throws LRException
	{
		Data fdata=(Data)getData();
		fdata.file=null;
		fdata.atEOF=false;
		if (directory==null || name==null) return;
		if (directory.length()==0)
		{
			directory=System.getProperty("user.dir");
			if (!directory.endsWith(System.getProperty("file.separator"))) directory+="/";
		}
		fdata.file=new File(directory,name);
		if (!fdata.file.exists()) throw new LRException(BasicRMessages.fileDoesNotExist(name));
		fdata.directory=directory;
		if (!fdata.file.isDirectory())
		{
			try
			{
				fdata.reader=new BufferedReader(new FileReader(fdata.file));
			}
			catch (IOException e) { e.printStackTrace(); fdata.file=null; }
		}
	}

	/***************************************************************************
		Open the file whose name is given.
	*/
	public void open(LVValue name) throws LRException
	{
		open("",name.getStringValue());
	}

	/***************************************************************************
		Open an existing file using a file dialog.
	*/
	public void open(LVValue prompt,LVValue left,LVValue top) throws LRException
	{
		Frame f=new Frame();
		f.setLocation(left.getIntegerValue(),top.getIntegerValue());
		FileDialog fd=new FileDialog(f,prompt.getStringValue(),FileDialog.LOAD);
		BasicRBackground bg=(BasicRBackground)getBackground("basic");
		fd.setDirectory(bg.getDirectory());
		fd.setVisible(true);
		bg.setDirectory(fd.getDirectory());
		open(fd.getDirectory(),fd.getFile());
	}

	/***************************************************************************
		Test if the file is open.
	*/
	public boolean isOpen() throws LRException
	{
		Data fdata=(Data)getData();
		return (fdata.file!=null);
	}

	/***************************************************************************
		Read data from a file.
	*/
	public void read(LHVariableHandler handler,boolean wholeLine) throws LRException
	{
		Data fdata=(Data)getData();
		if (fdata.reader==null) throw new LRException(BasicRMessages.fileNotOpen(name));
		String s="";
		if (wholeLine)
		{
			try
			{
				s=fdata.reader.readLine();
				fdata.reader.mark(256);
				if (fdata.reader.readLine()!=null) fdata.reader.reset();
				else fdata.atEOF=true;
			}
			catch (IOException e) { throw new LRException(BasicRMessages.cantReadFile(name)); }
		}
		else
		{
			boolean flag=false;
			while (true)
			{
				try
				{
					int i=fdata.reader.read();
					fdata.reader.mark(1);
					if (fdata.reader.read()>=0) fdata.reader.reset();
					else fdata.atEOF=true;
					char c=(char)i;
					if (c<=' ')
					{
						if (!flag) continue;
						break;
					}
					s+=c;
					flag=true;
				}
				catch (IOException e) { throw new LRException(BasicRMessages.cantReadFile(name)); }
			}
		}
		if (handler instanceof BasicHVariable)
		{
			long value;
			try
			{
				value=Long.parseLong(s);
			}
			catch (NumberFormatException e ) { value=0; }
			((BasicHVariable)handler).setValue(value);
		}
		else if (handler instanceof BasicHBuffer)
		{
			((BasicHBuffer)handler).setValue(s);
		}
	}

	/***************************************************************************
		Write data to a file.
	*/
	public void write(LVValue s,boolean newline) throws LRException
	{
		Data fdata=(Data)getData();
		if (fdata.writer==null) throw new LRException(BasicRMessages.fileNotOpen(name));
		try
		{
			fdata.writer.write(s.getStringValue());
			if (newline) fdata.writer.newLine();
		}
		catch (IOException e) { throw new LRException(BasicRMessages.cantWriteFile(name)); }
	}

	/***************************************************************************
		Close a file.
	*/
	public void close() throws LRException
	{
		Data fdata=(Data)getData();
		try
		{
			if (fdata.reader!=null)
			{
				fdata.reader.close();
				fdata.reader=null;
			}
			if (fdata.writer!=null)
			{
				fdata.writer.flush();
				fdata.writer.close();
				fdata.writer=null;
			}
		}
		catch (IOException e ) {}
	}

	/***************************************************************************
		Test if we are at the end of a file.
	*/
	public boolean isAtEOF() throws LRException
	{
		Data fdata=(Data)getData();
		return fdata.atEOF;
	}

	/***************************************************************************
		Return the last modification time of the file.
	*/
	public long getModifyTime() throws LRException
	{
		Data fdata=(Data)getData();
		try { return fdata.file.lastModified(); }
		catch (NullPointerException e) { return 0; }
	}

	/***************************************************************************
		Create a directory.
	*/
	public static void makeDirectory(LVValue name) throws LRException
	{
		new File(name.getStringValue()).mkdirs();
	}

	/***************************************************************************
		Delete a file.
	*/
	public static void delete(LVValue name) throws LRException
	{
		if (!(new File(name.getStringValue()).delete()))
		{
			new IOException(BasicRMessages.cantDeleteFile(name.getStringValue())).printStackTrace();
//			throw new LRError(BasicRErrors.cantDeleteFile(name.getStringValue()));
		}
	}

	/***************************************************************************
		Rename a file.
	*/
	public static void rename(LVValue oldName,LVValue newName) throws LRException
	{
		String name1=oldName.getStringValue();
		String name2=newName.getStringValue();
		File file=new File(name1);
		if (!file.renameTo(new File(name2)))
		{
			new IOException(BasicRMessages.cantRenameFile(name1,name2)).printStackTrace();
//			throw new LRError(BasicRErrors.cantRenameFile(name1,name2));
		}
	}

	/***************************************************************************
		Copy a file.
	*/
	public static void copy(LVValue fromName,LVValue toName) throws LRException
	{
		String name1=fromName.getStringValue();
		String name2=toName.getStringValue();
		try
		{
			File file1=new File(name1);
			if (file1.isDirectory())
			{
				new File(name2).mkdir();
				String[] list=file1.list();
				for (int n=0; n<list.length; n++)
				{
					copy(new LVStringConstant(name1+"/"+list[n]),
						new LVStringConstant(name2+"/"+list[n]));
				}
			}
			else
			{
				FileInputStream in=new FileInputStream(name1);
				FileOutputStream out=new FileOutputStream(name2);
				byte[] buf=new byte[1024];
				int count;
				while ((count=in.read(buf))>0) out.write(buf,0,count);
				in.close();
				out.close();
			}
		}
		catch (IOException e)
		{
			new IOException(BasicRMessages.cantCopyFile(name1,name2)).printStackTrace();
//			throw new LRError(BasicRErrors.cantCopyFile(name1,name2));
		}
	}

	/***************************************************************************
		A private class that holds data about a file.
	*/
	class Data extends LHData
	{
		File file=null;
		String directory;
		BufferedReader reader=null;
		BufferedWriter writer=null;
		boolean atEOF=false;

		Data() {}
	}
}

