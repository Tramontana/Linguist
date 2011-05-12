// BasicHWrite.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Write to a file.
*/
public class BasicHWrite extends LHHandler
{
	private BasicHFile file;
	private LVValue fileName;
	private LVValue value;
	private boolean newline;

	/***************************************************************************
		Write data to a file.
	*/
	public BasicHWrite(int line,BasicHFile file,LVValue value,boolean newline)
	{
		super(line);
		this.file=file;
		this.value=value;
		this.newline=newline;
	}

	/***************************************************************************
		Write data to a named file.
	*/
	public BasicHWrite(int line,LVValue value,LVValue fileName,boolean newline)
	{
		super(line);
		this.value=value;
		this.fileName=fileName;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		if (file!=null) file.write(value,newline);
		else if (fileName!=null)
		{
			try
			{
				BufferedWriter out=new BufferedWriter(new FileWriter(fileName.getStringValue()));
				out.write(value.getStringValue());
				if (newline) out.newLine();
				out.close();
			}
			catch (IOException ignored) {}
		}
		return pc+1;
	}
}

