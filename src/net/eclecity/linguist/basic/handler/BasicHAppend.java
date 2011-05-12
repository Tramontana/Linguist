// BasicHAppend.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;


import java.io.*;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHStringHolder;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Append a string value to a string holder or a file.
*/
public class BasicHAppend extends LHHandler
{
	private LVValue value;						// the value to append
	private LHStringHolder handler;			// the item to append it to
	private LVValue fileName;					// the file to append it to

	/***************************************************************************
		Append to a string holder.
	*/
	public BasicHAppend(int line,LVValue value,LHStringHolder handler)
	{
		super(line);
		this.value=value;
		this.handler=handler;
	}

	/***************************************************************************
		Append to a string file.
	*/
	public BasicHAppend(int line,LVValue value,LVValue fileName)
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
		if (handler!=null) handler.append(value);
		else if (fileName!=null)
		{
			try
			{
				RandomAccessFile raf=new RandomAccessFile(fileName.getStringValue(),"rw");
				raf.seek(raf.length());
				raf.writeBytes(value.getStringValue());
				raf.close();
			}
			catch (IOException e) {}
			
		}
		return pc+1;
	}
}

